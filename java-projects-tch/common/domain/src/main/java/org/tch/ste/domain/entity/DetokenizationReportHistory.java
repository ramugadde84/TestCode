/**
 * 
 */
package org.tch.ste.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * This class contains inforamtion about the DeTokenization report histories.3
 * 
 * @author anus
 * 
 */
@Entity
@Table(name = "DETOKENIZATION_REPORT_HISTORIES")
public class DetokenizationReportHistory implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -1846933141621576476L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "DETOKENIZATION_REQUEST_ID")
    private int detokenizationRequestId;

    @Column(name = "IISN")
    private String iisn;

    /**
     * Gets the field id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the parameter id to the field id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the field detokenizationRequestId.
     * 
     * @return the detokenizationRequestId
     */
    public int getDetokenizationRequestId() {
        return detokenizationRequestId;
    }

    /**
     * Sets the parameter detokenizationRequestId to the field
     * detokenizationRequestId.
     * 
     * @param detokenizationRequestId
     *            the detokenizationRequestId to set
     */
    public void setDetokenizationRequestId(int detokenizationRequestId) {
        this.detokenizationRequestId = detokenizationRequestId;
    }

    /**
     * Gets the field iisn.
     * 
     * @return the iisn
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * Sets the parameter iisn to the field iisn.
     * 
     * @param iisn
     *            the iisn to set
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }
}
