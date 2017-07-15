/**
 * 
 */
package org.tch.ste.infra.service.core.security;

import org.tch.ste.infra.exception.SteException;

/**
 * Encryption Service.
 * 
 * @author Karthik.
 * 
 */
public interface EncryptionService {
    /**
     * Encrypts the given value.
     * 
     * @param value
     *            String - The value to be encrypted.
     * @return String - The encrypted value.
     * @throws SteException
     *             Thrown.
     */
    String encrypt(String value) throws SteException;

    /**
     * @param value
     *            -The value to be decrypted.
     * @return String - decrypted value.
     * @throws SteException
     *             Thrown.
     */
    String decrypt(String value) throws SteException;
}
