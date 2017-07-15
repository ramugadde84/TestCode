package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_ISSUER_LIST_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_ISSUER_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_AUTHENTICATION_MECHANISMS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER_DETAILS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER_SAVE_MSG;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER_SAVE_MSG_CODE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_TOKEN_REQUESTORS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REDIRECT_PATH;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_SAVE_MSG;
import static org.tch.ste.admin.constant.AdminViewConstants.V_ISSUER_DETAILS_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_ISSUER_PAGE;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.admin.domain.dto.Issuer;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.admin.service.core.issuer.AuthenticationComparator;
import org.tch.ste.admin.service.core.issuer.IssuerService;
import org.tch.ste.admin.service.core.mapping.BinMappingService;
import org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorFacade;
import org.tch.ste.admin.web.controller.lib.ErrorHandler;
import org.tch.ste.domain.constant.BoolStr;
import org.tch.ste.domain.entity.AuthenticationMechanism;
import org.tch.ste.infra.util.JsonObjectConverter;

/**
 * Controller class to handle issuer configuration requests.
 * 
 * URLS: GET /list - get the list of issuers configured and list
 * 
 * @author janarthanans
 * 
 */
@Controller
@RequestMapping(C_ISSUER_REQ_MAPPING)
public class IssuerController {

    @Autowired
    private BinMappingService binMappingService;

    @Autowired
    private IssuerService issuerService;

    @Autowired
    private TokenRequestorFacade tokenRequestorFacade;

    @Autowired
    @Qualifier("issuerInfoValidator")
    private Validator issuerInfoValidator;

    @Autowired
    @Qualifier("authenticationOptionsValidator")
    private Validator authOptionsValidator;

    @Autowired
    private AuthenticationComparator authenticationComparator;

    private static Logger logger = LoggerFactory.getLogger(IssuerController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(issuerInfoValidator, authOptionsValidator);
    }

    /**
     * Load all issuers and resolve the request to issuer page.
     * 
     * @param model
     *            -The model
     * 
     * @return String - return issuer page
     * 
     */
    @RequestMapping(value = C_ISSUER_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public String listIssuers(Model model) {
        model.addAttribute(C_MODEL_ATTR_ISSUER,
                new JsonObjectConverter<List<Issuer>>().stringify(issuerService.loadIssuers()));
        logger.info("Issuer list screen requested by {} ", SecurityContextHolder.getContext().getAuthentication()
                .getName());
        return V_ISSUER_PAGE;
    }

    /**
     * For add/edit issuer page
     * 
     * @param id
     *            Integer - The issuer id.
     * @param model
     *            - -The model
     * @param session
     *            HttpSession - The session.
     * @return -returns the add issuer page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getIssuerDetails(@RequestParam(value = C_REQUEST_PARAM_ISSUER_ID, required = false) Integer id,
            Model model, HttpSession session) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (id == null) {
            logger.info("Add issuer screen requested by {} ", userName);
            model.addAttribute(C_MODEL_ATTR_ISSUER_DETAILS, new IssuerDetail());
        } else {
            IssuerDetail issuerDetails = issuerService.getIssuerEditDetails(id);
            String iisn = issuerDetails.getIisn();
            logger.info("Edit issuer screen requested by {} for IISN = {}", userName, iisn);
            try {
                String tokenBinMappings = new JsonObjectConverter<List<BinMapping>>().stringify(binMappingService
                        .getBinMappings(iisn));
                issuerDetails.setTokenBinMappings(tokenBinMappings);
            } catch (Throwable t) {
                // Pass.
            }
            model.addAttribute(C_MODEL_ATTR_ISSUER_DETAILS, issuerDetails);
            if (session.getAttribute(C_REQUEST_PARAM_SAVE_MSG) != null) {
                model.addAttribute(C_MODEL_ATTR_ISSUER_SAVE_MSG, C_MODEL_ATTR_ISSUER_SAVE_MSG_CODE);
                session.removeAttribute(C_REQUEST_PARAM_SAVE_MSG);
            }
        }

        List<AuthenticationMechanism> authenticationMechanisms = issuerService.getAllAuthenticationMechanism();
        Collections.sort(authenticationMechanisms, authenticationComparator);
        model.addAttribute(C_MODEL_ATTR_AUTHENTICATION_MECHANISMS, authenticationMechanisms);
        model.addAttribute(C_MODEL_ATTR_TOKEN_REQUESTORS, tokenRequestorFacade.loadTokenRequestors());
        return V_ISSUER_DETAILS_PAGE;
    }

    /**
     * Saves the issuer.
     * 
     * @param issuer
     *            Issuer - The bound login object.
     * @param result
     *            Binding Result - The binding result.
     * @param model
     *            Model - The model.
     * @param redirectAttributes
     *            RedirectAttributes - The redirect attributes.
     * @param session
     *            HttpSession - The session.
     * @return String - The home page if successful or the same login page with
     *         an error.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute IssuerDetail issuer, BindingResult result, Model model,
            RedirectAttributes redirectAttributes, HttpSession session) {
        Integer issuerId = issuer.getId();
        String iisn = issuer.getIisn();
        Integer[] selectedTokenRequestors = issuer.getSelectedTokenRequestors();
        if (result.hasErrors()) {
            ErrorHandler.addErrorsToModel(result, model);
            if (selectedTokenRequestors != null) {
                for (Integer selectedTokenRequestor : selectedTokenRequestors) {
                    issuer.getCurrentTokenRequestors().put(selectedTokenRequestor, Boolean.TRUE);
                }
            }
            if (null != iisn && issuerId != null) {
                try {
                    String tokenBinMappings = new JsonObjectConverter<List<BinMapping>>().stringify(binMappingService
                            .getBinMappings(iisn));
                    issuer.setTokenBinMappings(tokenBinMappings);
                } catch (Throwable t) {
                    // Pass.
                }
            }
            model.addAttribute(C_MODEL_ATTR_ISSUER_DETAILS, issuer);
            model.addAttribute(C_MODEL_ATTR_AUTHENTICATION_MECHANISMS, issuerService.getAllAuthenticationMechanism());
            model.addAttribute(C_MODEL_ATTR_TOKEN_REQUESTORS, tokenRequestorFacade.loadTokenRequestors());

            return V_ISSUER_DETAILS_PAGE;
        }
        logger.info("Issuer with IISN: {} saved by {} ", iisn, SecurityContextHolder.getContext().getAuthentication()
                .getName());
        issuerService.saveIssuerConfiguration(issuer);
        tokenRequestorFacade.savePermittedTokenRequestor(iisn, selectedTokenRequestors);

        redirectAttributes.addAttribute(C_REQUEST_PARAM_ISSUER_ID, issuer.getId());
        session.setAttribute(C_REQUEST_PARAM_SAVE_MSG, BoolStr.Y.name());
        return C_REDIRECT_PATH + C_ISSUER_REQ_MAPPING;
    }

}
