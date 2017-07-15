/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author anus
 * 
 */
public class GetIssuerEnrollmentAppUrlRequestBody implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6172124414444332867L;
    @JsonProperty("TRID")
    private Integer trid;
    @JsonProperty("CIID")
    private String ciid;
    @JsonProperty("IISN")
    private Integer iisn;

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
