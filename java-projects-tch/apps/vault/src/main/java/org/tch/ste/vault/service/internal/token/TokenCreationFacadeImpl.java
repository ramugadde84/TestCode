package org.tch.ste.vault.service.internal.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.Token;

/**
 * @author sujathas
 * 
 */
@Service
public class TokenCreationFacadeImpl implements TokenCreationFacade {

    @Autowired
    private TokenCreationService tokenCreationService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.generation.TokenCreationFacade#createToken
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Token createToken(String iisn, String tokenRequestorId, String arn, String ciid) {

        return tokenCreationService.createToken(iisn, tokenRequestorId, arn, ciid);
    }

}
