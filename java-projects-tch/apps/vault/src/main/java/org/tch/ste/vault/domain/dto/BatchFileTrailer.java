/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import java.io.Serializable;

import org.tch.ste.infra.annotation.FixedField;

/**
 * Trailer record.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileTrailer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -655590897647844021L;

    @FixedField(offset = 0, length = 1)
    private int type;

    @FixedField(offset = 1, length = 12,overrideType=Long.class)
    private String numRecords;

    @FixedField(offset = 13, length = 6)
    private String errorCode;

    /**
     * Returns the field type.
     * 
     * @return type int - Get the field.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the field type.
     * 
     * @param type
     *            int - Set the field type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the field numRecords.
     * 
     * @return numRecords String - Get the field.
     */
    public String getNumRecords() {
        return numRecords;
    }

    /**
     * Sets the field numRecords.
     * 
     * @param numRecords
     *            String - Set the field numRecords.
     */
    public void setNumRecords(String numRecords) {
        this.numRecords = numRecords;
    }

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
