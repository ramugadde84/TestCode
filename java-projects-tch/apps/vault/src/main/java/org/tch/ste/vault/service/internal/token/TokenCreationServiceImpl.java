/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.domain.entity.PermittedTokenRequestorArnToken;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.domain.entity.TokenPosEntryMode;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.security.EncryptionService;
import org.tch.ste.infra.service.core.security.HashingService;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.arn.ArnFetchService;
import org.tch.ste.vault.service.internal.permittedtokenrequestor.PermittedTokenRequestorFetchService;
import org.tch.ste.vault.util.PanUtil;

/**
 * Service for Token creation
 * 
 * @author kjanani
 * 
 */

@Service
public class TokenCreationServiceImpl implements TokenCreationService {

    /**
     * To display all level logs.
     */
    public static final Logger logger = LoggerFactory.getLogger(TokenCreationServiceImpl.class);

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private HashingService hashingService;

    @Autowired
    private TokenGenerationService tokenGenerationService;

    @Autowired
    @Qualifier("paymentInstrumentDao")
    private JpaDao<PaymentInstrument, String> paymentInstrumentsDao;

    @Autowired
    @Qualifier("permittedTokenRequestorArnDao")
    private JpaDao<PermittedTokenRequestorArn, String> permittedTokenRequestorArnDao;

    @Autowired
    @Qualifier("permittedTokenRequestorArnTokenDao")
    private JpaDao<PermittedTokenRequestorArnToken, String> permittedTokenRequestorArnTokenDao;

    @Autowired
    @Qualifier("tokenDao")
    private JpaDao<Token, String> tokenDao;

    @Autowired
    private TokenFetchService tokenFetchService;

    @Autowired
    private ArnFetchService arnFetchService;

    @Autowired
    private PermittedTokenRequestorFetchService permittedTokenRequestorFetchService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.generation.TokenGenerationService#
     * generate(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public Token createToken(String iisn, String tokenRequestorId, String arn, String ciid) {
        try {
            PermittedTokenRequestorArn permittedTokenRequestorArn = tokenFetchService.getPermittedTokenRequestorArn(
                    arn, tokenRequestorId, ciid);

            if (permittedTokenRequestorArn == null) {
                permittedTokenRequestorArn = createPermittedTokenRequestorArn(arn, tokenRequestorId, ciid);
            }

            Token createdToken = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(VaultQueryConstants.PARAM_ARN, arn);

            PaymentInstrument paymentInstrument = ListUtil.getFirstOrNull(paymentInstrumentsDao.findByName(
                    VaultQueryConstants.GET_PAN_BY_ARN, params));

            boolean isActive = paymentInstrument.isActive();

            if (isActive) {
                int panLength = paymentInstrument.getPanLength();
                String expiryDate = paymentInstrument.getExpiryMonthYear();

                String realBin = encryptionService.decrypt(paymentInstrument.getPanFirstSixDigits());

                for(int i=0; i < VaultConstants.MAX_RETRIES_FOR_UNIQUE_CREATION;++i) {
                    if ((createdToken = validateAndSave((tokenGenerationService.generate(panLength, realBin)), realBin,
                            expiryDate, permittedTokenRequestorArn)) != null) {
                        break;
                    }
                }
                if (createdToken!=null) {
                    int tokenId = createdToken.getId();
                    logger.info("Token id: " + tokenId + " was generated for ARN " + arn + ", Token Requestor "
                            + tokenRequestorId + " and IISN " + iisn);
                }
                return createdToken;
            }
        } catch (Throwable t) {
            logger.error("An error occurred attempting to generate a new token for ARN " + arn + ", Token Requestor "
                    + tokenRequestorId + " and IISN " + iisn, t);

        }
        return null;
    }

    /**
     * Creates the permitted token requestor.
     * 
     * @param arn
     *            String - The arn.
     * @param tokenRequestorId
     *            String - The token requestor id.
     * @param ciid
     *            String - The ciid.
     * @return PermittedTokenRequestorArn - The permitted token requestor ARN.
     */
    private PermittedTokenRequestorArn createPermittedTokenRequestorArn(String arn, String tokenRequestorId, String ciid) {
        PermittedTokenRequestorArn retVal = new PermittedTokenRequestorArn();
        retVal.setArn(arnFetchService.getArn(arn));
        retVal.setPermittedTokenRequestor(permittedTokenRequestorFetchService.getByTrId(tokenRequestorId));
        retVal.setActive(true);
        retVal.setCiid(ciid);
        return permittedTokenRequestorArnDao.saveAndFlush(retVal);
    }

    /**
     * Method which validates whether the token exists and save.
     * 
     * @param token
     *            - generated token
     * @param realBin
     *            - value of real bin
     * @return - Token entity
     */
    private Token validateAndSave(String token, String realBin, String expDate,
            PermittedTokenRequestorArn permittedTokenRequestorArn) {
        String newEncryptedToken = null;
        try {
            newEncryptedToken = encryptionService.encrypt(token);
        } catch (Exception e) {
            throw new RuntimeException("Error while attempting to encrypt the token for realBin: " + realBin, e);
        }
        Token newToken = null;

        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put(VaultQueryConstants.ENCRYPTED_TOKEN, newEncryptedToken);
        Token checkTokens = ListUtil.getFirstOrNull(tokenDao.findByName(VaultQueryConstants.CHECK_TOKEN, params1));
        if (checkTokens == null) {
            try {
                newToken = new Token();
                newToken.setPlainTextToken(token);
                newToken.setEncryptedPanToken(newEncryptedToken);
                newToken.setTokenPanLastFourEncrypt(encryptionService.encrypt(PanUtil.getLastFourDigits(token)));
                newToken.setTokenPanFirstSixEncrypt(encryptionService.encrypt(PanUtil.getBinFromPan(token)));
                newToken.setActive(true);
                newToken.setTokenPanHash(hashingService.hash(token));

                // TODO: Based on conversation with Jithu, setting it to PAN
                // expiry date
                Date currentUtcTime = DateUtil.getUtcTime();
                newToken.setTokenExpMonthYear(expDate);

                // FIXME - Should generate based on rules
                TokenPosEntryMode tokenPosEntryMode = new TokenPosEntryMode();
                tokenPosEntryMode.setId(1);
                newToken.setTokenPosEntryModes(tokenPosEntryMode);
                newToken.setTokencreated(currentUtcTime);
                newToken = tokenDao.saveAndFlush(newToken);

                PermittedTokenRequestorArnToken permittedTokenRequestorArnToken = new PermittedTokenRequestorArnToken();
                permittedTokenRequestorArnToken.setToken(newToken);
                permittedTokenRequestorArnToken.setPermittedTokenRequestorArn(permittedTokenRequestorArn);
                permittedTokenRequestorArnTokenDao.save(permittedTokenRequestorArnToken);

            } catch (Throwable t) {
                logger.warn("Error while creating token :", t);
            }
        }
        return newToken;
    }
}
