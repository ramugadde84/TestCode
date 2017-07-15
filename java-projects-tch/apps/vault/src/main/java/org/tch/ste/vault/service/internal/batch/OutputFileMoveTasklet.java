/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.tch.ste.vault.constant.VaultConstants;

/**
 * Common to most batches which send a confirmation/output file. Moves the file
 * to the output folder.
 * 
 * 
 * @author Karthik.
 * 
 */
public class OutputFileMoveTasklet implements Tasklet {

    private static Logger logger = LoggerFactory.getLogger(OutputFileMoveTasklet.class);

    private String tempOutputFileName;

    private String actualOutputFileName;

    /**
     * Overloaded constructor.
     * 
     * @param tempOutputFileName
     *            String - The temporary location of the output file.
     * @param actualOutputFileName
     *            String - The location to which it needs to be moved.
     */
    public OutputFileMoveTasklet(String tempOutputFileName, String actualOutputFileName) {
        this.tempOutputFileName = tempOutputFileName;
        this.actualOutputFileName = actualOutputFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.
     * springframework.batch.core.StepContribution,
     * org.springframework.batch.core.scope.context.ChunkContext)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        File tempFile = new File(this.tempOutputFileName);
        if (tempFile.exists()) {
            try {
                FileUtils.moveFile(tempFile, new File(this.actualOutputFileName));
            } catch (IOException e) {
                logger.error("An error occurred while writing output file {} for issuer {}", actualOutputFileName,
                        actualOutputFileName.split(VaultConstants.FILE_PART_SEPERATOR)[VaultConstants.IISN_PART]);
                logger.error("Exception occured:",e);
            }
        }
        return RepeatStatus.FINISHED;
    }

}
