/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 *
 */
public class IssuerEnrollmentUrlResponseObj {

    @JsonProperty("GetIssuerEnrollmentAppURLResponse")
    private GetIssuerEnrollmentAppUrlResponse issuerEnrollmentUrlResponse;

    /**
     * Gets the field issuerEnrollmentUrlResponse. 
     *
     * @return the issuerEnrollmentUrlResponse
     */
    public GetIssuerEnrollmentAppUrlResponse getIssuerEnrollmentUrlResponse() {
        return issuerEnrollmentUrlResponse;
    }

    /**
     * Sets the parameter issuerEnrollmentUrlResponse to the field issuerEnrollmentUrlResponse.
     *
     * @param issuerEnrollmentUrlResponse the issuerEnrollmentUrlResponse to set
     */
    public void setIssuerEnrollmentUrlResponse(GetIssuerEnrollmentAppUrlResponse issuerEnrollmentUrlResponse) {
        this.issuerEnrollmentUrlResponse = issuerEnrollmentUrlResponse;
    }
}
