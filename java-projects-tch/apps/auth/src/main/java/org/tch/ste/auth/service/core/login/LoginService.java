/**
 * 
 */
package org.tch.ste.auth.service.core.login;

import org.tch.ste.auth.dto.Login;
import org.tch.ste.auth.dto.LoginModel;
import org.tch.ste.auth.dto.LoginResponse;

/**
 * Exposes methods to do login.
 * 
 * @author Karthik.
 * 
 */
public interface LoginService {

	/**
	 * Fetches the login model for the given issuer.
	 * 
	 * @param trId
	 *            String - The trId.
	 * @param iisn
	 *            String - The iisn.
	 * @return LoginModel - The login model.
	 */
	LoginModel getLoginModel(String trId, String iisn);

	/**
	 * Performs the login.
	 * 
	 * @param login
	 *            Login - The login DTO.
	 * @param clientIpAddress
	 *            String - The client IP address.
	 * @param clientUserAgent
	 *            String - The client user agent.
	 * @return LoginResponse - List of payment instruments associated with the
	 *         customer and status code.
	 */
	LoginResponse login(Login login, String clientIpAddress,
			String clientUserAgent);

	/**
	 * Validates Whether TRID and IISN Exists.
	 * 
	 * @param trid
	 *            - TRID of Wallet
	 * @param iisn
	 *            - Issuer IISN Number.
	 * @return true -If trid and issn exists ,else false.
	 */
	public boolean validateTridAndIisn(String trid, String iisn);
}
