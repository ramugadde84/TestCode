/**
 * 
 */
package org.tch.ste.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.admin.constant.AdminControllerConstants;

/**
 * Method to keep the session alive.
 * 
 * @author Karthik.
 * 
 */
@Controller
@RequestMapping(AdminControllerConstants.C_KEEP_ALIVE_MAPPING)
public class KeepAliveController {

    /**
     * Keep alive function.
     * 
     * @return boolean - Always true.
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    boolean keepAlive() {
        return true;
    }
}
