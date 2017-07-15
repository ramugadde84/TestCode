/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * Logout.
 * 
 * @author Karthik.
 * 
 */
@Service("logoutHandler")
public class SteAuthLogoutHandler implements LogoutSuccessHandler {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.authentication.logout.LogoutSuccessHandler
     * #onLogoutSuccess(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

    }

}
