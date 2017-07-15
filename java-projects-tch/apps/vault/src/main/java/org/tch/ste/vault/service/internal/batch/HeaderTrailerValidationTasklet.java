/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.batch.support.FixedLengthReader;
import org.tch.ste.infra.util.RandomAccessFileUtil;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.domain.dto.BatchFileHeader;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.util.BatchFileReaderUtil;

/**
 * Implements the validation logic to check if a trailer record is found in the
 * input file.
 * 
 * @author Karthik.
 * 
 */
public class HeaderTrailerValidationTasklet implements Tasklet {

    private static Logger logger = LoggerFactory.getLogger(HeaderTrailerValidationTasklet.class);

    private String iisn;

    private String fileType;

    @Autowired
    @Qualifier("headerReader")
    private FixedLengthReader<BatchFileHeader> headerReader;

    @Autowired
    @Qualifier("trailerReader")
    private FixedLengthReader<BatchFileTrailer> trailerReader;

    @Autowired
    private HeaderValidationService headerValidationService;

    /**
     * Constructor.
     * 
     * @param iisn
     *            String - The IISN.
     * @param fileType
     *            String - The file type.
     */
    public HeaderTrailerValidationTasklet(String iisn, String fileType) {
        this.iisn = iisn;
        this.fileType = fileType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
     * springframework.batch.core.StepContribution,
     * org.springframework.batch.core.scope.context.ChunkContext)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        StepContext stepContext = chunkContext.getStepContext();
        Map<String, Object> jobParams = stepContext.getJobParameters();
        File file = new File((String) jobParams.get(VaultConstants.BATCH_INPUT_FILE_NAME));
        BatchResponseCode response;
        logger.debug("Checking for header record in the file {}", file.getAbsolutePath());
        String firstLine = RandomAccessFileUtil.readFirstLine(file);
        ExecutionContext stepExecutionContext = stepContext.getStepExecution().getExecutionContext();
        stepExecutionContext.put(VaultConstants.NUM_PROCESSED_RECORDS, 0);
        if ((response = processHeader(firstLine, stepExecutionContext)) != BatchResponseCode.SUCCCESS) {
            logger.warn("Header record failed validation in file: {}", file.getAbsolutePath());
        } else {
            logger.debug("Checking for trailer record in the file {}", file.getAbsolutePath());
            String trailerLine = RandomAccessFileUtil.readLastLine(file);
            if (!trailerLine.startsWith(String.valueOf(BatchFileRecordType.TRAILER.ordinal()))) {
                logger.warn("Trailer record not found in file: {}", file.getAbsolutePath());
                response = BatchResponseCode.TRAILER_RECORD_MISSING;
            } else if (!validateTrailer(trailerLine, file, stepExecutionContext)) {
                logger.warn("Trailer record count not matching with number of records in file: {}",
                        file.getAbsolutePath());
                response = BatchResponseCode.TRAILER_RECORD_COUNT_MISMATCH;
            }
        }

        stepExecutionContext.put(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, response.getResponseStr());
        return RepeatStatus.FINISHED;
    }

    /**
     * Validates the trailer record number.
     * 
     * @param trailerLine
     *            String - The trailer line.
     * @param file
     *            File - The file.
     * @param stepExecutionContext
     * @return boolean - If the trailer count is valid.
     * @throws IOException
     *             Thrown.
     * @throws FileNotFoundException
     *             Thrown.
     */
    private boolean validateTrailer(String trailerLine, File file, ExecutionContext stepExecutionContext)
            throws FileNotFoundException, IOException {
        boolean retVal= false;
        if (trailerLine.startsWith(String.valueOf(BatchFileRecordType.TRAILER.ordinal()))) {
            BatchFileTrailer trailer = trailerReader.readLine(trailerLine, true);
            try {
                int numRecords = Integer.parseInt(trailer.getNumRecords());
                stepExecutionContext.put(VaultConstants.NUM_PROCESSED_RECORDS, numRecords);
                retVal = (numRecords == BatchFileReaderUtil.countLines(file) - 2);
            }
            catch(Throwable t) {
                retVal=false;
            }
        }
        return retVal;
    }

    /**
     * Processes the header record.
     * 
     * @param line
     *            String - The line.
     * @param stepExecutionContext
     *            StepExecutionContext - To put iid.
     * @return boolean - True if successful.
     */
    private BatchResponseCode processHeader(String line, ExecutionContext stepExecutionContext) {
        BatchResponseCode retVal = BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR;
        if (line.startsWith(String.valueOf(BatchFileRecordType.HEADER.ordinal()))) {
            BatchFileHeader header = headerReader.readLine(line, true);
            stepExecutionContext.put(VaultConstants.IID, header.getIid());
            retVal = headerValidationService.validate(header, iisn, fileType);
        }
        return retVal;
    }

}
