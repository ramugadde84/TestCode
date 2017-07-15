/**
 * 
 */
package org.tch.ste.admin.domain.dto;

/**
 * A DTO which maintains the vault configuration properties.
 * 
 * @author Karthik.
 * 
 */
public class VaultConfigurationProperties {

    private String switchEndpointUrl;

    private String batchFileRootDirectory;

    private String baseLogLevel;

    private String cloudSwitchId;

    private String vaultSwitchId;

    /**
     * @return switchEndpointUrl String - Get the field.
     */
    public String getSwitchEndpointUrl() {
        return switchEndpointUrl;
    }

    /**
     * @param switchEndpointUrl
     *            String - Set the field switchEndpointUrl.
     */
    public void setSwitchEndpointUrl(String switchEndpointUrl) {
        this.switchEndpointUrl = switchEndpointUrl;
    }

    /**
     * @return batchFileRootDirectory String - Get the field.
     */
    public String getBatchFileRootDirectory() {
        return batchFileRootDirectory;
    }

    /**
     * @param batchFileRootDirectory
     *            String - Set the field batchFileRootDirectory.
     */
    public void setBatchFileRootDirectory(String batchFileRootDirectory) {
        this.batchFileRootDirectory = batchFileRootDirectory;
    }

    /**
     * @return baseLogLevel String - Get the field.
     */
    public String getBaseLogLevel() {
        return baseLogLevel;
    }

    /**
     * @param baseLogLevel
     *            String - Set the field baseLogLevel.
     */
    public void setBaseLogLevel(String baseLogLevel) {
        this.baseLogLevel = baseLogLevel;
    }

    /**
     * @return cloudSwitchId String - Get the field
     */
    public String getCloudSwitchId() {
        return cloudSwitchId;
    }

    /**
     * @param cloudSwitchId
     *            String - set the field cloudSwitchId
     */
    public void setCloudSwitchId(String cloudSwitchId) {
        this.cloudSwitchId = cloudSwitchId;
    }

    /**
     * @return vaultSwitchId String - Get the filed
     */
    public String getVaultSwitchId() {
        return vaultSwitchId;
    }

    /**
     * @param vaultSwitchId
     *            String -Set the filed vaultSwitchId
     */
    public void setVaultSwitchId(String vaultSwitchId) {
        this.vaultSwitchId = vaultSwitchId;
    }
}
