/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.dto.Login;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service("internalAuthenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.authentication.AuthenticationService
     * #authenticate(org.tch.ste.auth.dto.Login)
     */
    @Override
    public boolean authenticate(Login login) {
        boolean retVal = true;
        Authentication request = new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword());
        try {
            Authentication result = authenticationManager.authenticate(request);
            retVal = result.isAuthenticated();
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (Exception e) {
            retVal = false;
            logger.warn("Exception while authenticating", e);
        }
        return retVal;
    }

}
