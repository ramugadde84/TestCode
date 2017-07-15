/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.StringUtil;

/**
 * Implements the interface.
 * 
 * @author sharduls
 * 
 */
@Service
public class PaymentInstrumentChangeServiceImpl implements PaymentInstrumentChangeService {

    private static Logger logger = LoggerFactory.getLogger(PaymentInstrumentChangeServiceImpl.class);

    @Autowired
    @Qualifier("permittedTokenRequestorArnDao")
    private JpaDao<PermittedTokenRequestorArn, Integer> permittedTokenRequestorArnDao;

    @Autowired
    @Qualifier("paymentInstrumentDao")
    private JpaDao<PaymentInstrument, Integer> paymentInstrumentDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.paymentinstrument.
     * PaymentInstrumentChangeService#getAddedPaymentInstruments(java.util.List,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentInstrument> getAddedPaymentInstruments(List<Integer> activePaymetnInstruments, String iisn,
            String trId, String ciid) {
        List<PaymentInstrument> addedPaymentInstruments = new ArrayList<>();
        StringBuilder arns = new StringBuilder();
        String seperator = "";
        for (Integer activePaymentInstrumentId : activePaymetnInstruments) {
            PaymentInstrument paymentInstrument = paymentInstrumentDao.get(activePaymentInstrumentId);
            if (!isPaymentInstrumentActive(paymentInstrument.getArn().getId(), trId, ciid, paymentInstrument.isActive())) {
                addedPaymentInstruments.add(paymentInstrument);
                arns.append(seperator);
                arns.append(paymentInstrument.getArn().getArn());
                seperator = ",";
            }
        }
        if (!addedPaymentInstruments.isEmpty() && logger.isInfoEnabled()) {
            logger.info("Payment instruments added for user = {}, TRID = {}, IISN = {}, CIID = {}, ARNS = {} ",
                    new Object[] { SecurityContextHolder.getContext().getAuthentication().getName(), trId, iisn, ciid,
                            arns.toString() });
        }
        return addedPaymentInstruments;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.paymentinstrument.
     * PaymentInstrumentChangeService
     * #getRemovedPaymentInstruments(java.util.List, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentInstrument> getRemovedPaymentInstruments(List<Integer> inactivePaymetnInstruments, String iisn,
            String trId, String ciid) {
        List<PaymentInstrument> removedPaymentInstruments = new ArrayList<>();
        StringBuilder arns = new StringBuilder();
        String seperator = "";
        for (Integer inactivePaymentInstrumentId : inactivePaymetnInstruments) {
            PaymentInstrument paymentInstrument = paymentInstrumentDao.get(inactivePaymentInstrumentId);
            if (isPaymentInstrumentActive(paymentInstrument.getArn().getId(), trId, ciid, paymentInstrument.isActive())) {
                removedPaymentInstruments.add(paymentInstrument);
                arns.append(seperator);
                arns.append(paymentInstrument.getArn().getArn());
                seperator = ",";
            }
        }
        if (!removedPaymentInstruments.isEmpty() && logger.isInfoEnabled()) {
            logger.info("Payment instruments removed for user = {}, TRID = {}, IISN = {}, CIID = {}, ARNS = {} ",
                    new Object[] { SecurityContextHolder.getContext().getAuthentication().getName(), trId, iisn, ciid,
                            arns.toString() });
        }
        return removedPaymentInstruments;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.paymentinstrument.
     * PaymentInstrumentChangeService#isPaymentInstrumentActive(int,
     * java.lang.String, java.lang.String, boolean)
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isPaymentInstrumentActive(int arnId, String trId, String ciid, boolean active) {
        boolean isActive = false;
        if (!StringUtil.isNullOrBlank(ciid)) {
            Map<String, Object> params = new HashMap<>();
            params.put(AuthConstants.ARNID, arnId);
            params.put(AuthConstants.TOKEN_REQUESTOR_ID, Integer.valueOf(trId));
            params.put(AuthConstants.CIID, ciid);
            params.put(AuthConstants.ACTIVE, active);
            PermittedTokenRequestorArn permittedTokenRequestorArn = ListUtil
                    .getFirstOrNull(permittedTokenRequestorArnDao.findByName(
                            AuthQueryConstants.CHECK_TOKEN_REQUESTOR_ID_ARN, params));
            isActive = permittedTokenRequestorArn == null ? false : true;
        } else {
            isActive = active;
        }
        return isActive;

    }

}
