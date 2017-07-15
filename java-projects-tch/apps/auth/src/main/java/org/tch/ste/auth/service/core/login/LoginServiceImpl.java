/**
 * 
 */
package org.tch.ste.auth.service.core.login;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.auth.constant.AuthViewConstants;
import org.tch.ste.auth.dto.AuthPaymentInstrument;
import org.tch.ste.auth.dto.Login;
import org.tch.ste.auth.dto.LoginModel;
import org.tch.ste.auth.dto.LoginResponse;
import org.tch.ste.auth.exception.EmptyPaymentInstrumentException;
import org.tch.ste.auth.service.internal.authentication.AuthenticationService;
import org.tch.ste.auth.service.internal.paymentinstrument.PaymentInstrumentFetchService;
import org.tch.ste.auth.service.internal.tokenrequestor.TokenRequestorUrlService;
import org.tch.ste.domain.constant.AuthenticationType;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.domain.entity.IssuerTokenRequestors;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory
			.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginHandler loginHandler;

	@Autowired
	private TokenRequestorUrlService tokenRequestorUrlService;

	@Autowired
	@Qualifier("internalAuthenticationService")
	private AuthenticationService authenticationService;

	@Autowired
	private PaymentInstrumentFetchService paymentInstrumentFetchService;

	@Autowired
	@Qualifier("issuerConfigurationDao")
	private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

	@Autowired
	@Qualifier("issuerTokenRequestorDao")
	private JpaDao<IssuerTokenRequestors, Integer> issuerTokenRequestorDao;

	@Autowired
	@Qualifier("tokenRequestorConfigurationDao")
	private JpaDao<TokenRequestorConfiguration, Integer> tokenRequestorConfigurationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.core.login.LoginService#login(org.tch.ste.auth
	 * .dto.Login, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public LoginResponse login(Login login, String clientIpAddress,
			String clientUserAgent) {
		LoginResponse retVal = new LoginResponse();
		retVal.setSuccess(false);
		retVal.setLocked(false);
		retVal.setRedirectUrl(AuthViewConstants.V_BLANK_PAGE
				+ tokenRequestorUrlService.formUrl(login.getTrId(), false));
		// Fetch the issuer using the IISN and then based on the authentication
		// option call the specific auth service.
		Map<String, Object> params = new HashMap<String, Object>();
		String iisn = login.getIisn();
		params.put(AuthQueryConstants.PARAM_IISN, iisn);
		IssuerConfiguration issuerConfiguration = ListUtil
				.getFirstOrNull(issuerConfigurationDao.findByName(
						AuthQueryConstants.GET_ISSUER_BY_IISN, params));
		if (issuerConfiguration != null) {
			int authenticationType = issuerConfiguration
					.getAuthenticationType();
			if (AuthenticationType.TCH_HOSTED_WITH_GENERATED_CREDS.getType() == authenticationType
					|| AuthenticationType.TCH_HOSTED_WITH_ISSUER_CREDS
							.getType() == authenticationType) {

				boolean isAuthenticated = authenticationService
						.authenticate(login);
				if (isAuthenticated) {
					// Do the actual authentication.
					String userId = login.getUserId();
					try {
						List<AuthPaymentInstrument> paymentInstruments = paymentInstrumentFetchService
								.fetchPaymentInstrumentsForCustomer(userId,
										login.getTrId(), login.getCiid());
						if (paymentInstruments != null
								&& !paymentInstruments.isEmpty()) {
							retVal.setSuccess(true);
							retVal.setPaymentInstruments(paymentInstruments);
						} else {
							loginHandler.success(login, issuerConfiguration,
									clientIpAddress, clientUserAgent);
							throw new EmptyPaymentInstrumentException(
									"There are no cards available for selection.Please contact customer support.");
						}
					} catch (SteException e) {
						logger.warn("Error while fetching payment instruments",
								e);
						// FIXME - Return appropriate response.
						retVal.setSuccess(false);
						retVal.setErrorMessage(issuerConfiguration
								.getAuthErrorMessage());
					}
					retVal.setLocked(loginHandler.success(login,
							issuerConfiguration, clientIpAddress,
							clientUserAgent));
				} else {
					retVal.setSuccess(false);
					String errorMessage = issuerConfiguration
							.getAuthErrorMessage();
					if (loginHandler.failure(login, issuerConfiguration,
							clientIpAddress, clientUserAgent)) {
						retVal.setLocked(true);
						errorMessage = issuerConfiguration
								.getAuthLockoutMessage();
					}
					retVal.setErrorMessage(errorMessage);
				}
			} else {
				throw new IllegalStateException(
						"Authentication not yet implemented for type: "
								+ authenticationType);
			}
		} else {
			throw new IllegalArgumentException("Issuer with IISN: " + iisn
					+ " does not exist");
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.core.login.LoginService#getLoginModel(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public LoginModel getLoginModel(String trId, String iisn) {

		byte[] logoImage = null;
		LoginModel retVal = new LoginModel();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AuthQueryConstants.PARAM_IISN, iisn);
		retVal.setRedirectUrl(AuthViewConstants.V_BLANK_PAGE
				+ tokenRequestorUrlService.formUrl(trId, false));
		IssuerConfiguration issuerConfiguration = ListUtil
				.getFirstOrNull(issuerConfigurationDao.findByName(
						AuthQueryConstants.GET_ISSUER_BY_IISN, params));
		if (issuerConfiguration != null
				&& (logoImage = issuerConfiguration.getLogoImage()) != null) {
			retVal.setIssuerLogo(new String(Base64.encodeBase64(logoImage),
					Charset.forName(AuthConstants.CHARSET_UTF8)));
			retVal.setIssuerLogoName(issuerConfiguration.getFileName());
		} else {
			throw new IllegalStateException(
					"No logo image configured for iisn: " + iisn);
		}

		return retVal;
	}

	/**
	 * Validates Whether TRID and IISN Exists.
	 * 
	 * @param trid
	 *            - TRID of Wallet
	 * @param iisn
	 *            - Issuer IISN Number.
	 * @return true -If trid and issn exists ,else false.
	 */
	@Override
	public boolean validateTridAndIisn(String trid, String iisn) {

		Map<String, Object> params = new HashMap<>();
		params.put(AuthQueryConstants.PARAM_TRID, trid);
		params.put(AuthQueryConstants.PARAM_IISN, iisn);
		IssuerTokenRequestors iTokenRequestor = ListUtil
				.getFirstOrNull(issuerTokenRequestorDao.findByName(
						AuthQueryConstants.GET_ISSUER_TOKEN_REQUESTOR, params));
		boolean isValidTridAndIisn = (iTokenRequestor == null ? false : true);
		if (!isValidTridAndIisn) {
			boolean isValidTrid = validateTrid(trid);
			boolean isValidIisn = validateIisn(iisn);
			
			if (!isValidTrid && !isValidIisn) {
				logger.error(
						"Invalid TRID and IISN provided: TRID = {}, IISN = {}",
						trid, iisn);
			} else if (!isValidTrid) {
					logger.error("Invalid TRID provided: TRID = {}", trid);
			}
			else {
					logger.error("Invalid IISN provided: IISN = {}", iisn);
			}
		}

		return isValidTridAndIisn;

	}

	/**
	 * Validates the TRID.
	 * 
	 * @param trid
	 *            - TRID of Wallet
	 * @return true - If trId exists.
	 */
	public boolean validateTrid(String trid) {

		Map<String, Object> params = new HashMap<>();
		params.put(AuthQueryConstants.PARAM_TRID, trid);
		return ListUtil.getFirstOrNull(tokenRequestorConfigurationDao
				.findByName(AuthQueryConstants.GET_TOKEN_REQUESTOR_BY_TR_ID,
						params)) == null ? false : true;
	}

	/**
	 * Validates the IISN.
	 * 
	 * @param iisn
	 *            - IISN
	 * @return true - If iisn exists.
	 */
	public boolean validateIisn(String iisn) {

		Map<String, Object> params = new HashMap<>();
		params.put(AuthQueryConstants.PARAM_IISN, iisn);
		return ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
				AuthQueryConstants.GET_ISSUER_BY_IISN, params)) == null ? false
				: true;
	}

}
