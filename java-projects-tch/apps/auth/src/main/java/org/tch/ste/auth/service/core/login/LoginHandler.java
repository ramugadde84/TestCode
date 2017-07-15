/**
 * 
 */
package org.tch.ste.auth.service.core.login;

import org.tch.ste.auth.dto.Login;
import org.tch.ste.domain.entity.IssuerConfiguration;

/**
 * Login handler interface - Will expose methods to handle login success and
 * failure scenarios.
 * 
 * @author Karthik.
 * 
 */
public interface LoginHandler {

    /**
     * Success Handling Function.
     * 
     * @param login
     *            Login - The login DTO.
     * @param issuerConfiguration
     *            IssuerConfiguration - The configured issuer.
     * @param clientIpAddress String - The client IP address.
     * @param clientUserAgent String - The client user agent.
     * @return boolean - True if the account is locked as a result of the
     *         authentication.
     */
    boolean success(Login login, IssuerConfiguration issuerConfiguration,String clientIpAddress,String clientUserAgent);

    /**
     * Failure Handling Function.
     * 
     * @param login
     *            Login- The login DTO.
     * @param issuerConfiguration
     *            IssuerConfiguration - The configured issuer.
     * @param clientIpAddress String - The client IP address.
     * @param clientUserAgent String - The client user agent.
     * @return boolean - True if the user is locked out, false otherwise.
     */
    boolean failure(Login login, IssuerConfiguration issuerConfiguration,String clientIpAddress,String clientUserAgent);
}