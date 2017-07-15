/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pamartheepan
 * 
 */
public class TokenDeactivationRequest {

    @JsonProperty("IISN")
    private String iisn;

    @JsonProperty("PAN")
    private String pan;

    @JsonProperty("DATE")
    private String Date;

    @JsonProperty("ARN")
    private String arn;

    /**
     * 
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
     * @return Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * @param date
     *            String.
     */
    public void setDate(String date) {
        Date = date;
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
