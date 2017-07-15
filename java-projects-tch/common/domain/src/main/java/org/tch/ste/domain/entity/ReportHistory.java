/**
 * 
 */
package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This Entity contains last login_history id value.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "REPORT_HISTORY")
public class ReportHistory implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "REPORT_TYPE", nullable = false)
    private String reportType;

    @Column(name = "LAST_REPORT_TIME", nullable = false)
    private Date lastReportTime;

    /**
     * Gets the Id value - primary key.
     * 
     * @return the id value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id value.
     * 
     * @param id
     *            - id value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Report Type.
     * 
     * @return reportType.
     */
    public String getReportType() {
        return reportType;
    }

    /**
     * Sets Report type.
     * 
     * @param reportType
     *            - Report Type.
     */
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /**
     * Last Report Time.
     * 
     * @return - Report Time Date.
     */
    public Date getLastReportTime() {
        return lastReportTime;
    }

    /**
     * Last Report Time.
     * 
     * @param lastReportTime
     *            - last ReportTime.
     */
    public void setLastReportTime(Date lastReportTime) {
        this.lastReportTime = lastReportTime;
    }

}
