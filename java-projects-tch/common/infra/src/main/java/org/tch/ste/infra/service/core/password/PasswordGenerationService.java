/**
 * 
 */
package org.tch.ste.infra.service.core.password;

import org.tch.ste.domain.dto.Password;

/**
 * Interface to define business service methods for password and related
 * activities
 * 
 * @author kjanani
 * 
 */
public interface PasswordGenerationService {

    /**
     * This method is used to generate a random password
     * 
     * 
     * @return String - password
     */
    Password generatePassword();

}
