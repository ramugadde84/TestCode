/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import org.tch.ste.vault.constant.ValidationErrorCode;

/**
 * Contains the result of the validation.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileValidationResult {
    private boolean success;

    private ValidationErrorCode errorCode;

    private String errorMessage;

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
     * @return errorCode ValidationErrorCode - Get the field.
     */
    public ValidationErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            ValidationErrorCode - Set the field errorCode.
     */
    public void setErrorCode(ValidationErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return errorMessage String - Get the field.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     *            String - Set the field errorMessage.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
