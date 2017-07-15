/**
 * 
 */
package org.tch.ste.vault.service.core.paymentinstrument;

import java.util.List;

import org.tch.ste.domain.entity.Token;

/**
 * @author pamartheepan
 *
 */
public interface PaymentInstrumentDeactivationService {

    /**
     * @param iisn
     * @param trid
     * @param ciid
     * @param arn 
     * @return List
     * 
     */
    List<Token> deactivateTokens(String iisn, String trid, String ciid, String arn);

}
