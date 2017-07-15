/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class GetIssuerEnrollmentAppUrlResponse {
    @JsonProperty("MessageHeader")
    private MessageHeader messageHeader = new MessageHeader();

    @JsonProperty("GetIssuerEnrollmentAppURLResponseBody")
    private GetIssuerEnrollmentAppUrlResponseBody responseBody = new GetIssuerEnrollmentAppUrlResponseBody();

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
     * Returns the field responseBody.
     * 
     * @return responseBody GetIssuerEnrollmentAppUrlResponseBody - Get the
     *         field.
     */
    public GetIssuerEnrollmentAppUrlResponseBody getResponseBody() {
        return responseBody;
    }

    /**
     * Sets the field responseBody.
     * 
     * @param responseBody
     *            GetIssuerEnrollmentAppUrlResponseBody - Set the field
     *            responseBody.
     */
    public void setResponseBody(GetIssuerEnrollmentAppUrlResponseBody responseBody) {
        this.responseBody = responseBody;
    }

}
