/**
 * 
 */
package org.tch.ste.admin.service.core.tokenrequestor;

import java.util.List;

import org.tch.ste.admin.domain.dto.TokenRequestor;

/**
 * A facade which provides methods for aspect injection.
 * 
 * @author Karthik.
 * 
 * 
 */
public interface TokenRequestorFacade {
    /**
     * This returns the list of token requestors in the system.
     * 
     * @return token requestorList - Return the list of token requestors
     */
    public List<TokenRequestor> loadTokenRequestors();

    /**
     * Save the token requestors in the system.
     * 
     * @param tokenRequestor
     *            - Token requestor to be saved
     * 
     */
    public void saveTokenRequestor(TokenRequestor tokenRequestor);

    /**
     * Delete the token requestors in the system
     * 
     * @param tokenRequestor
     *            - Token requestor to be deleted
     * @return TokenRequestor - The return value.
     */
    public TokenRequestor deleteTokenRequestor(TokenRequestor tokenRequestor);

    /**
     * This method returns the names of all the supported tokens
     * 
     * @return - TokenRequestor
     */
    public List<TokenRequestor> getAll();

    /**
     * Returns the selected token requestor details.
     * 
     * @param id
     *            Integer - the id.
     * @return TokenRequestor
     */
    public TokenRequestor getTokenRequestorDtls(Integer id);

    /**
     * Saves the selected token requestors for the issuer into
     * PERMITTED_TOKEN_REQUESTORS.
     * 
     * @param iisn
     *            String - The iisn.
     * @param selectedTokenRequestors
     *            Integer[] - The selected token requestors.
     */
    public void savePermittedTokenRequestor(String iisn, Integer[] selectedTokenRequestors);
}
