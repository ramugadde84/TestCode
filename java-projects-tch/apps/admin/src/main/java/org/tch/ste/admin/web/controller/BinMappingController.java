package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_MAPPING_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.FIELD_NAME_ISSUER_IISN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_REAL_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_IISN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKENBIN_LABEL;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REALBIN_LABEL;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.admin.domain.dto.BinMappingSaveResponse;
import org.tch.ste.admin.service.core.mapping.BinMappingService;
import org.tch.ste.admin.web.controller.lib.BinMappingValidator;

/**
 * @author pamartheepan
 * 
 */
@Controller
@RequestMapping(C_MAPPING_REQ_MAPPING)
public class BinMappingController implements MessageSourceAware {

    private static Logger logger = LoggerFactory.getLogger(BinMappingController.class);

    private static final String REAL_BIN_FIELD_NAME = "realBin";

    private static final String TOKEN_BIN_FIELD_NAME = "tokenBin";

    @Autowired
    private BinMappingService binMappingService;

    @Autowired
    private BinMappingValidator binMappingValidator;

    private MessageSource messageSource;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(binMappingValidator);
    }

    /**
     * Save bin mapping details.
     * 
     * @param binMapping
     *            BinMapping - The bin mapping.
     * @param result
     *            BindingResult - The binding result.
     * @param request
     *            HttpServletRequest - The request.
     * @return BinMappingSaveResponse - The response.
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    BinMappingSaveResponse saveBinMapping(@Valid @ModelAttribute BinMapping binMapping, BindingResult result,
            HttpServletRequest request) {

        String contextUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (binMapping.getId() == null) {
            logger.info("Add BIN mapping screen requested by {}.", contextUser);
        } else {
            logger.info("Edit BIN mapping screen requested by {} for real BIN = {}.", contextUser,
                    binMapping.getRealBin());
        }
        BinMappingSaveResponse retVal = new BinMappingSaveResponse();
        retVal.setSuccess(false);
        if (result.hasErrors()) {
            populateErrorsInResponse(retVal, result, request);
            return retVal;
        }
        binMappingService.saveBinMapping(binMapping);
        retVal.setSuccess(true);
        retVal.setBinMapping(binMapping);
        return retVal;
    }

    /**
     * Fetches the bin mapping.
     * 
     * @param iisn
     *            String - The iisn.
     * @param id
     *            Integer - The iid.
     * @return BinMapping - The bin mapping.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    BinMapping getBinMapping(@RequestParam(value = FIELD_NAME_ISSUER_IISN, required = false) String iisn,
            @RequestParam(value = C_REQUEST_PARAM_ISSUER_ID, required = false) Integer id) {
        BinMapping binMapping = null;
        if (id != null) {
            binMapping = binMappingService.getBinMapping(id);
            binMapping.setIisn(iisn);
        }
        return binMapping;
    }

    /**
     * Populates the errors in the response object.
     * 
     * @param response
     *            BinMappingSaveResponse- The response.
     * @param result
     *            BindingResult - The binding result.
     * @param request
     *            HttpServletRequest - The request.
     */
    private void populateErrorsInResponse(BinMappingSaveResponse response, BindingResult result,
            HttpServletRequest request) {
        for (FieldError fieldError : result.getFieldErrors()) {
            if (REAL_BIN_FIELD_NAME.equals(fieldError.getField())) {
                response.setRealBinErrMsg(messageSource.getMessage(fieldError.getCode(),
                        new String[] { C_REALBIN_LABEL }, request.getLocale()));
            } else if (TOKEN_BIN_FIELD_NAME.equals(fieldError.getField())) {
                response.setTokenBinErrMsg(messageSource.getMessage(fieldError.getCode(),
                        new String[] { C_TOKENBIN_LABEL }, request.getLocale()));
            }
        }
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
     * @return BinMappingSaveResponse - The response.
     */
    @ExceptionHandler(Throwable.class)
    public @ResponseBody
    Object handleException(HttpServletRequest request, Throwable t) {
        Object retVal = null;
        String arg = null;

        String realBin = request.getParameter(C_FIELD_NAME_REAL_BIN);
        String tokenBin = request.getParameter(C_FIELD_NAME_TOKEN_BIN);
        String iisn = request.getParameter(C_FIELD_NAME_IISN);
        if (!"GET".equals(request.getMethod())) {

            BinMappingSaveResponse response = new BinMappingSaveResponse();
            String id = request.getParameter(C_REQUEST_PARAM_ID).trim();

            if (id.isEmpty()) {
                arg = "adding";
                logger.error("Error while adding BIN Mapping for Real BIN " + realBin + " to Token BIN " + tokenBin
                        + " for Issuer " + iisn + ".");
            } else {
                arg = "editing";
                logger.error("Error while editing BIN Mapping for Real BIN " + realBin + " to Token BIN " + tokenBin
                        + " for Issuer " + iisn + ".");
            }
            response.setTokenBinErrMsg(messageSource.getMessage("BIN_MAPPING_ERROR", new String[] { arg, realBin,
                    tokenBin, iisn }, request.getLocale()));
            retVal = response;
        }
        return retVal;
    }
}
