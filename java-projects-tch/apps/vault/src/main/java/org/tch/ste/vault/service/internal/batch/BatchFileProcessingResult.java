/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.Serializable;

/**
 * Container which holds the batch processing result.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileProcessingResult implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7680754611452724062L;

    private boolean success;

    private String errorCode;

    private String errMessage;

    private int recordCount;

    /**
     * @return success boolean - Get the field.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            boolean - Set the field success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return errorCode String - Get the field.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            String - Set the field errorCode.
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return errMessage String - Get the field.
     */
    public String getErrMessage() {
        return errMessage;
    }

    /**
     * @param errMessage
     *            String - Set the field errMessage.
     */
    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    /**
     * Returns the field recordCount.
     * 
     * @return recordCount int - Get the field.
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the field recordCount.
     * 
     * @param recordCount
     *            int - Set the field recordCount.
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
