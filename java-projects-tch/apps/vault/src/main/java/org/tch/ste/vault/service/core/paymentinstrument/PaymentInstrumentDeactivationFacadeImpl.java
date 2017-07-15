/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.Token;

/**
 * @author pamartheepan
 *
 */
@Service
public class PaymentInstrumentDeactivationFacadeImpl implements PaymentInstrumentDeactivationFacade{

    @Autowired
    private PaymentInstrumentDeactivationService deactivatePaymentInstrumentService;
    
    /**
     * Method used to deactivate payment instruments.
     * 
     */
    @Override
    public List<Token> deactivateTokens(String iisn, String trid, String ciid, String arn) {
        return deactivatePaymentInstrumentService.deactivateTokens(iisn, trid, ciid, arn);
    }

}
