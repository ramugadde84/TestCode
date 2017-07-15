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
 * Customer DTO - The object that carries Customer information from screen to
 * controller.
 * 
 */
@Entity
@Table(name = "CUSTOMERS")
public class Customer implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9177303917547065462L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "ISSUER_CUSTOMER_ID")
    private String issuerCustomerId;

    @Column(name = "REGISTRATION_TOKEN")
    private String registrationToken;

    @Column(name = "HASHED_PASSWORD")
    private String hashedPassword;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @Column(name = "LAST_SUCCESSFUL_LOGIN", nullable = false)
    private Date lastSuccessfulLogin;

    @Column(name = "LAST_FAILED_LOGIN", nullable = false)
    private Date lastFailedLogin;

    @Column(name = "ACCOUNT_LOCKED")
    private Boolean accountLocked;

    @Column(name = "ACCOUNT_LOCKED_REASON_CODE")
    private String accountLockedReasonCode;

    @Column(name = "ACCOUNT_LOCKED_AT", nullable = false)
    private Date accountLockedAt;

    @Column(name = "LAST_PASSWORD_CHANGE_DATETIME", nullable = false)
    private Date lastPasswordChange;

    @Column(name = "FAILED_LOGIN_COUNT")
    private Integer failedLoginCount;

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
     * Gets the field userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the parameter userName to the field userName.
     * 
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the field issuerCustomerId.
     * 
     * @return the issuerCustomerId
     */
    public String getIssuerCustomerId() {
        return issuerCustomerId;
    }

    /**
     * Sets the parameter issuerCustomerId to the field issuerCustomerId.
     * 
     * @param issuerCustomerId
     *            the issuerCustomerId to set
     */
    public void setIssuerCustomerId(String issuerCustomerId) {
        this.issuerCustomerId = issuerCustomerId;
    }

    /**
     * Gets the field registrationToken.
     * 
     * @return the registrationToken
     */
    public String getRegistrationToken() {
        return registrationToken;
    }

    /**
     * Sets the parameter registrationToken to the field registrationToken.
     * 
     * @param registrationToken
     *            the registrationToken to set
     */
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    /**
     * Gets the field hashedPassword.
     * 
     * @return the hashedPassword
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets the parameter hashedPassword to the field hashedPassword.
     * 
     * @param hashedPassword
     *            the hashedPassword to set
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets the field passwordSalt.
     * 
     * @return the passwordSalt
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Sets the parameter passwordSalt to the field passwordSalt.
     * 
     * @param passwordSalt
     *            the passwordSalt to set
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    /**
     * Gets the field lastSuccessfulLogin.
     * 
     * @return the lastSuccessfulLogin
     */
    public Date getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    /**
     * Sets the parameter lastSuccessfulLogin to the field lastSuccessfulLogin.
     * 
     * @param lastSuccessfulLogin
     *            the lastSuccessfulLogin to set
     */
    public void setLastSuccessfulLogin(Date lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    /**
     * Gets the field lastFailedLogin.
     * 
     * @return the lastFailedLogin
     */
    public Date getLastFailedLogin() {
        return lastFailedLogin;
    }

    /**
     * Sets the parameter lastFailedLogin to the field lastFailedLogin.
     * 
     * @param lastFailedLogin
     *            the lastFailedLogin to set
     */
    public void setLastFailedLogin(Date lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
    }

    /**
     * Returns the field accountLocked.
     * 
     * @return accountLocked Boolean - Get the field.
     */
    public Boolean getAccountLocked() {
        return accountLocked;
    }

    /**
     * Sets the field accountLocked.
     * 
     * @param accountLocked
     *            Boolean - Set the field accountLocked.
     */
    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    /**
     * Gets the field accountLockedReasonCode.
     * 
     * @return the accountLockedReasonCode
     */
    public String getAccountLockedReasonCode() {
        return accountLockedReasonCode;
    }

    /**
     * Sets the parameter accountLockedReasonCode to the field
     * accountLockedReasonCode.
     * 
     * @param accountLockedReasonCode
     *            the accountLockedReasonCode to set
     */
    public void setAccountLockedReasonCode(String accountLockedReasonCode) {
        this.accountLockedReasonCode = accountLockedReasonCode;
    }

    /**
     * Gets the field accountLockedAt.
     * 
     * @return the accountLockedAt
     */
    public Date getAccountLockedAt() {
        return accountLockedAt;
    }

    /**
     * Sets the parameter accountLockedAt to the field accountLockedAt.
     * 
     * @param accountLockedAt
     *            the accountLockedAt to set
     */
    public void setAccountLockedAt(Date accountLockedAt) {
        this.accountLockedAt = accountLockedAt;
    }

    /**
     * Gets the field lastPasswordChange.
     * 
     * @return the lastPasswordChange
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Sets the parameter lastPasswordChange to the field lastPasswordChange.
     * 
     * @param lastPasswordChange
     *            the lastPasswordChange to set
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * @return the failedLoginCount
     */
    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    /**
     * @param failedLoginCount
     *            the failedLoginCount to set
     */
    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }
}
