/**
 * 
 */
package org.tch.ste.admin.domain.dto;

/**
 * @author anus
 * 
 */
public class CustomerLockResponse {
    private Integer id;
    private boolean success = false;
    private String errorMsg;

    /**
     * Gets the field id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the parameter id to the field id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * Gets the field errorMsg.
     * 
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the parameter errorMsg to the field errorMsg.
     * 
     * @param errorMsg
     *            the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
