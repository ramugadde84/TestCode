/**
 * 
 */
package org.tch.ste.vault.service.internal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.configuration.VaultProperties;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class ConfigurationPropertiesServiceImpl implements ConfigurationPropertiesService {

    @Autowired
    private VaultProperties vaultProperties;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.configuration.
     * ConfigurationPropertiesService#getBatchRoot()
     */
    @Override
    public String getBatchRoot() {
        return vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_BATCH_ROOT_DIRECTORY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.configuration.
     * ConfigurationPropertiesService#getPasswordExpiryDays()
     */
    @Override
    public Integer getPasswordExpiryDays() {
        return Integer.valueOf(vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_PASSWORD_EXPIRATION_DAYS));
    }

}
