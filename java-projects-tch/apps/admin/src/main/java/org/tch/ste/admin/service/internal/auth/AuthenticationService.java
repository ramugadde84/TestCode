/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import org.tch.ste.admin.domain.dto.Login;

/**
 * Interface for authentication options.
 * 
 * @author Karthik.
 * 
 */
public interface AuthenticationService {
    /**
     * Authenticates the given user.
     * 
     * @param login
     * @return boolean - True if successful, false if not.
     */
    boolean authenticate(Login login);
}
