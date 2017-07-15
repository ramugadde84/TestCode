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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.domain.constant.BoolInt;
import org.tch.ste.domain.constant.BoolStr;

/**
 * Logout success handling.
 * 
 * @author Karthik.
 * 
 */
@Service("logoutHandler")
public class SteLogoutHandler implements LogoutSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(SteLogoutHandler.class);

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
        logger.info("Administrative user {} logged out.", authentication.getName());
        int logoutParam = BoolInt.TRUE.ordinal();

        if (BoolStr.Y.name().equals(request.getParameter(AdminControllerConstants.IS_SESSION_EXPIRED))) {
            logoutParam = BoolInt.FALSE.ordinal();
        }

        response.sendRedirect(request.getContextPath() + "/app/login?"
                + AdminControllerConstants.C_REQUEST_PARAM_FROM_LOGOUT + "=" + logoutParam);
    }

}
