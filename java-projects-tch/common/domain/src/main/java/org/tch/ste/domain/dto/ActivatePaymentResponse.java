/**
 * 
 */
package org.tch.ste.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kjanani
 * 
 */
public class ActivatePaymentResponse {

    @JsonProperty("ResponseCode")
    private String responseCode;

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode
     *            the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty("MASKEDTOKEN")
    private String maskedToken;

    /**
     * @return the maskedToken
     */
    public String getMaskedToken() {
        return maskedToken;
    }

    /**
     * @param maskedToken
     *            the maskedToken to set
     */
    public void setMaskedToken(String maskedToken) {
        this.maskedToken = maskedToken;
    }

}
