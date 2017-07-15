/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.passwordexpiry;

/**
 * Exposes methods to expire passwords.
 * 
 * @author Karthik.
 * 
 */
public interface PasswordExpiryService {
    /**
     * Expires the passwords for the given issuer.
     * 
     * @param iisn
     *            String - The iisn.
     */
    void expirePasswords(String iisn);
}
