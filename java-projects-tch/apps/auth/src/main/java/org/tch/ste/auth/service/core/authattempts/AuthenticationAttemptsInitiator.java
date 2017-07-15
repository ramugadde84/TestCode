/**
 * 
 */
package org.tch.ste.auth.service.core.authattempts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.auth.service.internal.authattempts.AuthAttemptsService;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;

/**
 * @author anus
 * 
 */
public class AuthenticationAttemptsInitiator {
    
    @Autowired
    private AuthAttemptsService authAttemptService;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /**
     * This method is start up file for batch process.
     */
    public void authAttemptsInitiator() {
        for (IssuerConfiguration issuerConfiguration : issuerConfigurationDao.getAll()) {
            authAttemptService.generateAuthAttemptsReport(issuerConfiguration.getIisn());
        }
    }
}
