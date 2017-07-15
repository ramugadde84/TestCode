package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class stores the information about Batch Files .
 * 
 * @author sharduls
 * 
 */
@Entity
@Table(name = "BATCH_FILE_HISTORIES")
public class BatchFileHistory implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 7133615853840839955L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BATCH_FILE_TYPE_ID", referencedColumnName = "ID", nullable = false)
    private BatchFileType batchFileType;

    @Column(name = "PROCESSING_END_TIME")
    private Date processingEndTime;

    @Column(name = "PROCESSING_START_TIME")
    private Date processingStartTime;

    @Column(name = "BATCH_FILE_NAME")
    private String batchFileName;

    @Column(name = "RECORD_COUNT")
    private int recordCount;

    @Column(name = "SUCCESSFULLY_PROCESSED")
    private int sucessfullyProcessed;

    /**
     * Returns the field id.
     * 
     * @return id int - Get the field.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the field id.
     * 
     * @param id
     *            int - Set the field id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the field batchFileType.
     * 
     * @return batchFileType BatchFileType - Get the field.
     */
    public BatchFileType getBatchFileType() {
        return batchFileType;
    }

    /**
     * Sets the field batchFileType.
     * 
     * @param batchFileType
     *            BatchFileType - Set the field batchFileType.
     */
    public void setBatchFileType(BatchFileType batchFileType) {
        this.batchFileType = batchFileType;
    }

    /**
     * Returns the field processingEndTime.
     * 
     * @return processingEndTime Date - Get the field.
     */
    public Date getProcessingEndTime() {
        return processingEndTime;
    }

    /**
     * Sets the field processingEndTime.
     * 
     * @param processingEndTime
     *            Date - Set the field processingEndTime.
     */
    public void setProcessingEndTime(Date processingEndTime) {
        this.processingEndTime = processingEndTime;
    }

    /**
     * Returns the field processingStartTime.
     * 
     * @return processingStartTime Date - Get the field.
     */
    public Date getProcessingStartTime() {
        return processingStartTime;
    }

    /**
     * Sets the field processingStartTime.
     * 
     * @param processingStartTime
     *            Date - Set the field processingStartTime.
     */
    public void setProcessingStartTime(Date processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    /**
     * Returns the field recordCount.
     * 
     * @return recordCount int - Get the field.
     */
    public int getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the field recordCount.
     * 
     * @param recordCount
     *            int - Set the field recordCount.
     */
    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    /**
     * Returns the field sucessfullyProcessed.
     * 
     * @return sucessfullyProcessed int - Get the field.
     */
    public int getSucessfullyProcessed() {
        return sucessfullyProcessed;
    }

    /**
     * Sets the field sucessfullyProcessed.
     * 
     * @param sucessfullyProcessed
     *            int - Set the field sucessfullyProcessed.
     */
    public void setSucessfullyProcessed(int sucessfullyProcessed) {
        this.sucessfullyProcessed = sucessfullyProcessed;
    }

    /**
     * Returns the field batchFileName.
     * 
     * @return batchFileName String - Get the field.
     */
    public String getBatchFileName() {
        return batchFileName;
    }

    /**
     * Sets the field batchFileName.
     * 
     * @param batchFileName
     *            String - Set the field batchFileName.
     */
    public void setBatchFileName(String batchFileName) {
        this.batchFileName = batchFileName;
    }

}
