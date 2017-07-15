package org.tch.ste.vault.service.internal.token;

import org.tch.ste.domain.entity.Token;

/**
 * @author kjanani
 * 
 */
public interface TokenCreationFacade {

    /**
     * @param iisn
     * @param tokenRequestorId
     * @param arn
     * @param ciid 
     * @return -
     */
    Token createToken(String iisn, String tokenRequestorId, String arn, String ciid);

}
