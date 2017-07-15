/**
 * 
 */
package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A DTO which maintains the User Properties.
 * 
 * @author sharduls
 * 
 */
public class UserConfigurationProperties implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8492313712198867598L;

    private Integer id;

    @NotBlank
    @Length(max = 20)
    private String userId;

    @NotBlank
    @Length(max = 50)
    private String firstName;

    @NotBlank
    @Length(max = 50)
    private String lastName;

    private String userRoles;

    private String isIssuerUser;

    private String issuerName;

    private String iisn;

    @NotNull
    @NotEmpty
    private Integer[] authorizedRoles;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return userRoles - return the user roles.
     */
    public String getUserRoles() {
        return userRoles;
    }

    /**
     * @param userRoles
     *            - set the user roles
     */
    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * @return authorizedRoles - return any set of authorized roles for the user
     */
    public Integer[] getAuthorizedRoles() {
        return authorizedRoles;
    }

    /**
     * @param authorizedRoles
     *            - set authorized roles for the user
     */
    public void setAuthorizedRoles(Integer[] authorizedRoles) {
        this.authorizedRoles = authorizedRoles;
    }

    /**
     * Returns Y while issuer specific user.
     * 
     * @return isIssuerUser - return Y/N
     * 
     */
    public String getIsIssuerUser() {
        return isIssuerUser;
    }

    /**
     * Set Y while issuer specific user.
     * 
     * @param isIssuerUser
     *            - set Y while issuer specific user
     * 
     */
    public void setIsIssuerUser(String isIssuerUser) {
        this.isIssuerUser = isIssuerUser;
    }

    /**
     * Returns the field issuerName.
     * 
     * @return issuerName String - Get the field.
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * Sets the field issuerName.
     * 
     * @param issuerName
     *            String - Set the field issuerName.
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
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
     * Returns the field id.
     * 
     * @return id Integer - Get the field.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the field id.
     * 
     * @param id
     *            Integer - Set the field id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
