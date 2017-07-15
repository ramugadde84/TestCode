/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

/**
 * Token generation service
 * 
 * @author kjanani
 * 
 */
public interface TokenGenerationService {

    /**
     * Generates the token.
     * 
     * @param length
     *            int - The length of the token
     * @param realBin
     *            String - The real bin.
     * @return String - generated token
     * 
     */
    String generate(int length, String realBin);

}
