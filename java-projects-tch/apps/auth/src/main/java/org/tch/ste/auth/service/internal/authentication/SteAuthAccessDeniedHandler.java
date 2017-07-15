/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

/**
 * Access Denied Handler.
 * 
 * @author Karthik.
 * 
 */
@Service("steAccessDeniedHandler")
public class SteAuthAccessDeniedHandler implements AccessDeniedHandler {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.access.AccessDeniedHandler#handle(javax
     * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.access.AccessDeniedException)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // TODO Auto-generated method stub

    }

}
