/**
 * 
 */
package org.tch.ste.auth.service.core.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.auth.dto.Login;
import org.tch.ste.auth.service.internal.login.LoginHistoryService;
import org.tch.ste.domain.constant.LockCode;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class LoginHandlerImpl implements LoginHandler {

	private static Logger logger = LoggerFactory
			.getLogger(LoginHandlerImpl.class);

	@Autowired
	@Qualifier("customerDao")
	private JpaDao<Customer, Integer> customerDao;

	@Autowired
	private LoginHistoryService loginHistoryService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.core.login.LoginHandler#success(org.tch.ste.
	 * auth.dto.Login, org.tch.ste.domain.entity.IssuerConfiguration,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public boolean success(Login login,
			IssuerConfiguration issuerConfiguration, String clientIpAddress,
			String clientUserAgent) {
		boolean retVal = false;
		String userId = login.getUserId();
		String iisn = login.getIisn();
		String trId = login.getTrId();
		String ciid = login.getCiid();
		if (logger.isInfoEnabled()) {
			logger.info(
					"Successful login for user = {}, IISN = {}, TRID = {}, CIID = {}",
					new Object[] { userId, iisn, trId, ciid });
		}

		Customer customer = getCustomerByUserName(userId);
		Date currentTime = DateUtil.getUtcTime();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime);
		customer.setLastSuccessfulLogin(currentTime);
		customer.setFailedLoginCount(0);
		Boolean disableCredAfterLogin = issuerConfiguration
				.getDisableCredentialAfterLogin();
		if (disableCredAfterLogin != null
				&& disableCredAfterLogin.equals(Boolean.TRUE)) {
			customer.setAccountLocked(Boolean.TRUE);
			customer.setAccountLockedAt(currentTime);
			customer.setAccountLockedReasonCode(LockCode.SUCCESSFUL_AUTH
					.getCode());
			if (logger.isInfoEnabled()) {
				logger.info(
						"Account locked after successful login for user = {}, IISN = {}, TRID = {}, CIID = {}.",
						new Object[] { userId, iisn, trId, ciid });
			}
			retVal = true;
		} else {
			customer.setAccountLocked(Boolean.FALSE);
		}
		customerDao.save(customer);
		logger.info(
				"Authentication attempt for user {} with issuer {}; Timestamp: {}, IP Address: {}, User Agent: {}, Result: Success",
				new Object[] { userId, iisn, timeStamp, clientIpAddress,
						clientUserAgent });
		loginHistoryService.addSuccessfulLogin(customer, userId, currentTime,
				customer.getAccountLocked(), clientIpAddress, clientUserAgent);
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.core.login.LoginHandler#failure(org.tch.ste.
	 * auth.dto.Login, org.tch.ste.domain.entity.IssuerConfiguration,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public boolean failure(Login login,
			IssuerConfiguration issuerConfiguration, String clientIpAddress,
			String clientUserAgent) {
		boolean isAccountLocked = false;
		String userId = login.getUserId();
		String iisn = login.getIisn();
		String trId = login.getTrId();
		if (logger.isInfoEnabled()) {
			logger.info(
					"Failed login attempt for user = {}, IISN = {}, TRID = {}, CIID = {}",
					new Object[] { userId, iisn, trId, login.getCiid() });
		}
		Customer customer = getCustomerByUserName(userId);
		Date currentTime = DateUtil.getUtcTime();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(currentTime);
		if (customer != null) {
			Boolean accountLocked = customer.getAccountLocked();
			if (accountLocked != null) {
				isAccountLocked = accountLocked;
			}
			customer.setLastFailedLogin(currentTime);
			Integer failedLoginCount = customer.getFailedLoginCount();
			int lockedOutCount = ((failedLoginCount != null) ? failedLoginCount
					: 0) + 1;
			customer.setFailedLoginCount(lockedOutCount);
			if (issuerConfiguration != null
					&& lockedOutCount >= issuerConfiguration
							.getFailedLoginsToLockout()) {
				accountLocked = customer.getAccountLocked();
				boolean locked = (accountLocked != null) ? accountLocked
						: false;
				if (!locked) {
					customer.setAccountLocked(Boolean.TRUE);
					customer.setAccountLockedReasonCode(LockCode.FAILED_AUTH_ATTEMPT
							.getCode());
					customer.setAccountLockedAt(currentTime);
				}
				isAccountLocked = true;
				if (logger.isInfoEnabled()) {
					logger.info(
							"Account locked for user = {}, IISN = {}, TRID = {}, CIID = {}.",
							new Object[] { userId, iisn, trId });
				}
			}
			customerDao.save(customer);
		}
		logger.info(
				"Authentication attempt for user {} with issuer {}; Timestamp: {}, IP Address: {}, User Agent: {}, Result: Failure",
				new Object[] { userId, iisn, timeStamp, clientIpAddress,
						clientUserAgent });
		loginHistoryService.addFailedLogin(customer, userId, currentTime,
				isAccountLocked, clientIpAddress, clientUserAgent);

		return isAccountLocked;
	}

	/**
	 * Return customer by user name.
	 * 
	 * @param userName
	 *            String - The user name.
	 * @return Customer - The customer.
	 */
	private Customer getCustomerByUserName(String userName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AuthQueryConstants.PARAM_USER_NAME, userName);
		return ListUtil.getFirstOrNull(customerDao.findByName(
				AuthQueryConstants.GET_CUSTOMER_BY_USER_NAME, params));
	}
}
