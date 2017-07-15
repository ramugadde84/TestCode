/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

/**
 * Exposes methods to do the actual preload.
 * 
 * @author Karthik.
 * 
 */
public interface PaymentInstrumentPreloadService {

    /**
     * Adds a payment instrument and returns the response code.
     * 
     * @param iisn
     *            String - The IISN.
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @return PaymentInstrumentPreloadRecordResponse - The response to the
     *         preload request
     */
    PaymentInstrumentPreloadRecordResponse addPaymentInstrument(String iisn,
            PaymentInstrumentPreloadContent preloadContent);
}
