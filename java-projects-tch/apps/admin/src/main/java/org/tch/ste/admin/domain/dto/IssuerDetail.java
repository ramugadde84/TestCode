package org.tch.ste.admin.domain.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * Issuer DTO - The object that carries issuer information from screen to
 * controller.
 * 
 * @author ramug
 * 
 */
public class IssuerDetail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8694961673301992769L;

    private Integer id;

    @NotEmpty
    @Length(max = 100)
    @NotBlank
    private String name;

    @NotEmpty
    private String iid;

    @NotEmpty
    @Length(max = 6)
    @NotBlank
    private String iisn;

    @NotEmpty
    @Length(max = 255)
    @NotBlank
    private String dropzonePath;

    @NotNull
    private Integer[] selectedTokenRequestors;

    @NotNull
    private Integer authMechanism;

    @Length(max = 255)
    private String authEndpoint;

    @Length(max = 255)
    private String accountListEndpoint;

    private boolean disableCredentialsAfterLogin;

    @Length(max = 1000)
    private String authErrorMessage;

    @Length(max = 1000)
    private String authLockoutMessage;

    @Length(max = 255)
    private String fileName;

    @Length(max = 1000)
    private String authenticationAppUrl;

    private String failedAttemptsToTriggerLockout;

    private MultipartFile issuerLogo;

    private String tokenBinMappings = "{}";

    private Map<Integer, Boolean> currentTokenRequestors = new HashMap<Integer, Boolean>();

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
     * Returns the field dropzonePath.
     * 
     * @return dropzonePath String - Get the field.
     */
    public String getDropzonePath() {
        return dropzonePath;
    }

    /**
     * Sets the field dropzonePath.
     * 
     * @param dropzonePath
     *            String - Set the field dropzonePath.
     */
    public void setDropzonePath(String dropzonePath) {
        this.dropzonePath = dropzonePath;
    }

    /**
     * Returns the field selectedTokenRequestors.
     * 
     * @return selectedTokenRequestors Integer[] - Get the field.
     */
    public Integer[] getSelectedTokenRequestors() {
        return selectedTokenRequestors;
    }

    /**
     * Sets the field selectedTokenRequestors.
     * 
     * @param selectedTokenRequestors
     *            Integer[] - Set the field selectedTokenRequestors.
     */
    public void setSelectedTokenRequestors(Integer[] selectedTokenRequestors) {
        this.selectedTokenRequestors = selectedTokenRequestors;
    }

    /**
     * Returns the field authMechanism.
     * 
     * @return authMechanism Integer - Get the field.
     */
    public Integer getAuthMechanism() {
        return authMechanism;
    }

    /**
     * Sets the field authMechanism.
     * 
     * @param authMechanism
     *            Integer - Set the field authMechanism.
     */
    public void setAuthMechanism(Integer authMechanism) {
        this.authMechanism = authMechanism;
    }

    /**
     * Returns the field authEndpoint.
     * 
     * @return authEndpoint String - Get the field.
     */
    public String getAuthEndpoint() {
        return authEndpoint;
    }

    /**
     * Sets the field authEndpoint.
     * 
     * @param authEndpoint
     *            String - Set the field authEndpoint.
     */
    public void setAuthEndpoint(String authEndpoint) {
        this.authEndpoint = authEndpoint;
    }

    /**
     * Returns the field accountListEndpoint.
     * 
     * @return accountListEndpoint String - Get the field.
     */
    public String getAccountListEndpoint() {
        return accountListEndpoint;
    }

    /**
     * Sets the field accountListEndpoint.
     * 
     * @param accountListEndpoint
     *            String - Set the field accountListEndpoint.
     */
    public void setAccountListEndpoint(String accountListEndpoint) {
        this.accountListEndpoint = accountListEndpoint;
    }

    /**
     * Returns the field disableCredentialsAfterLogin.
     * 
     * @return disableCredentialsAfterLogin boolean - Get the field.
     */
    public boolean isDisableCredentialsAfterLogin() {
        return disableCredentialsAfterLogin;
    }

    /**
     * Sets the field disableCredentialsAfterLogin.
     * 
     * @param disableCredentialsAfterLogin
     *            boolean - Set the field disableCredentialsAfterLogin.
     */
    public void setDisableCredentialsAfterLogin(boolean disableCredentialsAfterLogin) {
        this.disableCredentialsAfterLogin = disableCredentialsAfterLogin;
    }

    /**
     * Returns the field authErrorMessage.
     * 
     * @return authErrorMessage String - Get the field.
     */
    public String getAuthErrorMessage() {
        return authErrorMessage;
    }

    /**
     * Sets the field authErrorMessage.
     * 
     * @param authErrorMessage
     *            String - Set the field authErrorMessage.
     */
    public void setAuthErrorMessage(String authErrorMessage) {
        this.authErrorMessage = authErrorMessage;
    }

    /**
     * Returns the field authLockoutMessage.
     * 
     * @return authLockoutMessage String - Get the field.
     */
    public String getAuthLockoutMessage() {
        return authLockoutMessage;
    }

    /**
     * Sets the field authLockoutMessage.
     * 
     * @param authLockoutMessage
     *            String - Set the field authLockoutMessage.
     */
    public void setAuthLockoutMessage(String authLockoutMessage) {
        this.authLockoutMessage = authLockoutMessage;
    }

    /**
     * Returns the field failedAttemptsToTriggerLockout.
     * 
     * @return failedAttemptsToTriggerLockout Integer - Get the field.
     */
    public String getFailedAttemptsToTriggerLockout() {
        return failedAttemptsToTriggerLockout;
    }

    /**
     * Sets the field failedAttemptsToTriggerLockout.
     * 
     * @param failedAttemptsToTriggerLockout
     *            Integer - Set the field failedAttemptsToTriggerLockout.
     */
    public void setFailedAttemptsToTriggerLockout(String failedAttemptsToTriggerLockout) {
        this.failedAttemptsToTriggerLockout = failedAttemptsToTriggerLockout;
    }

    /**
     * Returns the field tokenBinMappings.
     * 
     * @return tokenBinMappings String - Get the field.
     */
    public String getTokenBinMappings() {
        return tokenBinMappings;
    }

    /**
     * Sets the field tokenBinMappings.
     * 
     * @param tokenBinMappings
     *            String - Set the field tokenBinMappings.
     */
    public void setTokenBinMappings(String tokenBinMappings) {
        this.tokenBinMappings = tokenBinMappings;
    }

    /**
     * Returns the field currentTokenRequestors.
     * 
     * @return currentTokenRequestors Map<Integer,Boolean> - Get the field.
     */
    public Map<Integer, Boolean> getCurrentTokenRequestors() {
        return currentTokenRequestors;
    }

    /**
     * Sets the field currentTokenRequestors.
     * 
     * @param currentTokenRequestors
     *            Map<Integer,Boolean> - Set the field currentTokenRequestors.
     */
    public void setCurrentTokenRequestors(Map<Integer, Boolean> currentTokenRequestors) {
        this.currentTokenRequestors = currentTokenRequestors;
    }

    /**
     * Returns the field issuerLogo.
     * 
     * @return issuerLogo MultipartFile - Get the field.
     */
    public MultipartFile getIssuerLogo() {
        return issuerLogo;
    }

    /**
     * Sets the field issuerLogo.
     * 
     * @param issuerLogo
     *            MultipartFile - Set the field issuerLogo.
     */
    public void setIssuerLogo(MultipartFile issuerLogo) {
        this.issuerLogo = issuerLogo;
    }

    /**
     * Returns the field issuerLogo fileName.
     * 
     * @return issuerLogo fileName - Get the field.
     */

    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the field issuerLogo.
     * 
     * @param fileName
     *            of issuerLogo - Set the field File_Name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the field authenticationAppUrl.
     * 
     * @return authenticationAppUrl String - Get the field.
     */
    public String getAuthenticationAppUrl() {
        return authenticationAppUrl;
    }

    /**
     * Sets the field authenticationAppUrl.
     * 
     * @param authenticationAppUrl
     *            String - Set the field authenticationAppUrl.
     */
    public void setAuthenticationAppUrl(String authenticationAppUrl) {
        this.authenticationAppUrl = authenticationAppUrl;
    }
}
