/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.vault.domain.dto.ActivatePaymentInstrument;

/**
 * @author kjanani
 * 
 */
@Service
public class PaymentInstrumentActivationFacadeImpl implements PaymentInstrumentActivationFacade {

    @Autowired
    private PaymentInstrumentActivationService paymentInstrumentActivationService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.core.paymentinstrument.
     * PaymentInstrumentActivationFacade#activate(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public ActivatePaymentInstrument activate(String iisn, String tokenRequestorId, String arn, String ciid) {

        return paymentInstrumentActivationService.activate(iisn, tokenRequestorId, arn, ciid);
    }

}
