/**
 * 
 */
package org.tch.ste.infra.transaction;

import java.util.Map;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tch.ste.domain.constant.MivConstants;

/**
 * A proxy transaction manager for use in multi-tenancy scenarios. Uses a thread
 * local variable to use a specific transaction manager.
 * 
 * @author Karthik.
 * 
 */
public class SteMivTransactionManager implements PlatformTransactionManager {

    private Map<String, PlatformTransactionManager> managedTransactionManagers;

    private String defaultTransactionManagerKey;

    /**
     * Overloaded Constructor.
     * 
     * @param managedTransactionManagers
     *            Map<String,PlatformTransactionManager> - The actual managers
     *            managed by this proxy.
     * @param defaultTransactionManagerKey
     *            String - The default transaction manager.
     */
    public SteMivTransactionManager(Map<String, PlatformTransactionManager> managedTransactionManagers,
            String defaultTransactionManagerKey) {
        this.managedTransactionManagers = managedTransactionManagers;
        this.defaultTransactionManagerKey = defaultTransactionManagerKey;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.transaction.PlatformTransactionManager#getTransaction
     * (org.springframework.transaction.TransactionDefinition)
     */
    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return getTransactionManager().getTransaction(definition);
    }

    /**
     * Fetches the specific transaction manager.
     * 
     * @return PlatformTransactionManager - The transaction manager to use.
     */
    private PlatformTransactionManager getTransactionManager() {
        String transactionManagerKey = (String) TransactionSynchronizationManager
                .getResource(MivConstants.CURRENT_PERSISTENCE_UNIT);
        if (transactionManagerKey == null) {
            transactionManagerKey = this.defaultTransactionManagerKey;
        }
        return this.managedTransactionManagers.get(transactionManagerKey);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.transaction.PlatformTransactionManager#commit(org
     * .springframework.transaction.TransactionStatus)
     */
    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        getTransactionManager().commit(status);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.transaction.PlatformTransactionManager#rollback(org
     * .springframework.transaction.TransactionStatus)
     */
    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        getTransactionManager().rollback(status);
    }

}
