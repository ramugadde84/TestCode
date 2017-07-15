/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 *
 */
public class IssuerEnrollmentUrlRequestObj {
    @JsonProperty("GetIssuerEnrollmentAppURLRequest")
    private GetIssuerEnrollmentAppUrlRequest issuerEnrollmentUrlRequest;

    /**
     * Gets the field issuerEnrollmentUrlRequest. 
     *
     * @return the issuerEnrollmentUrlRequest
     */
    public GetIssuerEnrollmentAppUrlRequest getIssuerEnrollmentUrlRequest() {
        return issuerEnrollmentUrlRequest;
    }

    /**
     * Sets the parameter issuerEnrollmentUrlRequest to the field issuerEnrollmentUrlRequest.
     *
     * @param issuerEnrollmentUrlRequest the issuerEnrollmentUrlRequest to set
     */
    public void setIssuerEnrollmentUrlRequest(GetIssuerEnrollmentAppUrlRequest issuerEnrollmentUrlRequest) {
        this.issuerEnrollmentUrlRequest = issuerEnrollmentUrlRequest;
    }

}
