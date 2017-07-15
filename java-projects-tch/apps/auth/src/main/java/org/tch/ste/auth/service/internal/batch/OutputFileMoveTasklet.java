/**
 * 
 */
package org.tch.ste.auth.service.internal.batch;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet for Output file move.
 * @author ramug
 *
 */
public class OutputFileMoveTasklet implements Tasklet {
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
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File tempFile = new File(this.tempOutputFileName);
        if (tempFile.exists()) {
            FileUtils.moveFile(tempFile, new File(this.actualOutputFileName));
        }
        return RepeatStatus.FINISHED;
    }

}
