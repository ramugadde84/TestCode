/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.domain.entity.PermittedTokenRequestor;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.domain.dto.ActivatePaymentInstrument;
import org.tch.ste.vault.service.internal.permittedtokenrequestor.PermittedTokenRequestorFetchService;
import org.tch.ste.vault.service.internal.token.TokenCreationFacade;

/**
 * @author kjanani
 * 
 */
@Service
public class PaymentInstrumentActivationServiceImpl implements PaymentInstrumentActivationService {

    @Autowired
    private TokenCreationFacade tokenCreationFacade;

 
    @Autowired
    @Qualifier("paymentInstrumentDao")
    private JpaDao<PaymentInstrument, String> paymentInstrumentsDao;

    @Autowired
    @Qualifier("permittedTokenRequestorArnDao")
    private JpaDao<PermittedTokenRequestorArn, String> permittedTokenRequestorArnDao;

    @Autowired
    private PermittedTokenRequestorFetchService permittedTokenRequestorFetchService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.core.paymentinstrument.
     * PaymentInstrumentActivationService#activate(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public ActivatePaymentInstrument activate(String iisn, String tokenRequestorId, String arn, String ciid) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_ARN, arn);
        PaymentInstrument paymentInstrument = ListUtil.getFirstOrNull(paymentInstrumentsDao.findByName(
                VaultQueryConstants.GET_PAN_BY_ARN, params));
        paymentInstrument.setActive(true);
        paymentInstrumentsDao.save(paymentInstrument);

        PermittedTokenRequestor tokenRequestor = permittedTokenRequestorFetchService.getByTrId(tokenRequestorId);

        PermittedTokenRequestorArn permittedTokenRequestorArn = new PermittedTokenRequestorArn();
        permittedTokenRequestorArn.setCiid(ciid);
        permittedTokenRequestorArn.setPermittedTokenRequestor(tokenRequestor);
        permittedTokenRequestorArn.setArn(paymentInstrument.getArn());
        permittedTokenRequestorArn.setActive(true);
        permittedTokenRequestorArnDao.save(permittedTokenRequestorArn);

        Token token = tokenCreationFacade.createToken(iisn, tokenRequestorId, arn, ciid);
        ActivatePaymentInstrument retVal=new ActivatePaymentInstrument();
        retVal.setToken(token);
        retVal.setSuccess(true);
        return retVal;
    }

}
