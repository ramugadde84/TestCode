/**
 * 
 */
package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_LOGIN_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_FROM_LOGOUT;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_LOGIN_PAGE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.domain.dto.Login;
import org.tch.ste.admin.service.core.login.LoginService;
import org.tch.ste.admin.web.controller.lib.ErrorHandler;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.util.DateUtil;

/**
 * Controller class which handles the login.
 * 
 * URLS: GET /login - Login Page POST /login - Submit Login credentials and
 * Login
 * 
 * @author Karthik.
 * 
 */
@Controller
@RequestMapping(C_LOGIN_REQ_MAPPING)
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private VaultProperties vaultProperties;

    /**
     * Fetch the login page.
     * 
     * @param fromLogout
     *            Integer - Whether the request is from a logout session.
     * 
     * @param model
     *            Model - The model.
     * 
     * @return String - The JSP name.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = C_REQUEST_PARAM_FROM_LOGOUT, required = false) Integer fromLogout,
            Model model) {
        model.addAttribute("login", new Login());
        if (fromLogout != null) {
            if (fromLogout == 1) {
                model.addAttribute(AdminConstants.LOGOUT_MSG, AdminConstants.LOGOUT_MSG_CODE);
            } else {
                model.addAttribute(AdminConstants.LOGOUT_MSG, AdminConstants.SESSION_EXPIRED_MSG);
            }
        }
        return V_LOGIN_PAGE;
    }

    /**
     * Logs in the user.
     * 
     * @param login
     *            Login - The bound login object.
     * @param result
     *            Binding Result - The binding result.
     * @param model
     *            Model - The model.
     * @return String - The home page if successful or the same login page with
     *         an error.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute Login login, BindingResult result, Model model) {

        if (result.hasErrors()) {
            ErrorHandler.addErrorsToModel(result, model);
            return V_LOGIN_PAGE;
        }

        boolean isLoggedIn = loginService.login(login);

        if (isLoggedIn) {
            String userId = login.getLoginId();
            model.addAttribute(AdminConstants.LAST_LOGIN_TIME, loginService.getLastLoginTime(userId));
            loginService.setCurrentLoginTime(userId, DateUtil.getUtcTime());
            logger.info("Administration Main Menu Viewed by user "
                    + SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute(AdminControllerConstants.C_MODEL_ATTR_WELCOME_MSG,
                    vaultProperties.getKey(AdminConstants.WELCOME_MSG));
            return V_HOME_PAGE;
        }
        model.addAttribute(AdminConstants.LOGIN_ERROR, AdminConstants.LOGIN_ERR_MSG);
        return V_LOGIN_PAGE;

    }

}
