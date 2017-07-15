package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity maps to ISSUER_CONFIGURATIONS table. ISSUER_CONFIGURATIONS table
 * contains the configuration attributes for each issuer
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "ISSUER_CONFIGURATIONS")
public class IssuerConfiguration implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7666827699259109743L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ACCOUNT_LIST_ENDPOINT")
    private String accountListEndpoint;

    @Column(name = "AUTH_ENDPOINT")
    private String authEndpoint;

    @Column(name = "AUTHENICATION_APP_URL")
    private String authenticationAppUrl;

    @Column(name = "AUTHENTICATION_TYPE")
    private Integer authenticationType;

    @Column(name = "AUTH_ERROR_MESSAGE")
    private String authErrorMessage;

    @Column(name = "AUTH_LOCKOUT_MESSAGE")
    private String authLockoutMessage;

    @Column(name = "DISABLE_CREDENTIAL_AFTER_LOGIN")
    private Boolean disableCredentialAfterLogin;

    @Column(name = "DROPZONE_PATH")
    private String dropzonePath;

    @Column(name = "FAILED_LOGINS_TO_LOCKOUT")
    private Integer failedLoginsToLockout;

    @Column(name = "IID")
    private String iid;

    @Column(name = "IISN")
    private String iisn;

    @Column(name = "ISSUER_NAME")
    private String issuerName;

    @Column(name = "LOGO_FILE_NAME")
    private String fileName;

    /**
     * @return String - The filename.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            String - The file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * List of Issuer Token Requestor mapping.
     */
    @OneToMany(mappedBy = "issuerConfiguration", cascade = CascadeType.PERSIST)
    private List<IssuerTokenRequestors> issuerTokenRequestors;

    @Column(name = "LOGO_IMAGE")
    @Lob
    private byte[] logoImage;

    /**
     * @return String accountListEndpoint - gets the Account list endpoint web
     *         service for the issuer if using option 5 authentication
     */
    public String getAccountListEndpoint() {
        return accountListEndpoint;
    }

    /**
     * @return String authEndpoint - gets the Authentication endpoint web
     *         service for the issuer if using option 5 authentication
     */
    public String getAuthEndpoint() {
        return authEndpoint;
    }

    /**
     * @return String authenticationAppUrl- gets Authentication application URL
     *         for issuers
     */
    public String getAuthenticationAppUrl() {
        return authenticationAppUrl;
    }

    /**
     * @return Integer authenticationType - gets the authentication option used
     *         by the issuer
     */
    public Integer getAuthenticationType() {
        return authenticationType;
    }

    /**
     * @return String authErrorMessage - gets Error message displayed if an
     *         error occurs during authentication
     */
    public String getAuthErrorMessage() {
        return authErrorMessage;
    }

    /**
     * @return String authLockoutMessage - gets the Message displayed when an
     *         authentication credential is locked out
     */
    public String getAuthLockoutMessage() {
        return authLockoutMessage;
    }

    /**
     * @return Boolean disableCredentialAfterLogin - gets the customer
     *         credential should be disabled after the login occurs
     */
    public Boolean getDisableCredentialAfterLogin() {
        return disableCredentialAfterLogin;
    }

    /**
     * @return String dropzonePath - gets the Root path of the dropzone for the
     *         issuer
     */
    public String getDropzonePath() {
        return dropzonePath;
    }

    /**
     * @return Integer failedLoginsToLockout - gets the number of failed logins
     *         to allow before locking out the credential
     */
    public Integer getFailedLoginsToLockout() {
        return failedLoginsToLockout;
    }

    /**
     * @return Integer id - gets the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return String iid- gets the iid of the issuer
     */
    public String getIid() {
        return iid;
    }

    /**
     * @return String iisn- gets the iisn of the issuer
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @return String issuerName- gets the name of the issuer
     */
    public String getIssuerName() {
        return issuerName;
    }

    /**
     * 
     * @return Issuer Token requestorMapping.
     */
    public List<IssuerTokenRequestors> getIssuerTokenRequestors() {
        return issuerTokenRequestors;
    }

    /**
     * @return Byte logoImage - gets Binary storage of the issuer image
     */
    public byte[] getLogoImage() {
        return logoImage;
    }

    /**
     * @param accountListEndpoint
     *            - sets the Account list endpoint web service for the issuer if
     *            using option 5 authentication
     * 
     * 
     */
    public void setAccountListEndpoint(String accountListEndpoint) {
        this.accountListEndpoint = accountListEndpoint;
    }

    /**
     * @param authEndpoint
     *            - sets the Authentication endpoint web service for the issuer
     *            if using option 5 authentication
     * 
     * 
     */
    public void setAuthEndpoint(String authEndpoint) {
        this.authEndpoint = authEndpoint;
    }

    /**
     * @param authenticationAppUrl
     *            - sets Authentication application URL for issuers
     * 
     * 
     */
    public void setAuthenticationAppUrl(String authenticationAppUrl) {
        this.authenticationAppUrl = authenticationAppUrl;
    }

    /**
     * @param authenticationType
     *            - sets the authentication option used by the issuer
     * 
     * 
     */
    public void setAuthenticationType(Integer authenticationType) {
        this.authenticationType = authenticationType;
    }

    /**
     * @param authErrorMessage
     *            - sets Error message displayed if an error occurs during
     *            authentication
     * 
     * 
     */
    public void setAuthErrorMessage(String authErrorMessage) {
        this.authErrorMessage = authErrorMessage;
    }

    /**
     * @param authLockoutMessage
     *            - sets the Message displayed when an authentication credential
     *            is locked out
     * 
     * 
     */
    public void setAuthLockoutMessage(String authLockoutMessage) {
        this.authLockoutMessage = authLockoutMessage;
    }

    /**
     * @param disableCredentialAfterLogin
     *            - Sets the customer credential should be disabled after the
     *            login occurs
     * 
     * 
     */
    public void setDisableCredentialAfterLogin(Boolean disableCredentialAfterLogin) {
        this.disableCredentialAfterLogin = disableCredentialAfterLogin;
    }

    /**
     * @param dropzonePath
     *            - sets the Root path of the dropzone for the issuer
     * 
     * 
     */
    public void setDropzonePath(String dropzonePath) {
        this.dropzonePath = dropzonePath;
    }

    /**
     * @param failedLoginsToLockout
     *            - sets the number of failed logins to allow before locking out
     *            the credential
     * 
     * 
     */
    public void setFailedLoginsToLockout(Integer failedLoginsToLockout) {
        this.failedLoginsToLockout = failedLoginsToLockout;
    }

    /**
     * @param id
     *            - sets the field id
     * 
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param iid
     *            - sets the iid of the issuer
     * 
     * 
     */
    public void setIid(String iid) {
        this.iid = iid;
    }

    /**
     * @param iisn
     *            - sets the iisn of the issuer
     * 
     * 
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @param issuerName
     *            - sets the name of the issuer
     * 
     * 
     */
    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    /**
     * 
     * @param issuerTokenRequestors
     *            list of Issuer token requestor mapping.
     */
    public void setIssuerTokenRequestors(List<IssuerTokenRequestors> issuerTokenRequestors) {
        this.issuerTokenRequestors = issuerTokenRequestors;
    }

    /**
     * Sets the field.
     * 
     * @param logoImage
     *            sets Binary storage of the issuer image
     */
    public void setLogoImage(byte[] logoImage) {
        this.logoImage = logoImage;
    }

}
