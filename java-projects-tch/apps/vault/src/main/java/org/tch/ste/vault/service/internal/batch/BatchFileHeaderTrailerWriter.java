/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.domain.dto.BatchOutputFileHeader;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * To write the footer record.
 * 
 * @author Karthik.
 * 
 */
public abstract class BatchFileHeaderTrailerWriter implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(BatchFileHeaderTrailerWriter.class);

    private String outputFileName;

    private String timeStamp;

    private String iid;

    private String headerTrailerValidationResult;

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     * @param timeStamp
     *            String - The timestamp on the original file.
     */
    public BatchFileHeaderTrailerWriter(String outputFileName, String timeStamp) {
        this.outputFileName = outputFileName;
        this.timeStamp = timeStamp;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.
     * springframework.batch.core.StepExecution)
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        iid = jobContext.getString(VaultConstants.IID, null);
        headerTrailerValidationResult = jobContext.getString(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, null);
        BatchOutputFileHeader header = new BatchOutputFileHeader();
        header.setFileType(getFileType());
        header.setIid(iid);
        header.setTimeStamp(timeStamp);
        header.setErrorCode(headerTrailerValidationResult);
        try {
            BatchFileWriterUtil.writeLine(new File(outputFileName), getHeaderWriter().write(header));
        } catch (IOException e) {
            logger.warn("Unable to write the header to file name: " + outputFileName, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.
     * springframework.batch.core.StepExecution)
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        ExitStatus retVal = ExitStatus.COMPLETED;
        BatchFileTrailer trailer = new BatchFileTrailer();
        trailer.setNumRecords(String.valueOf(stepExecution.getWriteCount()));
        trailer.setType(BatchFileRecordType.TRAILER.ordinal());
        trailer.setErrorCode(jobContext.getString(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, null));
        try {
            BatchFileWriterUtil.writeLine(new File(outputFileName), getTrailerWriter().write(trailer));
        } catch (IOException e) {
            logger.warn("Unable to write the trailer to file name: " + outputFileName, e);
            retVal = ExitStatus.FAILED;
        }
        return retVal;
    }

    /**
     * Returns the specific header writer.
     * 
     * @return FixedLengthWriter<BatchOutputFileHeader> - The return.
     */
    protected abstract FixedLengthWriter<BatchOutputFileHeader> getHeaderWriter();

    /**
     * Fetches the trailer writer for the given class.
     * 
     * @return FixedLengthWriter<BatchFileTrailer> - The trailer writer for the
     *         given class.
     */
    protected abstract FixedLengthWriter<BatchFileTrailer> getTrailerWriter();

    /**
     * Fetches the file type.
     * 
     * @return String - The file type.
     */
    protected abstract String getFileType();
}
