/**
 * 
 */
package org.tch.ste.infra.configuration;

/**
 * @author pamartheepan
 * 
 */
public interface VaultProperties {
    /**
     * Returns the value of the property.
     * 
     * @param key
     *            String - the key.
     * @return Property value.
     */
    String getKey(String key);

}
