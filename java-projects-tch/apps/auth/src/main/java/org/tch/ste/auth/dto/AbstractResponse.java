/**
 * 
 */
package org.tch.ste.auth.dto;

import java.util.Map;

/**
 * Abstract class which defines basic fields required for a response object.
 * 
 * @author Karthik.
 * 
 */
public abstract class AbstractResponse {
    private boolean success;

    private String errorMessage;

    private Map<String, String> fieldErrorMessages;

    /**
     * Returns the field success.
     * 
     * @return success boolean - Get the field.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the field success.
     * 
     * @param success
     *            boolean - Set the field success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the field errorMessage.
     * 
     * @return errorMessage String - Get the field.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the field errorMessage.
     * 
     * @param errorMessage
     *            String - Set the field errorMessage.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns the field fieldErrorMessages.
     * 
     * @return fieldErrorMessages Map<String,String> - Get the field.
     */
    public Map<String, String> getFieldErrorMessages() {
        return fieldErrorMessages;
    }

    /**
     * Sets the field fieldErrorMessages.
     * 
     * @param fieldErrorMessages
     *            Map<String,String> - Set the field fieldErrorMessages.
     */
    public void setFieldErrorMessages(Map<String, String> fieldErrorMessages) {
        this.fieldErrorMessages = fieldErrorMessages;
    }

}
