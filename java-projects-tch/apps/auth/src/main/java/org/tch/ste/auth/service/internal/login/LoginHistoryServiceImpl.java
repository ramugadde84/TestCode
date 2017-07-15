/**
 * 
 */
package org.tch.ste.auth.service.internal.login;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.LoginHistory;
import org.tch.ste.infra.repository.JpaDao;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    @Autowired
    @Qualifier("loginHistoryDao")
    private JpaDao<LoginHistory, Integer> loginHistoryDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.login.LoginHistoryService#
     * addSuccessfulLogin(org.tch.ste.domain.entity.Customer, java.lang.String,
     * java.util.Date, boolean, java.lang.String, java.lang.String)
     */
    @Override
    public void addSuccessfulLogin(Customer customer, String userId, Date loginTime, boolean isAccountLocked,
            String clientIpAddress, String clientUserAgent) {
        addLoginHistory(customer, userId, loginTime, true, isAccountLocked, clientIpAddress, clientUserAgent);
    }

    /**
     * Adds the login to the login history table.
     * 
     * @param customer
     *            Customer - The customer.
     * @param userId
     *            String - The user id.
     * @param loginTime
     *            Date - The login time.
     * @param isSuccess
     *            boolean - Is it successful?
     * @param isAccountLocked
     *            boolean - Is the account locked due to this?
     * @param clientIpAddress
     *            String - The client IP address.
     * @param clientUserAgent
     *            String - The client user agent.
     */
    private void addLoginHistory(Customer customer, String userId, Date loginTime, boolean isSuccess,
            boolean isAccountLocked, String clientIpAddress, String clientUserAgent) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setCustomer(customer);
        loginHistory.setUserName(userId);
        loginHistory.setLoginSuccessful(isSuccess);
        loginHistory.setClientIpAddress(clientIpAddress);
        loginHistory.setClientUserAgent((clientUserAgent != null) ? clientUserAgent.substring(0,
                AuthConstants.CLIENT_USER_AGENT_LEN) : null);
        if (isSuccess) {
            loginHistory.setAccountLockedFromSucessfulLogin(isAccountLocked);
            loginHistory.setAccountLockedFromFailedLogins(false);
        } else {
            loginHistory.setAccountLockedFromSucessfulLogin(false);
            loginHistory.setAccountLockedFromFailedLogins(isAccountLocked);
        }
        loginHistory.setLoginAttemptTime(loginTime);
        loginHistoryDao.save(loginHistory);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.login.LoginHistoryService#addFailedLogin
     * (org.tch.ste.domain.entity.Customer, java.lang.String, java.util.Date,
     * boolean, java.lang.String, java.lang.String)
     */
    @Override
    public void addFailedLogin(Customer customer, String userId, Date loginTime, boolean isAccountLocked,
            String clientIpAddress, String clientUserAgent) {
        addLoginHistory(customer, userId, loginTime, false, isAccountLocked, clientIpAddress, clientUserAgent);
    }

}
