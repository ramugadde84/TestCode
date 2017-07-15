/**
 * 
 */
package org.tch.ste.vault.service.internal.configuration;

/**
 * Exposes methods to fetch various parameters.
 * 
 * @author Karthik.
 * 
 */
public interface ConfigurationPropertiesService {
    /**
     * Returns the batch root path.
     * 
     * @return String - The root path.
     */
    String getBatchRoot();

    /**
     * Returns the password expiry days.
     * 
     * @return Integer - The number of days for password expiry.
     */
    Integer getPasswordExpiryDays();

}
