/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.tch.ste.admin.domain.dto.Login;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Override
    public boolean authenticate(Login login) {
        boolean retVal = true;
        Authentication request = new UsernamePasswordAuthenticationToken(login.getLoginId(), login.getPassword());
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
