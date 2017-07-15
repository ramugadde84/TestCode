/**
 * 
 */
package org.tch.ste.vault.domain.dto;

/**
 * @author anus
 * 
 */
public class Errors {
    private String errorCode;
    private String errorMessage;

    /**
     * Gets the field errorCode.
     * 
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the parameter errorCode to the field errorCode.
     * 
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the field errorMessage.
     * 
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the parameter errorMessage to the field errorMessage.
     * 
     * @param errorMessage
     *            the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
