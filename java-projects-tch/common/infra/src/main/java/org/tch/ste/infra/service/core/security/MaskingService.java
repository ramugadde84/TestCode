/**
 * 
 */
package org.tch.ste.infra.service.core.security;

/**
 * Service for masking data.
 * 
 * @author pamartheepan
 * 
 */
public interface MaskingService {
    /**
     * Masks the given input.
     * 
     * @param maskingData
     *            String - The data to be masked.
     * @return String - The masked data.
     */
    String mask(String maskingData);

}
