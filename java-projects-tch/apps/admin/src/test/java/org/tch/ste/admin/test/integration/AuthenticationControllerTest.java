/**
 *
 */
package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ERR_MSG;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_LOGIN_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * @author Karthik.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class AuthenticationControllerTest extends WebAppIntegrationTest
		implements ApplicationContextAware {

	/**
	 * Default Constructor.
	 */
	public AuthenticationControllerTest() {
	}

	private ApplicationContext applicationContext;

	@Test
	public void testLdap() {
		LdapAuthenticationProvider authenticationProvider = applicationContext
				.getBean("ldapAuthProvider", LdapAuthenticationProvider.class);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"tch-configuration", "password");

		authenticationProvider.authenticate(authentication);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;

	}

	/**
	 * Authentication Test - For Success.
	 * 
	 * @throws Exception
	 *             - Exception.
	 */
	@Test
	public void testAuthentication() throws Exception {
		String userId = "tch-user-mgmt";
		String password = "password";
		ResultMatcher homeName = viewResultMatcher.name(V_HOME_PAGE);

		LdapAuthenticationProvider authenticationProvider = applicationContext
				.getBean("ldapAuthProvider", LdapAuthenticationProvider.class);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userId, password);

		authenticationProvider.authenticate(authentication);

		this.mockMvc
				.perform(
						post(LOGIN_URL)
								.accept(MediaType.APPLICATION_FORM_URLENCODED)
								.param(LOGIN_ID, userId)
								.param(PASSWORD, password)).andExpect(okStatus)
				.andExpect(homeName).andReturn();

		userId = "issuer-mgmt";
		password = "password";

		this.mockMvc
				.perform(
						post(LOGIN_URL)
								.accept(MediaType.APPLICATION_FORM_URLENCODED)
								.param(LOGIN_ID, userId)
								.param(PASSWORD, password)).andExpect(okStatus)
				.andExpect(homeName).andReturn();
	}

	/**
	 * Test wrong user id and password.
	 * 
	 * @throws Exception
	 *             -Exception.
	 */
	@Test
	public void testAuthenticationFailure() throws Exception {

		// Wrong Password.
		String userId = "tch-user-mgmt";
		String password = "wrong-password";

		ResultMatcher loginName = viewResultMatcher.name(V_LOGIN_PAGE);

		MvcResult res = this.mockMvc
				.perform(
						post(LOGIN_URL)
								.accept(MediaType.APPLICATION_FORM_URLENCODED)
								.param(LOGIN_ID, userId)
								.param(PASSWORD, password)).andExpect(okStatus)
				.andExpect(loginName).andReturn();

		assertTrue(LOGIN_ERR_MSG.equals(res.getModelAndView().getModel()
				.get(AdminConstants.LOGIN_ERROR)));

		// Non-Existent user.
		userId = "tch-admin-nonexistent";
		password = "some-password";

		res = this.mockMvc
				.perform(
						post(LOGIN_URL)
								.accept(MediaType.APPLICATION_FORM_URLENCODED)
								.param(LOGIN_ID, userId)
								.param(PASSWORD, password)).andExpect(okStatus)
				.andExpect(loginName).andReturn();

		assertTrue(LOGIN_ERR_MSG.equals(res.getModelAndView().getModel()
				.get(AdminConstants.LOGIN_ERROR)));
	}

	/**
	 * Tests for Authorization Failure.
	 * 
	 * @throws Exception
	 *             -Exception.
	 */
	@Test
	public void testAuthorizationFailure() throws Exception {
		String userId = "unauthorized-issuer";
		String password = "password";

		MvcResult res = this.mockMvc
				.perform(
						post(LOGIN_URL)
								.accept(MediaType.APPLICATION_FORM_URLENCODED)
								.param(LOGIN_ID, userId)
								.param(PASSWORD, password)).andExpect(okStatus)
				.andExpect(viewResultMatcher.name(V_LOGIN_PAGE)).andReturn();

		assertTrue(LOGIN_ERR_MSG.equals(res.getModelAndView().getModel()
				.get(AdminConstants.LOGIN_ERROR)));
	}

}
