/**
 * 
 */
package org.tch.ste.vault.web.service;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_DEACTIVATE_PAYMENT_INSTRUMENT_URL;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentRequest;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentResponse;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultErrorCode;
import org.tch.ste.vault.service.core.paymentinstrument.PaymentInstrumentDeactivationFacade;

/**
 * @author pamartheepan
 * 
 */
@RestController
@RequestMapping(C_DEACTIVATE_PAYMENT_INSTRUMENT_URL)
public class DeactivatePaymentInstrumentWebService {

    private Logger logger = LoggerFactory.getLogger(DeactivatePaymentInstrumentWebService.class);

    @Autowired
    private PaymentInstrumentDeactivationFacade deactivatePaymentInstrumentFacade;

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<DeactivatePaymentInstrumentRequest> tokenConverter;

    /**
     * Method to deactivate payment instruments using web service call.
     * 
     * @param request
     */
    @RequestMapping(method = RequestMethod.POST)
    DeactivatePaymentInstrumentResponse deactivatePaymentInstruments(HttpServletRequest request) {
        DeactivatePaymentInstrumentResponse res = new DeactivatePaymentInstrumentResponse();
        DeactivatePaymentInstrumentRequest req;
        List<Token> tokenList;
        try {
            req = tokenConverter.objectify(IOUtils.toString(request.getInputStream()),
                    DeactivatePaymentInstrumentRequest.class);
            res.setIisn(req.getIisn());
            res.setCiid(req.getCiid());
            res.setTrid(req.getTrid());
            res.setArn(req.getArn());
            tokenList = deactivatePaymentInstrumentFacade.deactivateTokens(req.getIisn(), req.getTrid(), req.getCiid(),
                    req.getArn());
            res.setNumDeactivatedTokens(tokenList.size());
            res.setResponseCode(VaultErrorCode.SUCCESS_RESPONSE_CODE.getResponseStr());
        } catch (Throwable t) {
            res.setResponseCode(VaultErrorCode.FAILURE_RESPONSE_CODE.getResponseStr());
            res.setErrorMsg(VaultConstants.FAILURE_MESSAGE);
            logger.warn("Exception Occured while deactivating PI ", t);
        }

        return res;
    }
}
