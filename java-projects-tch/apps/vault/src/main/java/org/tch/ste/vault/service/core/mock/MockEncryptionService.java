package org.tch.ste.vault.service.core.mock;

import org.tch.ste.infra.exception.SteException;

/**
 * @author sujathas
 * 
 */
public interface MockEncryptionService {

    /**
     * @param value
     * @return -Returns Encrypted Value
     * @throws SteException
     */
    String encrypt(String value) throws SteException;

    /**
     * @param key
     * @param value
     * @return -Returns Decrypted Value
     * @throws SteException
     */
    String decrypt(String value) throws SteException;

}
