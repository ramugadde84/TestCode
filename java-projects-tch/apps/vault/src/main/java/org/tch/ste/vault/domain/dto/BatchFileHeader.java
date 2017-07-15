/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import java.io.Serializable;

import org.tch.ste.infra.annotation.FixedField;

/**
 * The header record of the preloaded payment instrument.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileHeader implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2815564566769159215L;

    @FixedField(offset = 0, length = 1)
    private int type;

    @FixedField(offset = 1, length = 30)
    private String fileType;

    @FixedField(offset = 31, length = 14)
    private String timeStamp;

    @FixedField(offset = 45, length = 13)
    private String iid;

    /**
     * Returns the field type.
     * 
     * @return type Integer - Get the field.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the field type.
     * 
     * @param type
     *            Integer - Set the field type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the field fileType.
     * 
     * @return fileType String - Get the field.
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets the field fileType.
     * 
     * @param fileType
     *            String - Set the field fileType.
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Returns the field timeStamp.
     * 
     * @return timeStamp String - Get the field.
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the field timeStamp.
     * 
     * @param timeStamp
     *            String - Set the field timeStamp.
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Returns the field iid.
     * 
     * @return iid String - Get the field.
     */
    public String getIid() {
        return iid;
    }

    /**
     * Sets the field iid.
     * 
     * @param iid
     *            String - Set the field iid.
     */
    public void setIid(String iid) {
        this.iid = iid;
    }

}
