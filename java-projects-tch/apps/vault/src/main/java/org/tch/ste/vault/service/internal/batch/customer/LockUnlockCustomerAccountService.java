/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

/**
 * Lock or Unlock Customer Account service.
 * 
 * @author ramug
 * 
 */
public interface LockUnlockCustomerAccountService {
    /**
     * The method which is used to find the customer table to lock or
     * unlock,inside first i checked whether the issuer-id and user-name is
     * available in database,and after update is done.
     * 
     * @param iisn
     *            - The iisn value.
     * @param accountContent
     *            - the value which accepts account content object.
     * 
     * @return the response object.
     */
    public LockUnlockCustomerAccountResponse lockUnlockCustomers(String iisn,
            LockUnlockCustomerAccountContent accountContent);
}
