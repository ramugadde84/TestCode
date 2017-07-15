/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author Karthik.
 * @param <I>
 *            - The input template.
 * 
 */
public class PaymentInstrumentPreloadBatchProcessor<I> implements ItemProcessor<I, I> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
     */
    @Override
    public I process(I item) throws Exception {
        if (item instanceof PaymentInstrumentPreloadContent) {
            return item;
        }
        return null;
    }

}
