/**
 * 
 */
package org.tch.ste.auth.service.internal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.configuration.VaultProperties;

/**
 * The implementation service of configuration.
 * 
 * @author ramug
 * 
 */
@Service
public class ConfigurationPropertiesServiceImpl implements ConfigurationPropertiesService {

    @Autowired
    private VaultProperties vaultProperties;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.configuration.
     * ConfigurationPropertiesService#getBatchRoot()
     */
    @Override
    public String getBatchRoot() {
        return vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_BATCH_ROOT_DIRECTORY);
    }

}
