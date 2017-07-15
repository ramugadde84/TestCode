/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import org.tch.ste.infra.annotation.FixedField;

/**
 * Header to be used for all output files.
 * 
 * @author Karthik.
 * 
 */
public class BatchOutputFileHeader extends BatchFileHeader {

    /**
     * 
     */
    private static final long serialVersionUID = -2318846919306341272L;

    @FixedField(offset = 58, length = 6)
    private String errorCode;

    /**
     * Returns the field errorCode.
     * 
     * @return errorCode String - Get the field.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the field errorCode.
     * 
     * @param errorCode
     *            String - Set the field errorCode.
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
