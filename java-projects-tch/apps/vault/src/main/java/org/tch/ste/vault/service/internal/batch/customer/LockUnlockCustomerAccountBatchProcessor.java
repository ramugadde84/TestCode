/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import org.springframework.batch.item.ItemProcessor;

/**
 * Batch Processor.
 * 
 * @author ramug
 * @param <I>
 *            The input template.
 * 
 */
public class LockUnlockCustomerAccountBatchProcessor<I> implements ItemProcessor<I, I> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
     */
    @Override
    public I process(I item) throws Exception {
        if (item instanceof LockUnlockCustomerAccountContent) {
            return item;
        }
        return null;
    }

}
