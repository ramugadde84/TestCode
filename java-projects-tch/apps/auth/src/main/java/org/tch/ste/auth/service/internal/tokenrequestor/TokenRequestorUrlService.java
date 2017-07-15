/**
 * 
 */
package org.tch.ste.auth.service.internal.tokenrequestor;

/**
 * Forms the url for the given token requestor.
 * 
 * @author Karthik.
 *
 */
public interface TokenRequestorUrlService {
    /**
     * Forms the url for the given token requestor.
     * 
     * @param trId String - The token requestor id.
     * @param success boolean - Whether the enrollment was successful.
     * @return String - The url.
     */
    String formUrl(String trId,boolean success);
}
