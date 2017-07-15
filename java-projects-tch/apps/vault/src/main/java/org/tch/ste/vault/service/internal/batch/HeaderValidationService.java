/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.domain.dto.BatchFileHeader;

/**
 * Provides methods to validate the header of a batch file.
 * 
 * @author Karthik.
 * 
 */
public interface HeaderValidationService {
    /**
     * Validates the header.
     * 
     * @param header
     *            BatchFileHeader - The header.
     * @param iisn
     *            String - The iisn.
     * @param fileType
     *            String - The file type.
     * @return BatchResponseCode - The response code.
     */
    BatchResponseCode validate(BatchFileHeader header, String iisn, String fileType);
}
