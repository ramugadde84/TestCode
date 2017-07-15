/**
 * 
 */
package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_FIRST_NAME;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_IISN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_LAST_NAME;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_USER_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_USER_ROLES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIRSTNAME_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_IISN_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_LASTNAME_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ROLES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_MAPPING_USER_DEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_ROLES_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_USERID_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_USER_URL;
import static org.tch.ste.admin.constant.AdminViewConstants.V_USER_PAGE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.admin.constant.UserConfigurationErrorCode;
import org.tch.ste.admin.domain.dto.UserConfigurationDeleteResponse;
import org.tch.ste.admin.domain.dto.UserConfigurationProperties;
import org.tch.ste.admin.domain.dto.UserConfigurationSaveResponse;
import org.tch.ste.admin.domain.entity.Role;
import org.tch.ste.admin.service.core.configuration.UserConfigurationService;
import org.tch.ste.admin.service.core.issuer.IssuerService;
import org.tch.ste.infra.util.JsonObjectConverter;

/**
 * @author sharduls
 * 
 */

@Controller
@RequestMapping(C_USER_URL)
public class UserController implements MessageSourceAware {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserConfigurationService userConfigurationService;

    @Autowired
    @Qualifier("userConfigurationValidator")
    private Validator userConfigurationValidator;

    @Autowired
    private IssuerService issuerService;

    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userConfigurationValidator);
    }

    /**
     * Load all User and their respective roles and resolve the request to
     * issuer page.
     * 
     * @param model
     *            - The model
     * 
     * @return String - return issuer page
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String loadUsers(Model model) {
        if (logger.isInfoEnabled()) {
            logger.info("TCH User Management sub menu Viewed by user {}", SecurityContextHolder.getContext()
                    .getAuthentication().getName());
        }
        List<UserConfigurationProperties> userConfigurationProperties = userConfigurationService.loadUsers();
        model.addAttribute("users",
                new JsonObjectConverter<List<UserConfigurationProperties>>().stringify(userConfigurationProperties));
        model.addAttribute(C_MODEL_ATTR_ROLES, userConfigurationService.getAllRoles());
        model.addAttribute(C_MODEL_ATTR_ISSUER, issuerService.getIssuersSortedAscendingByName());
        return V_USER_PAGE;

    }

    /**
     * 
     * @param userConfigurationProperties
     *            -
     * @param result
     *            -
     * @param request
     *            -
     * @return Redirect to the TCH User Management Page.
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public UserConfigurationSaveResponse saveUser(
            @Valid @ModelAttribute UserConfigurationProperties userConfigurationProperties, BindingResult result,
            HttpServletRequest request) {
        UserConfigurationSaveResponse response = new UserConfigurationSaveResponse();

        if (result.hasErrors()) {

            response.setSuccess(false);
            FieldError error;
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();

            if ((error = result.getFieldError(C_FIELD_NAME_USER_ID)) != null) {
                response.setUserIdErrorMsg(messageSource.getMessage(error.getCode(), new String[] { C_USERID_LABEL },
                        request.getLocale()));
            }
            if ((error = result.getFieldError(C_FIELD_NAME_FIRST_NAME)) != null) {
                response.setFirstNameErrorMsg(messageSource.getMessage(error.getCode(),
                        new String[] { C_FIRSTNAME_LABEL }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_FIELD_NAME_LAST_NAME)) != null) {
                response.setLastNameErrorMsg(messageSource.getMessage(error.getCode(),
                        new String[] { C_LASTNAME_LABEL }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_FIELD_NAME_USER_ROLES)) != null) {
                response.setRolesErrorMsg(messageSource.getMessage(error.getCode(), new String[] { C_ROLES_LABEL },
                        request.getLocale()));
            }
            if ((error = result.getFieldError(C_FIELD_NAME_IISN)) != null) {
                response.setIisnErrorMsg(messageSource.getMessage(error.getCode(), new String[] { C_IISN_LABEL },
                        request.getLocale()));
            }
            // Waring message to be created.
            StringBuilder warnMsg = new StringBuilder();
            warnMsg.append("Missing");
            String iisn = userConfigurationProperties.getIisn();
            if (iisn != null && !iisn.trim().isEmpty()) {
                warnMsg.append(" issuer");
            }
            warnMsg.append(" user ID ").append(userConfigurationProperties.getUserId());
            warnMsg.append(" requested to be added by ").append(userName);
            if (iisn != null && !iisn.trim().isEmpty()) {
                warnMsg.append(" for IISN = ").append(iisn);
            }
            warnMsg.append(".");
            logger.warn(warnMsg.toString());

            return response;
        }

        userConfigurationService.saveUser(userConfigurationProperties);
        response.setSuccess(true);
        response.setSaveSuccessMsg(messageSource.getMessage(UserConfigurationErrorCode.SAVE_USER_MESSAGE.name(),
                new String[] { userConfigurationProperties.getUserId() }, request.getLocale()));
        response.setUserConfigurationProperties(userConfigurationService.getUser(userConfigurationProperties.getId()));

        StringBuilder authorizedRoles = new StringBuilder();
        List<Role> roles = userConfigurationService.getAllRoles();
        Integer[] authorizedRolesKey = userConfigurationProperties.getAuthorizedRoles();
        for (Role role : roles) {
            for (Integer authorizedRole : authorizedRolesKey) {
                if (role.getId().equals(authorizedRole)) {
                    authorizedRoles.append(role.getName()).append(",");
                    break;
                }
            }
        }
        return response;
    }

    /**
     * Fetches the user.
     * 
     * @param id
     *            - Id to Edit the Information for the user.
     * @return String -return issuer Page.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    UserConfigurationProperties getUser(@RequestParam(value = C_REQUEST_PARAM_ID, required = false) Integer id) {
        UserConfigurationProperties userConfigurationProperties;

        if (id == null || id == 0) {
            userConfigurationProperties = new UserConfigurationProperties();
        } else {
            userConfigurationProperties = userConfigurationService.getUser(id);
        }
        return userConfigurationProperties;
    }

    /**
     * Delete the User and resolve the request to TCH User Management Page.
     * page.
     * 
     * @param userConfigurationProperties
     *            - Form object from user interface, which binds the user
     *            information
     * 
     * @param request
     *            HttpServletRequest - The request.
     * 
     * @return String - return TCH User Management page.
     * 
     */
    @RequestMapping(method = RequestMethod.POST, value = C_REQUEST_MAPPING_USER_DEL)
    public @ResponseBody
    UserConfigurationDeleteResponse deleteUser(@ModelAttribute UserConfigurationProperties userConfigurationProperties,
            HttpServletRequest request) {
        UserConfigurationDeleteResponse userConfigurationDeleteResponse = new UserConfigurationDeleteResponse();
        userConfigurationService.deleteUser(userConfigurationProperties);
        userConfigurationDeleteResponse.setUserConfigurationProperties(userConfigurationProperties);
        userConfigurationDeleteResponse.setSuccess(true);
        String userId = userConfigurationService.getUser(userConfigurationProperties.getId()).getUserId();
        userConfigurationDeleteResponse.setDeleteMessage(messageSource.getMessage(
                UserConfigurationErrorCode.DELETE_USER_MESSAGE.name(), new String[] { userId }, request.getLocale()));

        if (logger.isInfoEnabled()) {
            logger.info("Credentials removed by {} for user {}.", SecurityContextHolder.getContext()
                    .getAuthentication().getName(), userId);
        }
        return userConfigurationDeleteResponse;

    }

    /**
     * @param messageSource
     *            the messageSource to set
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
