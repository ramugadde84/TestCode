/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

/**
 * Exposes methods to validate a file.
 * 
 * @author Karthik.
 * 
 */
public interface BatchFileValidator {
    /**
     * Validates the file. Implementing classes should check for the following:
     * - Naming Convention. - Duplicate File Name. - Valid IISN which matches
     * the file name. - Validate the batch file type.
     * 
     * @param file
     *            File - The file to be processed.
     * @return BatchFileValidationResult - The result of the validation.
     */
    BatchFileValidationResult validate(File file);
}
