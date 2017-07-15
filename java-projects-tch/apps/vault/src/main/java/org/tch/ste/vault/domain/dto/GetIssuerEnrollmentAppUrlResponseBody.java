/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class GetIssuerEnrollmentAppUrlResponseBody {
    @JsonProperty("ResponseCode")
    private String responseCode;
    @JsonProperty("TRID")
    private Integer trid;
    @JsonProperty("CIID")
    private String ciid;
    @JsonProperty("IISN")
    private Integer iisn;
    @JsonProperty("IssuerURL")
    private String issuerUrl;
    private Errors errorlist;

    /**
     * Gets the field responseCode.
     * 
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the parameter responseCode to the field responseCode.
     * 
     * @param responseCode
     *            the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Gets the field iisn.
     * 
     * @return the iisn
     */
    public Integer getIisn() {
        return iisn;
    }

    /**
     * Sets the parameter iisn to the field iisn.
     * 
     * @param iisn
     *            the iisn to set
     */
    public void setIisn(Integer iisn) {
        this.iisn = iisn;
    }

    /**
     * Gets the field issuerUrl.
     * 
     * @return the issuerUrl
     */
    public String getIssuerUrl() {
        return issuerUrl;
    }

    /**
     * Sets the parameter issuerUrl to the field issuerUrl.
     * 
     * @param issuerUrl
     *            the issuerUrl to set
     */
    public void setIssuerUrl(String issuerUrl) {
        this.issuerUrl = issuerUrl;
    }

    /**
     * Gets the field errorlist.
     * 
     * @return the errorlist
     */
    public Errors getErrorlist() {
        return errorlist;
    }

    /**
     * Sets the parameter errorlist to the field errorlist.
     * 
     * @param errorlist
     *            the errorlist to set
     */
    public void setErrorlist(Errors errorlist) {
        this.errorlist = errorlist;
    }

    /**
     * Gets the field trid.
     * 
     * @return the trid
     */
    public Integer getTrid() {
        return trid;
    }

    /**
     * Sets the parameter trid to the field trid.
     * 
     * @param trid
     *            the trid to set
     */
    public void setTrid(Integer trid) {
        this.trid = trid;
    }

    /**
     * Gets the field ciid.
     * 
     * @return the ciid
     */
    public String getCiid() {
        return ciid;
    }

    /**
     * Sets the parameter ciid to the field ciid.
     * 
     * @param ciid
     *            the ciid to set
     */
    public void setCiid(String ciid) {
        this.ciid = ciid;
    }

}
