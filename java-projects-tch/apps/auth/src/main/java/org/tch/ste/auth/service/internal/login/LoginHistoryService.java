/**
 * 
 */
package org.tch.ste.auth.service.internal.login;

import java.util.Date;

import org.tch.ste.domain.entity.Customer;

/**
 * Provides methods to manipulate login history.
 * 
 * @author Karthik.
 * 
 */
public interface LoginHistoryService {
    /**
     * Adds a successful login to login history.
     * 
     * @param customer
     *            Customer - The customer.
     * @param userId
     *            String - The user id.
     * @param loginTime
     *            Date - The login time.
     * @param isAccountLocked
     *            boolean - Is the account locked due to login?
     * @param clientIpAddress
     *            String - The client IP address.
     * @param clientUserAgent
     *            String - The client user agent.
     */
    void addSuccessfulLogin(Customer customer, String userId, Date loginTime, boolean isAccountLocked,
            String clientIpAddress, String clientUserAgent);

    /**
     * Adds a failed login to login history.
     * 
     * @param customer
     *            Customer - The customer.
     * @param userId
     *            String - The user id.
     * @param loginTime
     *            Date - The login time.
     * @param isAccountLocked
     *            boolean - Is the account locked due to login?
     * @param clientIpAddress
     *            String - The client IP address.
     * @param clientUserAgent
     *            String - The client user agent.
     */
    void addFailedLogin(Customer customer, String userId, Date loginTime, boolean isAccountLocked,
            String clientIpAddress, String clientUserAgent);
}
