/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

import org.tch.ste.domain.entity.Token;

/**
 * Service for Token Creation
 * 
 * @author kjanani
 * 
 */
public interface TokenCreationService {

    /**
     * This methods returns the generated Tokens
     * 
     * @param tokenRequestorId
     *            - Token requestor id
     * @param arn
     * @param iisn
     * @param ciid 
     * 
     * @return Token - Tokens
     */
    Token createToken(String iisn, String tokenRequestorId, String arn,String ciid);

}
