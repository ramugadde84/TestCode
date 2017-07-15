/**
 * 
 */
package org.tch.ste.vault.service.internal.issuer;

import java.util.List;

import org.tch.ste.domain.entity.IssuerConfiguration;

/**
 * Contains methods related to retrieval of issuers.
 * 
 * @author Karthik.
 * 
 */
public interface IssuerRetrievalService {
    /**
     * Returns all issuers.
     * 
     * @return List<IssuerConfiguration> - The issuers.
     */
    List<IssuerConfiguration> getAll();

    /**
     * Fetches the issuers who have generated user names and passwords.
     * 
     * @return List<IssuerConfiguration> - The issuers that match.
     */
    List<IssuerConfiguration> fetchIssuersWithGeneratedUsernamePasswords();
}
