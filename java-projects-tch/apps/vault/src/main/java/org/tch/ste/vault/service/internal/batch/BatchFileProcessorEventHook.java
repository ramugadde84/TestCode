/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * A callback object which is passed to a batch processor and captures the
 * events.
 * 
 * @author Karthik.
 * 
 */
public interface BatchFileProcessorEventHook {
    /**
     * Started processing the batch.
     * 
     * @param file
     *            File - The file.
     */
    void batchStart(File file);

    /**
     * Completed the batch.
     * 
     * @param file
     *            File - The file.
     * @param batchFileProcessingResult
     *            BatchFileProcessingResult - The result of the execution.
     */
    void batchComplete(File file, BatchFileProcessingResult batchFileProcessingResult);
}
