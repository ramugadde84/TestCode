/**
 * 
 */
package org.tch.ste.auth.web.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.auth.constant.AuthControllerConstants;
import org.tch.ste.auth.constant.AuthViewConstants;
import org.tch.ste.auth.dto.Login;
import org.tch.ste.auth.dto.LoginModel;
import org.tch.ste.auth.dto.LoginResponse;
import org.tch.ste.auth.exception.EmptyPaymentInstrumentException;
import org.tch.ste.auth.service.core.login.LoginService;
import org.tch.ste.auth.service.internal.tokenrequestor.TokenRequestorUrlService;
import org.tch.ste.auth.util.ErrorMessageUtil;

/**
 * Implements the login mechanism.
 * 
 * @author Karthik.
 * 
 */
@Controller
@RequestMapping(AuthControllerConstants.C_REQUEST_MAPPING_LOGIN)
public class LoginController implements MessageSourceAware {

	private static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private TokenRequestorUrlService tokenRequestorUrlService;

	private MessageSource messageSource;

	/**
	 * Fetches the login page.
	 * 
	 * @param iisn
	 *            String - The iisn.
	 * @param trId
	 *            String - the TRID of the Wallet.
	 * @param ciid
	 *            String - The CIID.
	 * @param model
	 *            Model - The model.
	 * 
	 * @return String - The login view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = AuthControllerConstants.C_REQ_PARAM_IISN, required = false) String iisn,
			@RequestParam(value = AuthControllerConstants.C_REQ_PARAM_TRID, required = false) String trId,
			@RequestParam(value = AuthControllerConstants.C_REQ_PARAM_CIID, required = false) String ciid,
			Model model) {
		String retVal = AuthViewConstants.V_START_PAGE;
		try {
			logger.info(
					"Token requestor registration requested for TRID = {}, IISN = {}",
					new Object[] { trId, iisn });
			if (loginService.validateTridAndIisn(trId, iisn)) {

				LoginModel loginModel = loginService.getLoginModel(trId, iisn);
				loginModel.setIisn(iisn);
				loginModel.setTrId(trId);
				loginModel.setCiid(ciid);
				model.addAttribute(AuthControllerConstants.C_MODEL_ATTR_LOGIN,
						loginModel);
			} else {
				
				retVal = "redirect:/" + AuthViewConstants.V_BLANK_PAGE
						+ tokenRequestorUrlService.formUrl(trId, false);
			}
		} catch (Throwable t) {
			retVal = "redirect:/" + AuthViewConstants.V_BLANK_PAGE
					+ tokenRequestorUrlService.formUrl(trId, false);
		}
		return retVal;
	}

	/**
	 * Perform the login.
	 * 
	 * @param login
	 *            Login - The login DTO.
	 * @param result
	 *            BindingResult - The associated binding result.
	 * @param request
	 *            HttpServletRequest - The request.
	 * @return LoginResponse - The response.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	LoginResponse login(@Valid @ModelAttribute Login login,
			BindingResult result, HttpServletRequest request) {

		if (logger.isInfoEnabled()) {
			logger.info(
					"Login attempt for user = {}, IISN = {}, TRID = {}, CIID = {}",
					new Object[] { login.getUserId(), login.getIisn(),
							login.getTrId(), login.getCiid() });
		}
		LoginResponse retVal = new LoginResponse();
		if (!result.hasErrors()) {
			retVal = loginService.login(login, request.getRemoteAddr(), request
					.getHeader(AuthControllerConstants.C_HEADER_USER_AGENT));
			if (retVal.isSuccess() && logger.isInfoEnabled()) {
				logger.info(
						"Payment instrument list display for user = {}, TRID = {}, IISN = {}, CIID = {}",
						new Object[] {
								login.getUserId(),
								login.getTrId(),
								login.getIisn(),
								(login.getCiid() == null ? "" : login.getCiid()) });
			}
		} else {
			for (FieldError fieldError : result.getFieldErrors()) {
				ErrorMessageUtil.addErrorMessage(messageSource,
						fieldError.getField(), fieldError.getCode(), retVal,
						new Object[] { fieldError.getField() }, null);
			}
		}
		login.setPassword(null);
		retVal.setLogin(login);
		return retVal;
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
	 * Handles Illegal arguments.
	 * 
	 * @param request
	 *            HttpServletRequest- The servlet request.
	 * @param exception
	 *            IllegalArgumentException - The exception.
	 * @return LoginResponse - The response.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody
	LoginResponse handleIllegalArgument(HttpServletRequest request,
			IllegalArgumentException exception) {
		String user = request
				.getParameter(AuthControllerConstants.C_REQUEST_PARAM_USER_ID);
		String iisn = request
				.getParameter(AuthControllerConstants.C_REQUEST_PARAM_IISN);
		logger.error(
				"An error occurred attempting to authenticate user {} with issuer {}.",
				user, iisn);
		LoginResponse retVal = new LoginResponse();
		retVal.setSuccess(false);
		Login login = new Login();
		login.setIisn(iisn);
		login.setUserId(user);
		retVal.setLogin(login);
		return retVal;
	}

	/**
	 * Handles any Error or Exception during Payment Instrument Processing.
	 * 
	 * @param request
	 *            HttpServletRequest - The Servlet request.
	 * @param throwable
	 *            Throwable - any Exception or Error Occurred.
	 * @return String - The view name.
	 */
	@ExceptionHandler(Throwable.class)
	public String handleException(HttpServletRequest request,
			Throwable throwable) {
		logger.error("Exception occured while logging in", throwable);
		return AuthViewConstants.V_START_PAGE;
	}

	/**
	 * Handles when Customer doesn't have any Payment Instruments.
	 * 
	 * @param request
	 *            HttpServletRequest - The Servlet request.
	 * @param exception
	 *            IllegalStateException - The exception.
	 * @return LoginResponse - The Response.
	 */
	@ExceptionHandler(EmptyPaymentInstrumentException.class)
	@ResponseBody
	LoginResponse handleEmptyPaymentInstrumentException(
			HttpServletRequest request,
			EmptyPaymentInstrumentException exception) {
		LoginResponse retVal = new LoginResponse();
		retVal.setLocked(true);
		retVal.setSuccess(true);
		retVal.setRedirectUrl(AuthViewConstants.V_BLANK_PAGE
				+ tokenRequestorUrlService.formUrl(
						request.getParameter(AuthControllerConstants.C_REQ_PARAM_TRID),
						true));
		retVal.setErrorMessage("There are no cards available for selection. Please contact customer support.");
		Login login = new Login();
		login.setIisn(request
				.getParameter(AuthControllerConstants.C_REQUEST_PARAM_IISN));
		login.setUserId(request
				.getParameter(AuthControllerConstants.C_REQUEST_PARAM_USER_ID));
		retVal.setLogin(login);
		return retVal;
	}

}
