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
 * Test class for login in lock outs
 * 
 * @author kjanani
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LoginLockOutTestForIssuer1 extends BaseAuthTest {

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject(getIisn());
    }

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<LoginResponse> loginResponseConverter;

    protected int loginAttemptsForLockOut = 2;
    
    protected String errorMessage="HDFC Error.";
    
    protected String lockoutMessage="HDFC Lockout.";

    /**
     * This method is used to test the lock out functionality for 3 failure
     * login attempts
     * 
     * @throws Exception
     *             -Exception
     */
    @Test
    @Transactional
    public void testLockout() throws Exception {
        String nonExistentUserName = userNames.get(0);
        String nonExistentPassword = "password";

        for (int i = 0; i < getLoginAttemptsForLockOut(); ++i) {

            MvcResult res = login(nonExistentUserName, nonExistentPassword);
            validateResponse(res, nonExistentUserName, getIisn(), null, null, getErrorMessage());

        }

        MvcResult thirdAttempt = login(nonExistentUserName, nonExistentPassword);
        validateResponse(thirdAttempt, nonExistentUserName, getIisn(), null, null, getLockoutMessage());

    }

    /**
     * @return the loginAttemptsForLockOut
     */
    public int getLoginAttemptsForLockOut() {
        return loginAttemptsForLockOut;
    }

    /**
     * @param loginAttemptsForLockOut the loginAttemptsForLockOut to set
     */
    public void setLoginAttemptsForLockOut(int loginAttemptsForLockOut) {
        this.loginAttemptsForLockOut = loginAttemptsForLockOut;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the lockoutMessage
     */
    public String getLockoutMessage() {
        return lockoutMessage;
    }

    /**
     * @param lockoutMessage the lockoutMessage to set
     */
    public void setLockoutMessage(String lockoutMessage) {
        this.lockoutMessage = lockoutMessage;
    }

    /**
     * 
     * Method for login using mock mvc
     * 
     * @throws Exception
     *             -Exception
     */

    protected MvcResult login(String nonExistentUserName, String nonExistentPassword) throws Exception {

        return this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, nonExistentUserName)
                                .param(PASSWORD, nonExistentPassword).param(IISN, getIisn()).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andReturn();

    }

    /**
     * 
     * Method to validate the response and error messages
     * 
     * @throws Exception
     *             -Exception
     */
    protected void validateResponse(MvcResult res, String userName, String myIisn, Integer numPi,
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

}
