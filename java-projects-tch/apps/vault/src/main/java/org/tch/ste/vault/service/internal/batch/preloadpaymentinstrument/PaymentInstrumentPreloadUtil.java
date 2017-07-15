/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tch.ste.infra.service.core.security.MaskingService;
import org.tch.ste.infra.util.StringUtil;

/**
 * Not a utility class per se, but offers common methods.
 * 
 * @author Karthik.
 * 
 */
@Component
public class PaymentInstrumentPreloadUtil {

    @Autowired
    private MaskingService maskingService;

    /**
     * Copies the input values to the output.
     * 
     * @param response
     *            PaymentInstrumentPreloadRecordResponse - The response.
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     */
    public void copyValues(PaymentInstrumentPreloadRecordResponse response,
            PaymentInstrumentPreloadContent preloadContent) {
        response.setAccountNickName(preloadContent.getNickName());
        try {
            response.setPanMasked(maskingService.mask(StringUtil.stripLeadingZeros(preloadContent.getPan())));
        } catch (IllegalStateException e) {
            // Masking won't work for given PAN number - reset the same.
            response.setPanMasked(StringUtil.stripLeadingZeros(preloadContent.getPan()));
        }
        response.setExpiryDate(preloadContent.getExpirationDate());
        response.setIssuerAccountId(preloadContent.getIssuerAccountId());
        response.setIssuerCustomerId(preloadContent.getIssuerCustomerId());
        response.setProvidedPassword(preloadContent.getPassword());
        response.setProvidedUserName(preloadContent.getUserName());
        response.setProvidedRegistrationToken(preloadContent.getToken());
    }
}
