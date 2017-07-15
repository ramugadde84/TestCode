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
public interface TokenDeactivationFacade {

    /**
     * Deactivates the token based on Pan & Expiry Date.
     * 
     * @param iisn String - The iisn.
     * @param pan String - The pan.
     * @param date String - The expiry date as MMYY.
     * @return  List<Token> - The list of deactivated tokens.
     */
    List<Token> tokenDeactivation(String iisn, String pan, String date);

    
    /**
     * Deactivates the token based on Arn.
     * 
     * @param iisn String - The iisn.
     * @param arn String - The arn.
     * @return  List<Token> - The list of deactivated tokens.
     */
    List<Token> tokenDeactivation(String iisn, String arn);
}
