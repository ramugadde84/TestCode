/**
 * 
 */
package org.tch.ste.test.auth.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.auth.dto.Login;
import org.tch.ste.auth.dto.LoginResponse;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.test.auth.base.BaseAuthTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * Test case for Login.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LoginTest extends BaseAuthTest {

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<LoginResponse> loginResponseConverter;

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject(iisn);
    }

    /**
     * Tests the login functionality.
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    @Transactional
    public void testLoginSuccess() throws Exception {
    	
        for (int i = 0; i < userNames.size(); ++i) {
            String userName = userNames.get(i);
            String password = passwords.get(i);
            MvcResult res = this.mockMvc
                    .perform(
                            post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                    .param(PASSWORD, password).param(IISN, iisn).param(TR_ID, trId))
                    .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                    .andReturn();
            validateResponse(res, userName, iisn, null, null, EMPTY_PI_ERROR_MSG);

        }
    }

    /**
     * Validates the login response.
     * 
     * @param res
     *            MvcResult - The result.
     * @param userName
     *            String - The user name.
     * @param myIisn
     *            String - The IISN.
     * @param numPi
     *            Integer - The number of payment instruments.
     * @param expectedFieldErrorMessages
     *            String[] - Expected Error Messages.
     * @param expectedErrorMessage
     *            String - The expected error message.
     * @throws UnsupportedEncodingException
     *             Thrown.
     */
    private void validateResponse(MvcResult res, String userName, String myIisn, Integer numPi,
            String[] expectedFieldErrorMessages, String expectedErrorMessage) throws UnsupportedEncodingException {
        MockHttpServletResponse resp = res.getResponse();
        assertTrue(resp.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        LoginResponse response = loginResponseConverter.objectify(resp.getContentAsString(), LoginResponse.class);
        Login login = response.getLogin();
        assertEquals(userName, login.getUserId());
        assertEquals(myIisn, login.getIisn());
        if (numPi != null) {
            assertEquals(Integer.valueOf(response.getPaymentInstruments().size()), numPi);
        } else {
            assertTrue(response.getPaymentInstruments() == null);
        }
        assertEquals(expectedErrorMessage, response.getErrorMessage());
        if (expectedFieldErrorMessages != null) {
            Map<String, String> errorMessages = response.getFieldErrorMessages();
            assertTrue(errorMessages != null);
            if (errorMessages != null) {
                for (int i = 0; i < expectedFieldErrorMessages.length; i += 2) {
                    assertEquals(errorMessages.get(expectedFieldErrorMessages[i]), expectedFieldErrorMessages[i + 1]);
                }
            }
        }

    }

    /**
     * Tests for a non-existent user name.
     * 
     * @throws Exception
     *             Thrown - Thrown.
     */
    @Test
    @Transactional
    public void testLoginNonExistentUserName() throws Exception {
        String nonExistentUserName = "userName";
        String nonExistentPassword = "password";
        MvcResult res = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, nonExistentUserName)
                                .param(PASSWORD, nonExistentPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andReturn();
        validateResponse(res, nonExistentUserName, iisn, null, null, "HDFC Error.");
    }

    /**
     * Tests for a bad password.
     * 
     * @throws Exception
     *             Thrown - Thrown.
     */
    @Test
    @Transactional
    public void testBadPassword() throws Exception {
        String nonExistentUserName = userNames.get(0);
        String nonExistentPassword = "password";
        MvcResult res = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, nonExistentUserName)
                                .param(PASSWORD, nonExistentPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andReturn();
        validateResponse(res, nonExistentUserName, iisn, null, null, "HDFC Error.");
    }

    /**
     * Tests for a bad iisn.
     * 
     * @throws Exception
     *             Thrown - Thrown.
     */
    @Test
    @Transactional
    public void testBadIisn() throws Exception {
        String nonExistentUserName = userNames.get(0);
        String nonExistentPassword = passwords.get(0);
        String badIisn = "111111";
        MvcResult res = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, nonExistentUserName)
                                .param(PASSWORD, nonExistentPassword).param(IISN, badIisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andReturn();
        validateResponse(res, nonExistentUserName, badIisn, null, null, null);
    }

    /**
     * Tests blank values.
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    @Transactional
    public void testBlankValues() throws Exception {
        String userName = userNames.get(0);
        String password = passwords.get(0);
        MvcResult res = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName).param(IISN, iisn)
                                .param(TR_ID, trId)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists()).andReturn();
        validateResponse(res, userName, iisn, null, new String[] { "password", "Field password is required." }, null);
        res = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(PASSWORD, password).param(IISN, iisn)
                                .param(TR_ID, trId)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists()).andReturn();
        validateResponse(res, null, iisn, null, new String[] { "userId", "Field userId is required." }, null);

    }

}
