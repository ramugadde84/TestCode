package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity refers to BATCH_FILE_TYPES table. BATCH_FILE_TYPES table defines
 * the batch file types expected by the system with a description of the file.
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "BATCH_FILE_TYPES")
public class BatchFileType implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -4717043162575659745L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @OneToMany(mappedBy = "batchFileType")
    private List<BatchFileHistory> batchFileHistories;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILE_NAME")
    private String fileName;

    /**
     * @return batchFileHistories
     */
    public List<BatchFileHistory> getBatchFileHistories() {
        return batchFileHistories;
    }

    /**
     * @return String description -gets the description of the file
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return String FileName -gets the File name identifier used as the first
     *         portion of the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return Integer id -get the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param batchFileHistories
     *            - sets the batchFileHistories list
     */
    public void setBatchFileHistories(List<BatchFileHistory> batchFileHistories) {
        this.batchFileHistories = batchFileHistories;
    }

    /**
     * @param description
     *            - sets the description of the file
     * 
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param fileName
     *            -sets the File name identifier used as the first portion of
     *            the file name
     * 
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param id
     *            -set the field id
     * 
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
