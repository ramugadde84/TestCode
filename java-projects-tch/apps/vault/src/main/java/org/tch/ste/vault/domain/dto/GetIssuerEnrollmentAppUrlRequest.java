/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class GetIssuerEnrollmentAppUrlRequest {
    @JsonProperty("MessageHeader")
    private MessageHeader messageHeader;

    @JsonProperty("GetIssuerEnrollmentAppURLRequestBody")
    private GetIssuerEnrollmentAppUrlRequestBody requestBody;

    /**
     * Returns the field messageHeader.
     * 
     * @return messageHeader MessageHeader - Get the field.
     */
    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    /**
     * Sets the field messageHeader.
     * 
     * @param messageHeader
     *            MessageHeader - Set the field messageHeader.
     */
    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    /**
     * Returns the field requestBody.
     * 
     * @return requestBody GetIssuerEnrollmentAppUrlRequestBody - Get the field.
     */
    public GetIssuerEnrollmentAppUrlRequestBody getRequestBody() {
        return requestBody;
    }

    /**
     * Sets the field requestBody.
     * 
     * @param requestBody
     *            GetIssuerEnrollmentAppUrlRequestBody - Set the field
     *            requestBody.
     */
    public void setRequestBody(GetIssuerEnrollmentAppUrlRequestBody requestBody) {
        this.requestBody = requestBody;
    }

}
