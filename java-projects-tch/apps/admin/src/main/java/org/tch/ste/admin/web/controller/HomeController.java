/**
 * 
 */
package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_HOME_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.infra.configuration.VaultProperties;

/**
 * The home controller.
 * 
 * @author Karthik.
 * 
 */
@Controller
@RequestMapping(C_HOME_REQ_MAPPING)
public class HomeController {

    @Autowired
    private VaultProperties vaultProperties;

    private static Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Returns the home page.
     * 
     * @param model
     *            - The model returned to resolved view
     * 
     * @return String - The home page.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        logger.info("Administration Main Menu Viewed by user "
                + SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute(AdminControllerConstants.C_MODEL_ATTR_WELCOME_MSG,
                vaultProperties.getKey(AdminConstants.WELCOME_MSG));
        return V_HOME_PAGE;
    }

}
