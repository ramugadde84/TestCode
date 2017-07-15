/**
 * 
 */
package org.tch.ste.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kjanani
 * 
 */
public class ActivatePaymentRequest {

    @JsonProperty("TRID")
    private String trId;

    @JsonProperty("ARN")
    private String arn;

    @JsonProperty("IISN")
    private String iisn;

    @JsonProperty("CIID")
    private String ciid;

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
     * @return the ciid
     */
    public String getCiid() {
        return ciid;
    }

    /**
     * @param ciid
     *            the ciid to set
     */
    public void setCiid(String ciid) {
        this.ciid = ciid;
    }

    /**
     * @return the arn
     */
    public String getArn() {
        return arn;
    }

    /**
     * @param arn the arn to set
     */
    public void setArn(String arn) {
        this.arn = arn;
    }
}
