/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * Exposes method to return a Batch File Process based on the File.
 * 
 * @author Karthik.
 * 
 */
public interface BatchFileProcessingStrategy {
    /**
     * Returns the processor for a given file.
     * 
     * @param file
     *            File - The file.
     * @return BatchFileProcessor - The file processor.
     */
    BatchFileProcessor getBatchFileProcessor(File file);
}
