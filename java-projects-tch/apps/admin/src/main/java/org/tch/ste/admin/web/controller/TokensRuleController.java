package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_DOLLAR_AMOUNT_EXPIRED_TOKENS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_DOLLAR_AMOUNT_EXPIRE_DPI;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_DPI_PER_DAY;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_EXPIRATION_AFTER_NUMBER_OF_DETOKENIZED_REQUESTS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_NUMBER_OF_DETOKENIZED_REQUEST;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_NUMBER_OF_TOKENS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REAL_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TIME_AFTER_EXPIRATION_TOKEN_REUSE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKENS_RULES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRAATION_IN_MINUTES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRATION_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRATION_MINUTES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_PULL_ENABLE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_PUSH_CREATION;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOOKEN_PULL_ENABLE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOOKEN_PUSH_CREATION;

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
import org.tch.ste.admin.domain.dto.TokenRule;
import org.tch.ste.admin.domain.dto.TokenRuleLoadResponse;
import org.tch.ste.admin.service.core.tokenrules.TokenRulesService;

/**
 * Controller to handle token rules information.
 * 
 * @author ramug
 * 
 */
@Controller
@RequestMapping(C_TOKENS_RULES)
public class TokensRuleController implements MessageSourceAware {
    /*
     * private static Logger logger = LoggerFactory
     * .getLogger(TokensRuleController.class);
     */
    private static Logger logger = LoggerFactory.getLogger(TokensRuleController.class);
    /**
     * Issuer Service bean reference.
     */
    @Autowired
    private TokenRulesService tokenRulesService;

    private MessageSource messageSource;

    @Autowired
    @Qualifier("tokenRulesValidator")
    private Validator tokenRulesValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(tokenRulesValidator);
    }

    /**
     * @param iisn
     *            Accepts iisn value.
     * @param bin
     *            String - The bin for which you need to fetch the token rules.
     * @return list of token rules and fetching that token rules in view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    TokenRuleLoadResponse loadListOfTokenRules(@RequestParam(value = "iisn", required = false) String iisn,
            @RequestParam(value = "bin", required = false) String bin) {
        TokenRuleLoadResponse response = new TokenRuleLoadResponse();
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder("Token Requestor rules screen requested by ");
            logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
            logBuilder.append(" for IISN = ");
            logBuilder.append(iisn);
            logger.info(logBuilder.toString());
        }
        if (iisn != null && bin == null) {
            List<String> tokenBins = tokenRulesService.loadListOfTokenBins();
            if (!tokenBins.isEmpty()) {
                response.setSuccess(true);
                response.setBins(tokenBins);
            }
        } else if (bin != null) {
            response.setTokenRule(tokenRulesService.get(bin));
            response.setSuccess(true);
        }
        return response;
    }

    /**
     * It saves the token rules information.
     * 
     * @param iisn
     *            iisn value.
     * @param tokenRule
     *            It stores the token rules values.
     * @param result
     *            which is used to bind the errors result to object.
     * @param request
     *            request object to get the locale data.
     * @return the value of token rules will return.
     */
    @RequestMapping(value = "/saveTokenRules", method = RequestMethod.POST)
    public @ResponseBody
    TokenRuleLoadResponse saveTokenRules(@RequestParam(value = "iisn", required = false) String iisn,
    		@ModelAttribute @Valid TokenRule tokenRule, BindingResult result, HttpServletRequest request) {
        TokenRuleLoadResponse response = new TokenRuleLoadResponse();
        if (result.hasErrors()) {
            response.setSuccess(false);
            FieldError error;
            if ((error = result.getFieldError(C_DOLLAR_AMOUNT_EXPIRE_DPI)) != null) {
                response.setDollarAmountForExpireDpiErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_DOLLAR_AMOUNT_EXPIRED_TOKENS }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_DPI_PER_DAY)) != null) {
                response.setDpiPerDayErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_NUMBER_OF_TOKENS }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_NUMBER_OF_DETOKENIZED_REQUEST)) != null) {
                response.setNumberOfDetokenizedRequestsErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_EXPIRATION_AFTER_NUMBER_OF_DETOKENIZED_REQUESTS }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_REAL_BIN)) != null) {
                response.setRealBinErrorMessage(messageSource.getMessage(error.getCode(), new String[] { C_BIN },
                        request.getLocale()));
            }
            if ((error = result.getFieldError(C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE)) != null) {
                response.setTimeInMinutesAfterExpirationForReuseErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_TIME_AFTER_EXPIRATION_TOKEN_REUSE }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_TOKEN_EXPIRATION_MINUTES)) != null) {
                response.setTokenExpirationMinutesErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_TOKEN_EXPIRAATION_IN_MINUTES }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION)) != null) {
                response.setTokenExpireAfterSingleDollarAmountTransactionErrorMessage(messageSource.getMessage(
                        error.getCode(), new String[] { C_TOKEN_EXPIRATION_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION },
                        request.getLocale()));
            }
            if ((error = result.getFieldError(C_TOKEN_PULL_ENABLE)) != null) {
                response.setTokenPullEnableErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_TOOKEN_PULL_ENABLE }, request.getLocale()));
            }
            if ((error = result.getFieldError(C_TOKEN_PUSH_CREATION)) != null) {
                response.setTokenPushOnCreationErrorMessage(messageSource.getMessage(error.getCode(),
                        new String[] { C_TOOKEN_PUSH_CREATION }, request.getLocale()));
            }

            return response;
        }
        tokenRulesService.save(tokenRule);
        response.setSuccess(true);
        if (logger.isInfoEnabled()) {
            StringBuilder logBuilder = new StringBuilder("Token rules updated for Token BIN ");
            logBuilder.append(tokenRule.getRealBin());
            logBuilder.append(" by user ");
            logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
            logBuilder.append(" for IISN ");
            logBuilder.append(iisn);
            logger.info(logBuilder.toString());
        }
        response.setSuccessMessage(messageSource.getMessage("TOKEN_RULES_SUCCESS_MESSAGE", new String[] {},
                request.getLocale()));
        return response;
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
}
