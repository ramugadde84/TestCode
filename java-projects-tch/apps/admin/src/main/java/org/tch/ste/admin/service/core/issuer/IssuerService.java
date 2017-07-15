package org.tch.ste.admin.service.core.issuer;

import java.util.List;

import org.tch.ste.admin.domain.dto.Issuer;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.domain.entity.AuthenticationMechanism;
import org.tch.ste.domain.entity.IssuerConfiguration;

/**
 * Interface to define business service methods for issuers.
 * 
 * @author ramug
 * 
 */
public interface IssuerService {

    /**
     * This returns the list of issuers in the system.
     * 
     * @return issuerList - Return the list of issuers
     */
    public List<Issuer> loadIssuers();

    /**
     * Fetches the list of authentication mechanisms.
     * 
     * @return String[] - The authentication mechanisms.
     */
    String[] getAuthentication();

    /**
     * Fetches the list of authentication mechanisms.
     * 
     * @return String[] - The authentication mechanisms.
     */
    List<AuthenticationMechanism> getAllAuthenticationMechanism();

    /**
     * Save the issuer configuration.
     * 
     * @param issuer
     *            - The issuer to be stored
     */
    void saveIssuerConfiguration(IssuerDetail issuer);

    /**
     * @param id
     *            - row id value
     * @return IssuerDetail - returns the details about the issuer
     */
    IssuerDetail getIssuerEditDetails(Integer id);

    /**
     * Returns the issuers sorted by ascending order of name.
     * 
     * @return List<IssuerConfiguration> - The values.
     */
    List<IssuerConfiguration> getIssuersSortedAscendingByName();
}
