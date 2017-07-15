/**
 * 
 */
package org.tch.ste.test.auth.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.test.auth.base.BaseAuthTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * Test for Fetching Login Page.
 * 
 * @author sharduls
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LoginPageTest extends BaseAuthTest {

    private static final StatusResultMatchers mockMvcResultMatchers = MockMvcResultMatchers.status();
	private static final ResultMatcher httpStatusMatcher = mockMvcResultMatchers
			.isOk();
	private static final ResultMatcher redirectStatusMatcher = mockMvcResultMatchers.is3xxRedirection();
	private String AUTH_LOGIN_PAGE = "auth";
	private String AUTH_BLANK_PAGE = "redirect:/views/blank.html?STATUS=Failure";
	private String AUTH_BLANK_PAGE_INVALID_TRID = "redirect:/views/blank.html";
	private String CIID = "ciid";
	private String invalidIisn = "123";
	private String invalidTrid = "123";

	private String ciid = "123456";

	/**
	 * Injects the entity manager.
	 */
	@BeforeTransaction
	public void setEntityManagerThreadLocal() {
		emInjector.inject(iisn);
	}

	/**
	 * Tests Fetching Login Page With Valid IISN and TRID.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	@Transactional
	public void testLoginPageWithValidIisnAndTrId() throws Exception {
		this.mockMvc
				.perform(get(LOGIN_URL).param(IISN, iisn).param(TR_ID, trId))
				.andExpect(httpStatusMatcher)
				.andExpect(viewResultMatcher.name(AUTH_LOGIN_PAGE)).andReturn();

	}

	/**
	 * Tests Fetching Login Page With Valid IISN , TRID And CIID.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	@Transactional
	public void testLoginPageWithValidIisnAndTrIdAndCiid() throws Exception {
		this.mockMvc
				.perform(
						get(LOGIN_URL).param(IISN, iisn).param(TR_ID, trId)
								.param(CIID, ciid))
				.andExpect(httpStatusMatcher)
				.andExpect(viewResultMatcher.name(AUTH_LOGIN_PAGE)).andReturn();

	}

	/**
	 * Tests Fetching Login Page With Invalid IISN.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	@Transactional
	public void testLoginPageWithInvalidIisn() throws Exception {
		this.mockMvc
				.perform(
						get(LOGIN_URL).param(IISN, invalidIisn).param(TR_ID,
								trId)).andExpect(redirectStatusMatcher)
				.andExpect(viewResultMatcher.name(AUTH_BLANK_PAGE)).andReturn();

	}

	/**
	 * Tests Fetching Login Page With Invalid TRID.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	@Transactional
	public void testLoginPageWithInvalidTrId() throws Exception {
		this.mockMvc
				.perform(
						get(LOGIN_URL).param(IISN, iisn).param(TR_ID,
								invalidTrid))
				.andExpect(redirectStatusMatcher)
				.andExpect(viewResultMatcher.name(AUTH_BLANK_PAGE_INVALID_TRID))
				.andReturn();

	}

}
