/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * An abstract class which manages the calling of the event hooks and delegates
 * processing to the sub-classes.
 * 
 * @author Karthik.
 * 
 */
public abstract class AbstractBatchFileProcessor implements BatchFileProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchFileProcessor#process(java
     * .lang.String, java.io.File,
     * org.tch.ste.vault.service.internal.batch.BatchFileProcessorEventHook)
     */
    @Override
    public void process(String iisn, File file, BatchFileProcessorEventHook batchFileProcessorEventHook) {
        if (batchFileProcessorEventHook != null) {
            batchFileProcessorEventHook.batchStart(file);
        }

        BatchFileProcessingResult batchFileProcessingResult = doProcess(file);
        if (batchFileProcessorEventHook != null) {
            batchFileProcessorEventHook.batchComplete(file, batchFileProcessingResult);
        }
    }

    /**
     * Abstract Method.
     * 
     * @param file
     *            File - The file.
     * @return BatchFileProcessingResult - The result.
     */
    protected abstract BatchFileProcessingResult doProcess(File file);

}
