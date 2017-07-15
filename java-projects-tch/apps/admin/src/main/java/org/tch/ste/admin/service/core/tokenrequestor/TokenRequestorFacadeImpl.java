/**
 * 
 */
package org.tch.ste.admin.service.core.tokenrequestor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.admin.domain.dto.TokenRequestor;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class TokenRequestorFacadeImpl implements TokenRequestorFacade {

    @Autowired
    private TokenRequestorService tokenRequestorService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#
     * loadTokenRequestors()
     */
    @Override
    public List<TokenRequestor> loadTokenRequestors() {
        return tokenRequestorService.loadTokenRequestors();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#
     * saveTokenRequestor(org.tch.ste.admin.domain.dto.TokenRequestor)
     */
    @Override
    public void saveTokenRequestor(TokenRequestor tokenRequestor) {
        tokenRequestorService.saveTokenRequestor(tokenRequestor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#
     * deleteTokenRequestor(org.tch.ste.admin.domain.dto.TokenRequestor)
     */
    @Override
    public TokenRequestor deleteTokenRequestor(TokenRequestor tokenRequestor) {
        return tokenRequestorService.deleteTokenRequestor(tokenRequestor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#getAll
     * ()
     */
    @Override
    public List<TokenRequestor> getAll() {
        return tokenRequestorService.getAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#
     * getTokenRequestorDtls(java.lang.Integer)
     */
    @Override
    public TokenRequestor getTokenRequestorDtls(Integer id) {
        return tokenRequestorService.getTokenRequestorDtls(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade#
     * savePermittedTokenRequestor(java.lang.String, java.lang.Integer[])
     */
    @Override
    public void savePermittedTokenRequestor(String iisn, Integer[] selectedTokenRequestors) {
        tokenRequestorService.savePermittedTokenRequestor(iisn, selectedTokenRequestors);
    }

}
