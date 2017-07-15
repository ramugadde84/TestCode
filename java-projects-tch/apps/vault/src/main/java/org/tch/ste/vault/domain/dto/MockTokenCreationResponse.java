/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kjanani
 * 
 */
public class MockTokenCreationResponse {

    String token;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("TRID")
    private String trId;

    @JsonProperty("ARNID")
    private String arnId;
    
    @JsonProperty("CIID")
    private String ciid;

    /**
     * @return the ciid
     */
    public String getCiid() {
        return ciid;
    }

    /**
     * @param ciid the ciid to set
     */
    public void setCiid(String ciid) {
        this.ciid = ciid;
    }

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

    private Errors errorlist;

    /**
     * @return the trId
     */
    public String getTrId() {
        return trId;
    }

    /**
     * @param trId
     *            the trId to set
     */
    public void setTrId(String trId) {
        this.trId = trId;
    }

    /**
     * @return the arnId
     */
    public String getArnId() {
        return arnId;
    }

    /**
     * @param arnId
     *            the arnId to set
     */
    public void setArnId(String arnId) {
        this.arnId = arnId;
    }

    /**
     * @return the iisn
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @param iisn
     *            the iisn to set
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @return the errorlist
     */
    public Errors getErrorlist() {
        return errorlist;
    }

    /**
     * @param errorlist
     *            the errorlist to set
     */
    public void setErrorlist(Errors errorlist) {
        this.errorlist = errorlist;
    }

    @JsonProperty("IISN")
    private String iisn;

}
