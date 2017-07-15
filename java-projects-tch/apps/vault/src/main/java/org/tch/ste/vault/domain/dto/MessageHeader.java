/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class MessageHeader implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4276929996053462151L;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("SourceID")
    private String sourceId;
    @JsonProperty("MessageID")
    private String messageId;
    @JsonProperty("CloudID")
    private String cloudId;

    /**
     * Gets the field version.
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the parameter version to the field version.
     * 
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the field sourceId.
     * 
     * @return the sourceId
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * Sets the parameter sourceId to the field sourceId.
     * 
     * @param sourceId
     *            the sourceId to set
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * Gets the field messageId.
     * 
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the parameter messageId to the field messageId.
     * 
     * @param messageId
     *            the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the field cloudId.
     * 
     * @return the cloudId
     */
    public String getCloudId() {
        return cloudId;
    }

    /**
     * Sets the parameter cloudId to the field cloudId.
     * 
     * @param cloudId
     *            the cloudId to set
     */
    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

}
