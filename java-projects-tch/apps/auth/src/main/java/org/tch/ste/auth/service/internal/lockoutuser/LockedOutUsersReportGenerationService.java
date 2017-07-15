/**
 * 
 */
package org.tch.ste.auth.service.internal.lockoutuser;

/**
 * This service finds all the locked customers information to form a report.
 * 
 * @author ramug
 * 
 */
public interface LockedOutUsersReportGenerationService {
    /**
     * It Generates Locked Customers report,for issuers.
     * 
     * @param iisn
     *            - it is unique value for every issuer.
     */
    void generateLockoutUsersReport(String iisn);
}
