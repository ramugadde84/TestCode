package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

/**
 * @author sharduls
 * 
 */
public class UserConfigurationDeleteResponse implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3057705818820820416L;
    private boolean success = false;
    private String deleteMessage;
    private UserConfigurationProperties userConfigurationProperties;

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
     * @return deleteMessage - get message to display in user interface once
     *         user deleted.
     */
    public String getDeleteMessage() {
        return deleteMessage;
    }

    /**
     * @param deleteMessage
     *            - set message to display in user interface once user deleted.
     */
    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

}
