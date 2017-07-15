/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * Interface which exposes methods to process files.
 * 
 * @author Karthik.
 * 
 */
public interface BatchFileProcessor {
    /**
     * Processes a batch.
     * 
     * @param file
     *            File - The file to process.
     * @param iisn
     *            String - The iisn.
     * @param batchFileProcessorEventHook
     *            BatchFileProcessorEventHook - The event hook class.
     */
    void process(String iisn, File file, BatchFileProcessorEventHook batchFileProcessorEventHook);
}
