/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.arn.ArnFetchService;
import org.tch.ste.vault.service.internal.permittedtokenrequestor.PermittedTokenRequestorFetchService;
import org.tch.ste.vault.service.internal.token.TokenFetchService;

/**
 * @author pamartheepan
 * 
 */
@Service
public class PaymentInstrumentDeactivationServiceImpl implements PaymentInstrumentDeactivationService {

    @Autowired
    @Qualifier("paymentInstrumentDao")
    private JpaDao<PaymentInstrument, Integer> paymentInstrumentsDao;

    @Autowired
    @Qualifier("permittedTokenRequestorArnDao")
    private JpaDao<PermittedTokenRequestorArn, Integer> permittedTokenRequestorArnDao;
    
    @Autowired
    @Qualifier("tokenDao")
    private JpaDao<Token, Integer> tokenDao;

    @Autowired
    private ArnFetchService arnFetchService;

    @Autowired
    private TokenFetchService tokenFetchService;

    @Autowired
    private PermittedTokenRequestorFetchService permittedTokenRequestorFetchService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.core.paymentinstrument.
     * PaymentInstrumentDeactivationService#deactivateTokens(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public List<Token> deactivateTokens(String iisn, String trid, String ciid, String arn) {
        Arn arnDetails = arnFetchService.getArn(arn);

        if (StringUtil.isNullOrBlank(ciid)) {
            Map<String, Object> paymentInstrumentsParam = new HashMap<String, Object>();
            paymentInstrumentsParam.put(VaultQueryConstants.ARN_DETAILS, arnDetails.getId());
            List<PaymentInstrument> paymentInstrument = paymentInstrumentsDao.findByName(
                    VaultQueryConstants.GET_LIST_OF_ACTIVE_PAYMENT_INSTRUMENT, paymentInstrumentsParam);
            for (PaymentInstrument instrument : paymentInstrument) {
                instrument.setActive(false);
                paymentInstrumentsDao.save(instrument);
            }
        }
        return deactivatePermittedTokenRequestorArn(iisn, trid, ciid, arn)?deactivate(arn,trid,ciid):new ArrayList<Token>();
    }

    /**
     * Deactivates the given tokens.
     * 
     * @param arn String - The arn.
     * @param trid String - The tr id.
     * @param ciid String - The ciid.
     * @return List<Token> - the deactivated tokens.
     */
    private List<Token> deactivate(String arn, String trid, String ciid) {
        List<Token> tokens=tokenFetchService.getTokens(arn, trid, ciid);
        for (Token token:tokens) {
            token.setActive(false);
            tokenDao.save(token);
        }
        return tokens;
    }

    /**
     * Method used to deactivate permitted token requestor arn.
     * 
     * @param iisn
     * @param trid
     * @param ciid
     * @Arn arn
     * 
     */
    private boolean deactivatePermittedTokenRequestorArn(String iisn, String trid, String ciid, String arn) {
        boolean retVal=false;
        PermittedTokenRequestorArn permittedTokenRequestorArn = tokenFetchService.getPermittedTokenRequestorArn(arn,
                trid, ciid);
        if (permittedTokenRequestorArn!=null) {
            permittedTokenRequestorArn.setActive(false);
            permittedTokenRequestorArnDao.save(permittedTokenRequestorArn);
            retVal=true;
        }
        return retVal;
    }
}
