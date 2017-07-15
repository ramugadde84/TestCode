/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.lockoutuser;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.util.BatchFileWriterUtil;

/**
 * It is a Header Writer Tasklet.
 * 
 * @author ramug
 * 
 */
public class LockedOutUsersReportHeaderWriterTasklet implements Tasklet {
    private static Logger logger = LoggerFactory.getLogger(LockedOutUsersReportHeaderWriterTasklet.class);

    private static String header = "Record Number, Issuer Customer ID, Vault Username,Current State ,Lockout Reason,Lockout timestamp";

    private File outputFile;

    String numberRecords = "Number of Records,";

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     * @throws IOException
     *             Thrown.
     */
    public LockedOutUsersReportHeaderWriterTasklet(String outputFileName) throws IOException {
        outputFile = new File(outputFileName);
        if (!outputFile.exists() && !outputFile.createNewFile()) {
            throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
        }
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
        try {
            BatchFileWriterUtil.writeLine(outputFile, header);
            StepContext stepContext = chunkContext.getStepContext();
            ExecutionContext stepExecutionContext = stepContext.getStepExecution().getExecutionContext();
            stepExecutionContext.put(AuthConstants.NUMBER_OF_RECORDS, numberRecords);
            stepExecutionContext.put(AuthConstants.VALUE, AuthConstants.ZERO);
        } catch (IOException e) {
            logger.error("Error while writing header to output file", e);
        }

        return RepeatStatus.FINISHED;
    }

}
