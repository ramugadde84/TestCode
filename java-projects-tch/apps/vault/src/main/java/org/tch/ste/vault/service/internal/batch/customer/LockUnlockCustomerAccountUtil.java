/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

/**
 * Util Class.
 * 
 * @author ramug
 * 
 */
public class LockUnlockCustomerAccountUtil {
    private LockUnlockCustomerAccountUtil() {

    }

    /**
     * Copies the input values to the output.
     * 
     * @param response
     *            Account reponse - The response.
     * @param accountContent
     *            AccountContent - The content.
     */
    public static void copyValues(LockUnlockCustomerAccountResponse response,
            LockUnlockCustomerAccountContent accountContent) {
        response.setIssuerCustomerId(accountContent.getIssuerCustomerId());
        response.setLockState(accountContent.getLockState());
        response.setUserName(accountContent.getUserName());
    }
}
