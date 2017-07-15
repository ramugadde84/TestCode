/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.paymenttoken;

/**
 * @author anus
 * 
 */
public interface PaymentTokenUsageService {

    /**
     * Generates the report.
     * 
     * @param iisn
     *            String - The iisn.
     */
    void generateDetokenizationReport(String iisn);

}
