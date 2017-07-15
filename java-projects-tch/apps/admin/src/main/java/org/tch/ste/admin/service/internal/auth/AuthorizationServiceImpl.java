/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tch.ste.admin.constant.RoleType;

/**
 * Implements the authorization interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.internal.auth.AuthorizationService#hasRole(
     * java.lang.String, org.tch.ste.admin.constant.RoleType)
     */
    @Override
    public boolean hasRole(String userId, RoleType type) {
        boolean retVal = false;
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (type.name().equals(authority.getAuthority())) {
                retVal = true;
            }
        }
        return retVal;
    }

}
