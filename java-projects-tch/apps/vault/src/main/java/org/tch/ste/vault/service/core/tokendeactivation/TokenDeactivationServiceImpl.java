/**
 * 
 */
package org.tch.ste.vault.service.core.tokendeactivation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.security.HashingService;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.token.TokenFetchService;

/**
 * @author pamartheepan
 * 
 */
@Service
public class TokenDeactivationServiceImpl implements TokenDeactivationService {

    @Autowired
    private HashingService hashingService;
    
    @Autowired
    private TokenFetchService tokenFetchService;

    @Autowired
    @Qualifier("tokenDao")
    private JpaDao<Token, Integer> tokenDao;

    private static Logger logger = LoggerFactory.getLogger(TokenDeactivationServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationService
     * #tokenDeactivation(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public List<Token> tokenDeactivation(String iisn, String pan, String date) {
        String hashedPan = hashingService.hash(pan);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_PAN_HASH, hashedPan);
        params.put(VaultQueryConstants.PARAM_EXPIRY_MONTH_YEAR, date);
        List<Token> tokenList = tokenDao.findByName(
                VaultQueryConstants.GET_TOKENS_FOR_DEACTIVATION_BY_PAYMENTINSTRUMENT, params);
        for (Token token : tokenList) {
            deactivateToken(iisn, token);
        }
        return tokenList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationService
     * #tokenDeactivation(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public List<Token> tokenDeactivation(String iisn, String arn) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_ARN_ID, arn);
        List<Token> tokenList = tokenDao.findByName(VaultQueryConstants.GET_TOKENS_FOR_DEACTIVATION_BY_ARN, params);
        for (Token token : tokenList) {
            deactivateToken(iisn, token);
        }
        return tokenList;
    }

    /**
     * Method used to deactivate token.
     * 
     * @param iisn
     *            String - The iisn.
     * @param token
     *            Token - The token.
     */
    private void deactivateToken(String iisn, Token token) {
        try {
            token.setActive(false);
            tokenDao.save(token);
            logger.info("Token " + token + " for issuer " + iisn + " has been deactivated.");
        } catch (Throwable t) {
            logger.error("An error occurred while attempting to deactivate token " + token + " for IISN " + iisn + ".");
        }
    }

}
