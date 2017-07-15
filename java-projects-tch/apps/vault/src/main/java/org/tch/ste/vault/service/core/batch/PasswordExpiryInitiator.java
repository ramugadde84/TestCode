/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.vault.service.internal.batch.passwordexpiry.PasswordExpiryService;
import org.tch.ste.vault.service.internal.issuer.IssuerRetrievalService;

/**
 * Checks for password expiration at a scheduler interval.
 * 
 * @author Karthik.
 * 
 */
public class PasswordExpiryInitiator {

    @Autowired
    private IssuerRetrievalService issuerRetrievalService;

    @Autowired
    private PasswordExpiryService passwordExpiryService;

    /**
     * Checks and expires passwords.
     */
    public void expirePasswords() {
        List<IssuerConfiguration> issuers = issuerRetrievalService.fetchIssuersWithGeneratedUsernamePasswords();
        for (IssuerConfiguration issuer : issuers) {
            passwordExpiryService.expirePasswords(issuer.getIisn());
        }
    }
}
