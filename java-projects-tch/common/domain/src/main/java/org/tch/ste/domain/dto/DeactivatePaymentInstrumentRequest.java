/**
 * 
 */
package org.tch.ste.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pamartheepan
 * 
 */
public class DeactivatePaymentInstrumentRequest {

    @JsonProperty("IISN")
    private String iisn;

    @JsonProperty("TRID")
    private String trid;

    @JsonProperty("CIID")
    private String ciid;

    @JsonProperty("ARN")
    private String arn;

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
}
