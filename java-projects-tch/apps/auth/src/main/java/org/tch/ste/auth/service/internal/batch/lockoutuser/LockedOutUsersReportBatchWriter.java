/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.lockoutuser;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;

/**
 * The Writer class which handles to write the data to flat files etc.
 * 
 * @author ramug
 * 
 */
public class LockedOutUsersReportBatchWriter {

    protected int value;


    /**
     * Retrieves the value which are stored in Header tasklet.
     * 
     * @param stepExecution
     *            StepExecution - The step execution.
     */
    @BeforeStep
    protected void retrieveHeaderTrailerResult(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        this.value = jobContext.getInt("value");
    }

    /**
     * Stores the record value.
     * 
     * @param stepExecution
     *            StepExecution - The step execution.
     */
    @AfterStep
    protected void retrieveHeaderTrailerResultAfter(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        jobContext.put("value", this.value);
    }
}
