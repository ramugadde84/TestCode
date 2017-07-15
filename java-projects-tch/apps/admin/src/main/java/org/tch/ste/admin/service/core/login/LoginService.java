/**
 * 
 */
package org.tch.ste.admin.service.core.login;

import org.tch.ste.admin.domain.dto.Login;

import java.util.Date;

/**
 * Provides methods related to login.
 * 
 * @author Karthik.
 * 
 */
public interface LoginService {
    /**
     * Logs in the user.
     * 
     * @param login
     * @return boolean - True if successful.
     */
    boolean login(Login login);

    /**
     * Sets the login time to be the associated time.
     * 
     * @param userId
     *            String - The user id.
     * @param loginTime
     *            Date - The login time.
     */
    void setCurrentLoginTime(String userId, Date loginTime);

    /**
     * Fetches the last login time of the user.
     * 
     * @param userId
     *            String - The user id.
     * @return Date - The last login time.
     */
    Date getLastLoginTime(String userId);
}
