package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

/**
 * DTO to return the response.
 * 
 * @author sharduls
 * 
 */
public class UserConfigurationSaveResponse implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -8209356184151418685L;

    private boolean success;

    private String userIdErrorMsg;

    private String firstNameErrorMsg;

    private String lastNameErrorMsg;

    private String rolesErrorMsg;

    private String iisnErrorMsg;

    private String saveSuccessMsg;

    private UserConfigurationProperties userConfigurationProperties;

    /**
     * @return the userIdErrorMsg
     */
    public String getUserIdErrorMsg() {
        return userIdErrorMsg;
    }

    /**
     * @param userIdErrorMsg
     *            the userIdErrorMsg to set
     */
    public void setUserIdErrorMsg(String userIdErrorMsg) {
        this.userIdErrorMsg = userIdErrorMsg;
    }

    /**
     * @return the firstNameErrorMsg
     */
    public String getFirstNameErrorMsg() {
        return firstNameErrorMsg;
    }

    /**
     * @param firstNameErrorMsg
     *            the firstNameErrorMsg to set
     */
    public void setFirstNameErrorMsg(String firstNameErrorMsg) {
        this.firstNameErrorMsg = firstNameErrorMsg;
    }

    /**
     * @return the lastNameErrorMsg
     */
    public String getLastNameErrorMsg() {
        return lastNameErrorMsg;
    }

    /**
     * @param lastNameErrorMsg
     *            the lastNameErrorMsg to set
     */
    public void setLastNameErrorMsg(String lastNameErrorMsg) {
        this.lastNameErrorMsg = lastNameErrorMsg;
    }

    /**
     * @return the rolesErrorMsg
     */
    public String getRolesErrorMsg() {
        return rolesErrorMsg;
    }

    /**
     * @param rolesErrorMsg
     *            the rolesErrorMsg to set
     */
    public void setRolesErrorMsg(String rolesErrorMsg) {
        this.rolesErrorMsg = rolesErrorMsg;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the iisnErrorMsg
     */
    public String getIisnErrorMsg() {
        return iisnErrorMsg;
    }

    /**
     * @param iisnErrorMsg
     *            the issuer error message
     */
    public void setIisnErrorMsg(String iisnErrorMsg) {
        this.iisnErrorMsg = iisnErrorMsg;
    }

    /**
     * @return the userConfigurationProperties
     */
    public UserConfigurationProperties getUserConfigurationProperties() {
        return userConfigurationProperties;
    }

    /**
     * @param userConfigurationProperties
     *            the userConfigurationProperties to set
     */
    public void setUserConfigurationProperties(UserConfigurationProperties userConfigurationProperties) {
        this.userConfigurationProperties = userConfigurationProperties;
    }

    /**
     * @return saveSuccessMsg - Get save success message to display in user
     *         interface
     */
    public String getSaveSuccessMsg() {
        return saveSuccessMsg;
    }

    /**
     * @param saveSuccessMsg
     *            - Set save success message to display in user interface
     */
    public void setSaveSuccessMsg(String saveSuccessMsg) {
        this.saveSuccessMsg = saveSuccessMsg;
    }

}
