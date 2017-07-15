/**
 * 
 */
package org.tch.ste.vault.web.service;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_PAYMENT_INSTRUMENT_ACTIVATION_SERVICE;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.domain.dto.ActivatePaymentRequest;
import org.tch.ste.domain.dto.ActivatePaymentResponse;
import org.tch.ste.infra.service.core.security.MaskingService;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.vault.constant.VaultResponseCode;
import org.tch.ste.vault.domain.dto.ActivatePaymentInstrument;
import org.tch.ste.vault.service.core.paymentinstrument.PaymentInstrumentActivationFacade;
import org.tch.ste.vault.service.core.paymentinstrument.PaymentInstrumentActivationService;

/**
 * @author kjanani
 * 
 */
@RestController
@RequestMapping(C_PAYMENT_INSTRUMENT_ACTIVATION_SERVICE)
public class PaymentInstrumentActivationWebService {

    @Autowired
    private PaymentInstrumentActivationService paymentInstrumentActivationService;

    @Autowired
    private MaskingService maskingService;

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<ActivatePaymentRequest> requestConverter;
    
    @Autowired
    private PaymentInstrumentActivationFacade paymentInstrumentActivationFacade;

    /**
     * @param request
     *            -
     * @return -
     * @throws Throwable
     *             -
     */
    @RequestMapping(method = RequestMethod.POST)
    public ActivatePaymentResponse activate(HttpServletRequest request) throws Throwable {

        ActivatePaymentResponse response = new ActivatePaymentResponse();
        ActivatePaymentRequest req;

        req = requestConverter.objectify(IOUtils.toString(request.getInputStream()), ActivatePaymentRequest.class);

        ActivatePaymentInstrument activatePaymentInstrument = paymentInstrumentActivationFacade.activate(
                req.getIisn(), req.getTrId(), req.getArn(), req.getCiid());

        if (activatePaymentInstrument.isSuccess()) {
            response.setMaskedToken(maskingService.mask(activatePaymentInstrument.getToken().getPlainTextToken()));
            response.setResponseCode(VaultResponseCode.SUCCESS_RESPONSE_CODE.getResponseStr());
        }
        else {
            
            response.setResponseCode(VaultResponseCode.FAILURE_RESPONSE_CODE.getResponseStr());            
        }

        return response;
    }

}
