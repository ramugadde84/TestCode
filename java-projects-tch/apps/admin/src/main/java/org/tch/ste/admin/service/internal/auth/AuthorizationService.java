/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import org.tch.ste.admin.constant.RoleType;

/**
 * Interface which offers methods for authorization.
 * 
 * @author Karthik.
 * 
 */
public interface AuthorizationService {
    /**
     * Checks if the user has the given role.
     * 
     * @param userId
     *            String - The user.
     * @param type
     *            RoleType - The role type.
     * @return boolean - true if user has the role, false otherwise.
     */
    boolean hasRole(String userId, RoleType type);
}
