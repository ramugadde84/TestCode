/**
 * 
 */
package org.tch.ste.vault.service.internal.generation;

import org.tch.ste.domain.entity.Customer;

/**
 * Generates a unique username. Validates against customers table for
 * uniqueness.
 * 
 * @author Karthik.
 * 
 */
public interface UniqueUserNameGenerationService {
    /**
     * Generates a unique user name.
     * 
     * @return Customer - The customer with the generated user name or NULL if
     *         it cannot generate a unique user name.
     */
    Customer generate();
}
