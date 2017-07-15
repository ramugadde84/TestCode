/**
 * 
 */
package org.tch.ste.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pamartheepan
 * 
 */
public class DeactivatePaymentInstrumentResponse {

    @JsonProperty("IISN")
    private String iisn;

    @JsonProperty("TRID")
    private String trid;

    @JsonProperty("CIID")
    private String ciid;

    @JsonProperty("ARN")
    private String arn;

    @JsonProperty("NumberOfDeactivatedTokens")
    private int numDeactivatedTokens;

    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ErrorMessage")
    private String errorMsg;

    /**
     * Returns the field iisn.
     * 
     * @return iisn String - Get the field.
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * Sets the field iisn.
     * 
     * @param iisn
     *            String - Set the field iisn.
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * Returns the field trid.
     * 
     * @return trid String - Get the field.
     */
    public String getTrid() {
        return trid;
    }

    /**
     * Sets the field trid.
     * 
     * @param trid
     *            String - Set the field trid.
     */
    public void setTrid(String trid) {
        this.trid = trid;
    }

    /**
     * Returns the field ciid.
     * 
     * @return ciid String - Get the field.
     */
    public String getCiid() {
        return ciid;
    }

    /**
     * Sets the field ciid.
     * 
     * @param ciid
     *            String - Set the field ciid.
     */
    public void setCiid(String ciid) {
        this.ciid = ciid;
    }

    /**
     * Returns the field arn.
     * 
     * @return arn String - Get the field.
     */
    public String getArn() {
        return arn;
    }

    /**
     * Sets the field arn.
     * 
     * @param arn
     *            String - Set the field arn.
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

    /**
     * Returns the field responseCode.
     * 
     * @return responseCode String - Get the field.
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the field responseCode.
     * 
     * @param responseCode
     *            String - Set the field responseCode.
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Returns the field errorMsg.
     * 
     * @return errorMsg String - Get the field.
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets the field errorMsg.
     * 
     * @param errorMsg
     *            String - Set the field errorMsg.
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * Returns the field numDeactivatedTokens.
     * 
     * @return numDeactivatedTokens int - Get the field.
     */
    public int getNumDeactivatedTokens() {
        return numDeactivatedTokens;
    }

    /**
     * Sets the field numDeactivatedTokens.
     * 
     * @param numDeactivatedTokens
     *            int - Set the field numDeactivatedTokens.
     */
    public void setNumDeactivatedTokens(int numDeactivatedTokens) {
        this.numDeactivatedTokens = numDeactivatedTokens;
    }
}
