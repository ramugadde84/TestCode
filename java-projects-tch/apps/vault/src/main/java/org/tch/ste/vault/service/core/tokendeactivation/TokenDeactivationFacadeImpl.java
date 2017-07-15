/**
 * 
 */
package org.tch.ste.vault.service.core.tokendeactivation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.Token;

/**
 * @author pamartheepan
 * 
 */
@Service
public class TokenDeactivationFacadeImpl implements TokenDeactivationFacade {

    @Autowired
    private TokenDeactivationService paymentInstrumentDeactivationService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationFacade
     * #tokenDeactivation(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Token> tokenDeactivation(String iisn, String pan, String date) {
        return paymentInstrumentDeactivationService.tokenDeactivation(iisn, pan, date);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationFacade
     * #tokenDeactivation(java.lang.String, java.lang.String)
     */
    @Override
    public List<Token> tokenDeactivation(String iisn, String arn) {
        return paymentInstrumentDeactivationService.tokenDeactivation(iisn, arn);
    }

}
