/**
 * 
 */
package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

/**
 * Base class for response.
 * 
 * @author anus
 * 
 */
public class TokenRequestorResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1720335927101748153L;

    private boolean success;

    private String errorMessage;

    /**
     * Gets the field success.
     * 
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the parameter success to the field success.
     * 
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
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
