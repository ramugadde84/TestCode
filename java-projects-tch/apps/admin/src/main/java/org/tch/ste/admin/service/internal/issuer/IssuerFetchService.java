/**
 * 
 */
package org.tch.ste.admin.service.internal.issuer;

import org.tch.ste.domain.entity.IssuerConfiguration;

/**
 * Methods to fetch issuers based on data.
 * 
 * @author anus
 * 
 */
public interface IssuerFetchService {
    /**
     * Returns the issuer based on the input iisn.
     * 
     * @param iisn
     *            String - The iisn.
     * @return IssuerConfiguration - The issuer for the iisn.
     */
    IssuerConfiguration getIssuerByIisn(String iisn);
}
