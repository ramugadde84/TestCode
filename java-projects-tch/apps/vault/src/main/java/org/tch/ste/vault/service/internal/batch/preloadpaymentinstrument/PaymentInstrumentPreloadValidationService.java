/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.tch.ste.vault.constant.BatchResponseCode;

/**
 * Validates.
 * 
 * @author Karthik.
 * 
 */
public interface PaymentInstrumentPreloadValidationService {
    /**
     * Validates the preload file.
     * 
     * @param iisn
     *            String - The IISN.
     * @param preloadContent
     *            PaymentInstrumentPreloadContent- The content.
     * @return PreloadResponseCode - Success or Error Code.
     */
    BatchResponseCode validate(String iisn, PaymentInstrumentPreloadContent preloadContent);
}
