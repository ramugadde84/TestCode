/**
 * 
 */
package org.tch.ste.vault.service.internal.permittedtokenrequestor;

import org.tch.ste.domain.entity.PermittedTokenRequestor;

/**
 * Fetches a token requestor based on Tr Id.
 * 
 * @author Karthik.
 *
 */
public interface PermittedTokenRequestorFetchService {
    /**
     * Fetches the by Tr Id.
     * 
     * @param trId String - The tr id.
     * @return PermittedTokenRequestor - The result.
     */
    PermittedTokenRequestor getByTrId(String trId);
}
