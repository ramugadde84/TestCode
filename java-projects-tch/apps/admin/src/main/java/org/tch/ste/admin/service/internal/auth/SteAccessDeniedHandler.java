/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

/**
 * Custom handling for access denied.
 * 
 * @author Karthik.
 * 
 */
@Service("steAccessDeniedHandler")
public class SteAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger logger = LoggerFactory.getLogger(SteAccessDeniedHandler.class);

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
        if (logger.isInfoEnabled()) {
            String userName = "";
            SecurityContext context = SecurityContextHolder.getContext();
            if (context != null) {
                Authentication authentication = context.getAuthentication();
                if (authentication != null) {
                    userName = authentication.getName();
                }
            }
            logger.info("Unauthorized request for the URL {} by user {}", request.getRequestURI(), userName);
        }
        response.sendRedirect(request.getContextPath() + "/app/login");
    }
}

