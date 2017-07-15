/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * Validation hook.
 * 
 * @author Karthik.
 * 
 */
public interface BatchFileValidationHook {
    /**
     * Validation has started.
     * 
     * @param file
     *            File - The file.
     */
    void validationStart(File file);

    /**
     * Validation is complete.
     * 
     * @param file
     *            File - The file.
     * @param validationResult
     *            BatchFileValidationResult - The result of the validation.
     */
    void validationComplete(File file, BatchFileValidationResult validationResult);
}
