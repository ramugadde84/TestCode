/**
 * 
 */
package org.tch.ste.auth.integration.paymentinstrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.domain.dto.ActivatePaymentRequest;
import org.tch.ste.domain.dto.ActivatePaymentResponse;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentRequest;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentResponse;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.infra.configuration.VaultProperties;

/**
 * Service implementation to manage payment instrument activation and
 * deactivation.
 * 
 * @author janarthanans
 * 
 */
@Service
public class PaymentInstrumentManagementServiceImpl implements PaymentInstrumentManagementService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VaultProperties vaultProperties;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.integration.paymentinstrument.
     * PaymentInstrumentManagementService
     * #activate(org.tch.ste.domain.entity.PaymentInstrument, java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public boolean activate(PaymentInstrument paymentInstrument, String ciid, String tokenRequestorId, String iisn) {
        ActivatePaymentRequest activatePaymentRequest = new ActivatePaymentRequest();
        activatePaymentRequest.setArn(paymentInstrument.getArn().getArn());
        activatePaymentRequest.setIisn(iisn);
        activatePaymentRequest.setTrId(tokenRequestorId);
        activatePaymentRequest.setCiid(ciid);
        ActivatePaymentResponse activatePaymentResponse = restTemplate.postForObject(
                vaultProperties.getKey(AuthConstants.PAYMENT_INSTRUMENT_ACTIVATION_URL), activatePaymentRequest,
                ActivatePaymentResponse.class);
        // FIXME - Move Response Code to common area.
        return activatePaymentResponse!=null && "0000".equals(activatePaymentResponse.getResponseCode());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.integration.paymentinstrument.
     * PaymentInstrumentManagementService
     * #deactivate(org.tch.ste.domain.entity.PaymentInstrument,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean deactivate(PaymentInstrument paymentInstrument, String ciid, String tokenRequestorId, String iisn) {
        DeactivatePaymentInstrumentRequest request=new DeactivatePaymentInstrumentRequest();
        request.setArn(paymentInstrument.getArn().getArn());
        request.setCiid(ciid);
        request.setIisn(iisn);
        request.setTrid(tokenRequestorId);
        DeactivatePaymentInstrumentResponse response=restTemplate.postForObject(
                vaultProperties.getKey(AuthConstants.PAYMENT_INSTRUMENT_DEACTIVATION_URL), request,
                DeactivatePaymentInstrumentResponse.class);
        return response!=null && "0000".equals(response.getResponseCode());
    }

}
