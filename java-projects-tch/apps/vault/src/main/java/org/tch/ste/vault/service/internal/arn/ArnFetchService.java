/**
 * 
 */
package org.tch.ste.vault.service.internal.arn;

import org.tch.ste.domain.entity.Arn;

/**
 * Interface to exposed methods to fetch an Arn Entity based on input arn string.
 * 
 * @author Karthik.
 *
 */
public interface ArnFetchService {
    /**
     * Fetches the arn.
     * 
     * @param arn String - The arn.
     * @return Arn - The arn.
     */
    Arn getArn(String arn);
}   
