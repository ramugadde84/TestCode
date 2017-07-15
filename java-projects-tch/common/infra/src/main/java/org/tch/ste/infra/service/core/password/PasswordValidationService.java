/**
 * 
 */
package org.tch.ste.infra.service.core.password;

/**
 * Validates the provided password.
 * 
 * @author Karthik.
 * 
 */
public interface PasswordValidationService {
    /**
     * Validates the password according to the following rules: - Must be 12
     * characters in length. - Must contain at least 1 upper case letter, 1
     * lower case letter and 1 number - Must not contain any special characters
     * 
     * @param password
     *            String - The password.
     * @return boolean - True if all rules pass.
     */
    boolean validate(String password);
}
