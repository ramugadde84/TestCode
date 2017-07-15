package org.tch.ste.infra.service.core.security;

/**
 * @author sujathas
 * 
 */
public interface HashingService {

    /**
     * @param value
     * @return Returns Hashed Value
     */
    String hash(String value);

    /**
     * @param value
     * @param salt
     * @return -Returns Hashed value with Salt
     */
    String hash(String value, Object salt);

}
