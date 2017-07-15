/**
 * 
 */
package org.tch.ste.vault.service.core.tokendeactivation;

import java.util.List;

import org.tch.ste.domain.entity.Token;

/**
 * @author pamartheepan
 * 
 */
public interface TokenDeactivationService {
    /**
    * Method used to deactivate token by passing parameter as pan and date.
    * 
    * @param iisn
    *            String - The iisn.
    * @param pan
    *            String - The pan.
    * @param date
    *            String - The date.
    * @return List<Token> - Deactivated tokens.
    */
    List<Token> tokenDeactivation(String iisn, String pan, String date);

    /**
     * Method used to deactivate token by passing parameter arn.
     * 
     * @param iisn
     *            String - The iisn.
     * @param arn
     *            String - The arn.
     *            
     * @return List<Token> - Deactivated tokens.
     */
    List<Token> tokenDeactivation(String iisn, String arn);
}
