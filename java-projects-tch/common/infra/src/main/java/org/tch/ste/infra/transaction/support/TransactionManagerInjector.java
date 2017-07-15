/**
 * 
 */
package org.tch.ste.infra.transaction.support;

/**
 * Injects the key for the given transaction manager.
 * 
 * @author Karthik.
 * 
 */
public interface TransactionManagerInjector {
    /**
     * Injects the given key as a thread local variable.
     * 
     * @param transactionManagerKey
     *            String - The key.
     */
    void inject(String transactionManagerKey);

    /**
     * Removes the given key as thread local variable.
     * 
     * @param transactionManagerKey
     *            String - The key.
     */
    void remove(String transactionManagerKey);
}
