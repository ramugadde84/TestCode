/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pamartheepan
 * 
 */
public class TokenDeactivationResponse {

    @JsonProperty("IISN")
    private String iisn;

    @JsonProperty("PAN")
    private String pan;

    @JsonProperty("DATE")
    private String date;

    @JsonProperty("ARN")
    private String arn;

    @JsonProperty("TOTALNOOFTOKENSDEACTIVATED")
    private int totalNoOfTokensDeactivated;

    /**
     * @return iisn
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @param iisn
     *            String.
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @return pan
     */
    public String getPan() {
        return pan;
    }

    /**
     * @param pan
     *            String.
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     *            String.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return totalNoOfTokensDeactivated
     */
    public int getTotalNoOfTokensDeactivated() {
        return totalNoOfTokensDeactivated;
    }

    /**
     * @param totalNoOfTokensDeactivated
     *            String.
     */
    public void setTotalNoOfTokensDeactivated(int totalNoOfTokensDeactivated) {
        this.totalNoOfTokensDeactivated = totalNoOfTokensDeactivated;
    }

    /**
     * @return arn
     */
    public String getArn() {
        return arn;
    }

    /**
     * @param arn
     *            String.
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

}
