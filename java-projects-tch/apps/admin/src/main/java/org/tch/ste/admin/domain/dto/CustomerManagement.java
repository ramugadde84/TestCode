/**
 * 
 */
package org.tch.ste.admin.domain.dto;

/**
 * @author anus
 * 
 */
public class CustomerManagement {
    private Integer id;

    private String userId;

    private String issuerName;

    private String authenticated;

    private String iisn;

    private boolean locked;

    /**
     * Gets the field id.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the parameter id to the field id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the field userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the parameter userName to the field userName.
     * 
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the field issuerName.
     * 
     * @return the issuerName
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Sets the parameter issuerName to the field issuerName.
     * 
     * @param issuerName
     *            the issuerName to set
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
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

    /**
     * Gets the field authenticated.
     * 
     * @return the authenticated
     */
    public String getAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the parameter authenticated to the field authenticated.
     * 
     * @param authenticated
     *            the authenticated to set
     */
    public void setAuthenticated(String authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Gets the field locked.
     * 
     * @return the locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the parameter locked to the field locked.
     * 
     * @param locked
     *            the locked to set
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
