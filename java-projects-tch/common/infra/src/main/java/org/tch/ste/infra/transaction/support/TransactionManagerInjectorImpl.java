/**
 * 
 */
package org.tch.ste.infra.transaction.support;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tch.ste.domain.constant.MivConstants;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service("defaultTransactionManagerInjector")
public class TransactionManagerInjectorImpl implements TransactionManagerInjector {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.transaction.support.TransactionManagerInjector#inject
     * (java.lang.String)
     */
    @Override
    public void inject(String transactionManagerKey) {
        TransactionSynchronizationManager.bindResource(MivConstants.CURRENT_TRANSACTION_MANAGER, transactionManagerKey);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.transaction.support.TransactionManagerInjector#remove
     * (java.lang.String)
     */
    @Override
    public void remove(String transactionManagerKey) {
        TransactionSynchronizationManager.unbindResource(MivConstants.CURRENT_TRANSACTION_MANAGER);
    }

}
