/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import org.tch.ste.vault.domain.dto.ActivatePaymentInstrument;

/**
 * @author kjanani
 * 
 */
public interface PaymentInstrumentActivationFacade {

    /**
     * @param iisn
     * @param tokenRequestorId
     * @param arn
     * @param ciid
     * @return -
     */
    ActivatePaymentInstrument activate(String iisn, String tokenRequestorId, String arn, String ciid);

}
