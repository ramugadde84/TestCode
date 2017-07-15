/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import org.tch.ste.vault.constant.BatchResponseCode;

/**
 * @author ramug
 * 
 */
public interface LockUnlockCustomerAccountValidationService {
    /**
     * Validates the Lock or Unlock Customer Account validation file.
     * 
     * @param iisn
     *            String - The IISN.
     * @param accountContent
     *            - It accepts the bean of input batch data.
     * @return ResponseCode - Success or Error Code.
     */
    BatchResponseCode validate(String iisn, LockUnlockCustomerAccountContent accountContent);
}
