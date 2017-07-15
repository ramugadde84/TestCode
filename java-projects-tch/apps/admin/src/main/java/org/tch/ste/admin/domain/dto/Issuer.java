package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

/**
 * 
 * Issuer DTO - The object that carries issuer information from screen to
 * controller.
 * 
 * @author ramug
 * 
 */
public class Issuer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8694961673301992769L;

    private Integer id;

    private String name;

    private String iid;

    private String iisn;

    private String tokenRequestor;

    /**
     * Returns the field name.
     * 
     * @return name String - Get the field.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the field name.
     * 
     * @param name
     *            String - Set the field name.
     */
    public void setName(String name) {
        this.name = name;
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
     * Returns the field tokenRequestor.
     * 
     * @return tokenRequestor String - Get the field.
     */
    public String getTokenRequestor() {
        return tokenRequestor;
    }

    /**
     * Sets the field tokenRequestor.
     * 
     * @param tokenRequestor
     *            String - Set the field tokenRequestor.
     */
    public void setTokenRequestor(String tokenRequestor) {
        this.tokenRequestor = tokenRequestor;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
