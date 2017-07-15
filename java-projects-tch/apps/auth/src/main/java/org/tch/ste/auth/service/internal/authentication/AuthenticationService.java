/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import org.tch.ste.auth.dto.Login;

/**
 * Authenticates for the auth app.
 * 
 * @author Karthik.
 * 
 */
public interface AuthenticationService {
    /**
     * Authenticates the payment instruments.
     * 
     * @param login
     *            Login - The login DTO.
     * @return boolean - True/False.
     */
    public boolean authenticate(Login login);
}
