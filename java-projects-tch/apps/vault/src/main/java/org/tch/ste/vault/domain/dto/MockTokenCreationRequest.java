/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class MockTokenCreationRequest {

    @JsonProperty("TRID")
    private String trId;

    @JsonProperty("ARNID")
    private String arnId;
    @JsonProperty("IISN")
    private String iisn;
    
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

}
