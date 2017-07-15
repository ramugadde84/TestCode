package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NAME;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_TOKEN_REQUESTOR;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_MAPPING_TOKEN_REQ_DEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKENREQUESTOR_ID_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKENREQUESTOR_NAME_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_REQUESTOR_LIST_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminViewConstants.V_TOKEN_REQUESTOR_PAGE;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.admin.constant.TokenRequestorErrorCode;
import org.tch.ste.admin.domain.dto.TokenRequestor;
import org.tch.ste.admin.domain.dto.TokenRequestorDeleteResponse;
import org.tch.ste.admin.domain.dto.TokenRequestorResponse;
import org.tch.ste.admin.domain.dto.TokenRequestorSaveResponse;
import org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorService;
import org.tch.ste.infra.util.JsonObjectConverter;

/**
 * 
 * @author anus
 * 
 */
@Controller
@RequestMapping(C_TOKEN_REQ_MAPPING)
public class TokenRequestorController implements MessageSourceAware {
    private static Logger logger = LoggerFactory.getLogger(TokenRequestorController.class);

    @Autowired
    private TokenRequestorService tokenRequestorService;

    private MessageSource messageSource;

    @Autowired
    @Qualifier("tokenRequestorValidator")
    private Validator tokenReqValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(tokenReqValidator);
    }

    /**
     * Load all tokenRequestors and resolve the request to token requestor page.
     * 
     * @param model
     *            -The model
     * 
     * @return String - return token requestor page
     * 
     */
    @RequestMapping(value = C_TOKEN_REQUESTOR_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public String loadtokenRequestors(Model model) {
        model.addAttribute(C_MODEL_ATTR_TOKEN_REQUESTOR,
                new JsonObjectConverter<List<TokenRequestor>>().stringify(tokenRequestorService.loadTokenRequestors()));

        if (logger.isInfoEnabled()) {
            logger.info("Token Requestor Management Main Menu Viewed by user {}", SecurityContextHolder.getContext()
                    .getAuthentication().getName());
        }
        return V_TOKEN_REQUESTOR_PAGE;
    }

    /**
     * 
     * @param id
     *            Integer - The id.
     * @return TokenRequestor
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    TokenRequestor getTokenRequestor(@RequestParam(value = C_REQUEST_PARAM_ISSUER_ID, required = false) Integer id) {
        TokenRequestor tokenRequestor;
        if (id == null) {
            tokenRequestor = new TokenRequestor();
        } else {
            tokenRequestor = tokenRequestorService.getTokenRequestorDtls(id);
        }
        return tokenRequestor;
    }

    /**
     * Save the token requestors and resolve the request to token requestor
     * page.
     * 
     * @param tokenRequestor
     *            - Form object from user interface, which binds the token
     *            requestor information
     * @param result
     *            BindingResult - The binding result.
     * @param request
     *            HttpServletRequest - The request.
     * 
     * @return String - return add/edit token requestor page
     * 
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    TokenRequestorSaveResponse saveTokenRequestor(@Valid @ModelAttribute TokenRequestor tokenRequestor,
            BindingResult result, HttpServletRequest request) {
        TokenRequestorSaveResponse retVal = new TokenRequestorSaveResponse();

        if (result.hasErrors()) {
            retVal.setSuccess(false);
            FieldError error;
            if ((error = result.getFieldError(C_FIELD_NAME_TOKEN_REQUESTOR_NAME)) != null) {
                retVal.setNameErrorMsg(messageSource.getMessage(error.getCode(),
                        new String[] { C_TOKENREQUESTOR_NAME_LABEL }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID)) != null) {
                retVal.setNetworkIdErrorMsg(messageSource.getMessage(error.getCode(),
                        new String[] { C_TOKENREQUESTOR_ID_LABEL }, request.getLocale()));
            }
            return retVal;
        }
        String action = (tokenRequestor.getId() != null) ? "updated" : "added";
        tokenRequestorService.saveTokenRequestor(tokenRequestor);
        retVal.setSuccess(true);
        retVal.setSaveSuccessMsg(messageSource.getMessage(TokenRequestorErrorCode.SAVE_TOKEN_REQUESTOR_MESSAGE.name(),
                new String[] { tokenRequestor.getName() }, request.getLocale()));
        retVal.setTokenRequestor(tokenRequestor);
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder("Token Requestor ");
            logBuilder.append(tokenRequestor.getNetworkId());
            logBuilder.append(" ");
            logBuilder.append(action);
            logBuilder.append(" by ");
            logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
            logBuilder.append(". Token Requestor name: ");
            logBuilder.append(tokenRequestor.getName());
            logBuilder.append('.');
            logger.info(logBuilder.toString());
        }
        return retVal;
    }

    /**
     * Delete the token requestors and resolve the request to token requestor
     * page.
     * 
     * @param tokenRequestor
     *            - Form object from user interface, which binds the token
     *            requestor information
     * @param request
     *            HttpServletRequest - The request object.
     * 
     * @return String - return token requestor list page
     * 
     */
    @RequestMapping(method = RequestMethod.POST, value = C_REQUEST_MAPPING_TOKEN_REQ_DEL)
    public @ResponseBody
    TokenRequestorDeleteResponse deleteTokenRequestor(@ModelAttribute TokenRequestor tokenRequestor,
            HttpServletRequest request) {
        TokenRequestorDeleteResponse tokenRequestorDeleteRes = new TokenRequestorDeleteResponse();
        TokenRequestor deletedTokenRequestor = tokenRequestorService.deleteTokenRequestor(tokenRequestor);
        Object[] args = new Object[] { deletedTokenRequestor.getName() };
        tokenRequestorDeleteRes.setTokenRequestor(tokenRequestor);
        tokenRequestorDeleteRes.setDeleteMessage(messageSource.getMessage(
                TokenRequestorErrorCode.DELETE_TOKEN_REQUESTOR_MESSAGE.name(), args, request.getLocale()));
        tokenRequestorDeleteRes.setSuccess(true);
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder("Token Requestor ");
            logBuilder.append(deletedTokenRequestor.getNetworkId());
            logBuilder.append(" removed by ");
            logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
            logBuilder.append(". Token Requestor name: ");
            logBuilder.append(deletedTokenRequestor.getName());
            logBuilder.append('.');
            logger.info(logBuilder.toString());
        }
        return tokenRequestorDeleteRes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
     * springframework.context.MessageSource)
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles exception.
     * 
     * @param request
     *            HttpServletRequest - The request.
     * @param t
     *            Throwable - The exception.
     * @return TokenRequestorSaveResponse - The response.
     */
    @ExceptionHandler(Throwable.class)
    public @ResponseBody
    Object handleException(HttpServletRequest request, Throwable t) {
        Object retVal = null;
        String arg = null;
        String networkId = request.getParameter(C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID);
        networkId = networkId != null ? networkId.trim() : "";

        if (!"GET".equals(request.getMethod())) {
            TokenRequestorResponse response = null;
            if (request.getRequestURI().endsWith(C_REQUEST_MAPPING_TOKEN_REQ_DEL)) {
                response = new TokenRequestorDeleteResponse();
                arg = "delete";
            } else {
                response = new TokenRequestorSaveResponse();
                arg = "save";
            }
            response.setErrorMessage(messageSource.getMessage("TOKEN_REQUESTOR_ERROR", new String[] { arg, networkId },
                    request.getLocale()));
            retVal = response;
        } else {
            arg = "edit";
        }
        // String id = request.getParameter(C_REQUEST_PARAM_ISSUER_ID);
        if (logger.isErrorEnabled()) {
            StringBuilder logBuilder = new StringBuilder("Error attempting to ");
            logBuilder.append(arg);
            logBuilder.append(" Token Requestor");
            if (!"".equals(networkId)) {
                logBuilder.append(" ");
                logBuilder.append(networkId);
            }
            logBuilder.append(". ");
            logBuilder.append(t);
            logger.error(logBuilder.toString());
        }
        return retVal;
    }
}
