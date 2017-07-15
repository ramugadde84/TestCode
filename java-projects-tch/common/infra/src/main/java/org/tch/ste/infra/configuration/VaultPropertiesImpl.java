/**
 * 
 */
package org.tch.ste.infra.configuration;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.constant.LogLevel;

/**
 * @author pamartheepan
 * 
 */

public class VaultPropertiesImpl implements VaultProperties {

    private Properties vaultProperties;

    private static Logger logger = LoggerFactory.getLogger(VaultPropertiesImpl.class);

    /**
     * Default Constructor.
     * 
     * @param vaultProperties
     *            Properties - The properties.
     * @param itemsToValidate
     *            List<String> - The items to validate.
     * @param module
     *            String - The module name.
     */
    public VaultPropertiesImpl(Properties vaultProperties, Map<String, String> itemsToValidate, String module) {
        this.vaultProperties = vaultProperties;
        for (String itemToValidate : itemsToValidate.keySet()) {
            if (!this.vaultProperties.containsKey(itemToValidate)) {
                throw new RuntimeException(itemToValidate + " is missing.");
            }
        }

        if (itemsToValidate.containsKey(InfraConstants.PROPERTY_VAULT_LOG_LEVEL)) {
            String logLevel = this.vaultProperties.getProperty(InfraConstants.PROPERTY_VAULT_LOG_LEVEL);
            Level level = null;
            if (LogLevel.ALL.name().equals(logLevel)) {
                level = Level.ALL;
            } else if (LogLevel.DEBUG.name().equals(logLevel)) {
                level = Level.DEBUG;
            } else if (LogLevel.INFO.name().equals(logLevel)) {
                level = Level.INFO;
            } else if (LogLevel.WARN.name().equals(logLevel)) {
                level = Level.WARN;
            } else if (LogLevel.ERROR.name().equals(logLevel)) {
                level = Level.ERROR;
            } else if (LogLevel.FATAL.name().equals(logLevel)) {
                level = Level.FATAL;
            } else {
                throw new RuntimeException("Log level value " + logLevel + " is invalid.");
            }
            org.apache.log4j.Logger.getRootLogger().setLevel(level);
        }

        if (logger.isInfoEnabled()) {
            StringBuilder vaultPropertyLog = new StringBuilder(module);
            vaultPropertyLog.append(" properties loaded. ");
            for (Map.Entry<String, String> itemToValidate : itemsToValidate.entrySet()) {
                String key = itemToValidate.getKey();
                String value = this.vaultProperties.getProperty(key);
                if (InfraConstants.PROPERTY_CLOUD_SWITCH_ENDPOINT.equals(key)) {
                    UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
                    if (!(urlValidator.isValid(value))) {
                        throw new RuntimeException("Switch endpoint URL is invalid: " + value);
                    }
                }
                vaultPropertyLog.append(itemToValidate.getValue());
                vaultPropertyLog.append(value);
                // FIXME - Comma check.
                vaultPropertyLog.append(", ");
            }
            logger.info(vaultPropertyLog.subSequence(0, vaultPropertyLog.length() - 2).toString());
        }

        if (itemsToValidate.containsKey(MivConstants.VAULT_CONFIGURATION_PASSWORD_EXPIRATION_DAYS)) {
            try {
                if (Integer.parseInt(this.vaultProperties
                        .getProperty(MivConstants.VAULT_CONFIGURATION_PASSWORD_EXPIRATION_DAYS)) <= 0) {
                    throw new RuntimeException();
                }
            } catch (Throwable t) {
                throw new RuntimeException("Property: " + MivConstants.VAULT_CONFIGURATION_PASSWORD_EXPIRATION_DAYS
                        + " is either missing or invalid", t);
            }
        }

        if (itemsToValidate.containsKey(MivConstants.VAULT_AUTHENTICATION_APP_URL)) {

            String authUrl = this.vaultProperties.getProperty(MivConstants.VAULT_AUTHENTICATION_APP_URL);
            UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
            if (!(urlValidator.isValid(authUrl))) {
                throw new RuntimeException("authentication app URL is invalid: " + authUrl);
            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.configuration.VaultProperties#getKey(java.lang.String)
     */
    @Override
    public String getKey(String key) {
        return vaultProperties.getProperty(key);
    }
}
