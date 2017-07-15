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
 * This Class contains the login related information for the Customer.
 * 
 * @author sharduls
 * 
 */
@Entity
@Table(name = "LOGIN_HISTORY")
public class LoginHistory implements Serializable {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3983455653891051415L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "LOGIN_ATTEMPT_TIME")
    private Date loginAttemptTime;

    @Column(name = "LOGIN_SUCCESSFUL")
    private boolean loginSuccessful;

    @Column(name = "ACCOUNT_LOCKED_FROM_FAILED_LOGINS")
    private boolean accountLockedFromFailedLogins;

    @Column(name = "ACCOUNT_LOCKED_FROM_SUCCESSFUL_LOGIN")
    private boolean accountLockedFromSucessfulLogin;

    @Column(name = "IP_ADDRESS")
    private String clientIpAddress;

    @Column(name = "USER_AGENT")
    private String clientUserAgent;

    /**
     * @return the id - Unique Id .
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the field customer.
     * 
     * @return customer Customer - Get the field.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the field customer.
     * 
     * @param customer
     *            Customer - Set the field customer.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the field userName.
     * 
     * @return userName String - Get the field.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the field userName.
     * 
     * @param userName
     *            String - Set the field userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the field loginAttemptTime.
     * 
     * @return loginAttemptTime Date - Get the field.
     */
    public Date getLoginAttemptTime() {
        return loginAttemptTime;
    }

    /**
     * Sets the field loginAttemptTime.
     * 
     * @param loginAttemptTime
     *            Date - Set the field loginAttemptTime.
     */
    public void setLoginAttemptTime(Date loginAttemptTime) {
        this.loginAttemptTime = loginAttemptTime;
    }

    /**
     * Returns the field loginSuccessful.
     * 
     * @return loginSuccessful boolean - Get the field.
     */
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    /**
     * Sets the field loginSuccessful.
     * 
     * @param loginSuccessful
     *            boolean - Set the field loginSuccessful.
     */
    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    /**
     * Returns the field accountLockedFromFailedLogins.
     * 
     * @return accountLockedFromFailedLogins boolean - Get the field.
     */
    public boolean isAccountLockedFromFailedLogins() {
        return accountLockedFromFailedLogins;
    }

    /**
     * Sets the field accountLockedFromFailedLogins.
     * 
     * @param accountLockedFromFailedLogins
     *            boolean - Set the field accountLockedFromFailedLogins.
     */
    public void setAccountLockedFromFailedLogins(boolean accountLockedFromFailedLogins) {
        this.accountLockedFromFailedLogins = accountLockedFromFailedLogins;
    }

    /**
     * Returns the field accountLockedFromSucessfulLogin.
     * 
     * @return accountLockedFromSucessfulLogin boolean - Get the field.
     */
    public boolean isAccountLockedFromSucessfulLogin() {
        return accountLockedFromSucessfulLogin;
    }

    /**
     * Sets the field accountLockedFromSucessfulLogin.
     * 
     * @param accountLockedFromSucessfulLogin
     *            boolean - Set the field accountLockedFromSucessfulLogin.
     */
    public void setAccountLockedFromSucessfulLogin(boolean accountLockedFromSucessfulLogin) {
        this.accountLockedFromSucessfulLogin = accountLockedFromSucessfulLogin;
    }

    /**
     * Returns the field clientIpAddress.
     * 
     * @return clientIpAddress String - Get the field.
     */
    public String getClientIpAddress() {
        return clientIpAddress;
    }

    /**
     * Sets the field clientIpAddress.
     * 
     * @param clientIpAddress
     *            String - Set the field clientIpAddress.
     */
    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    /**
     * Gets the field clientUserAgent.
     * 
     * @return the clientUserAgent
     */
    public String getClientUserAgent() {
        return clientUserAgent;
    }

    /**
     * Sets the parameter clientUserAgent to the field clientUserAgent.
     * 
     * @param clientUserAgent
     *            the clientUserAgent to set
     */
    public void setClientUserAgent(String clientUserAgent) {
        this.clientUserAgent = clientUserAgent;
    }


    /**
     * Gets the field serialversionuid.
     * 
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
