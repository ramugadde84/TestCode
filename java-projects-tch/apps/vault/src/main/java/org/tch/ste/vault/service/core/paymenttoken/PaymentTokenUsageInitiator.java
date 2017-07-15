/**
 * 
 */
package org.tch.ste.vault.service.core.paymenttoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.vault.service.internal.batch.paymenttoken.PaymentTokenUsageService;

/**
 * @author anus
 * 
 */
public class PaymentTokenUsageInitiator {

    @Autowired
    private PaymentTokenUsageService paymentTokenUsageService;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /**
     * Fetches the detokenization reports.
     * 
     */
    public void fetchDetokenizationRequests() {
        for (IssuerConfiguration issuerConfiguration : issuerConfigurationDao.getAll()) {
            paymentTokenUsageService.generateDetokenizationReport(issuerConfiguration.getIisn());
        }
    }
}
