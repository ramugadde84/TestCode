/**
 * 
 */
package org.tch.ste.test.auth.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.tch.ste.auth.dto.LoginResponse;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.test.auth.base.BaseAuthTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LockOutSuccessfulAfterLoginTest extends BaseAuthTest {

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

  /**
   * To override iisn values.
   * 
   */
    @Override
    public String getIisn() {
        return "123656";
    }

    /**
     * Method to validate the response and error messages
     * 
     * @throws Exception
     *             -
     */
    @Test
    @Transactional
    public void lockOutSuccessfullyAfterLoginTest() throws Exception {
        String userName = userNames.get(2);
        String password = passwords.get(2);

        MvcResult res = login(userName, password);
        LoginResponse response = validateResponse(res);
        assertTrue(response.isLocked());

        // Re-login to test error message.
        MvcResult resRelogin = login(userName, password);
        LoginResponse responseRelogin = validateResponse(resRelogin);
        assertEquals(responseRelogin.getErrorMessage(), "RBS Lockout.");
    }

    /**
     * 
     * Method to get the response.
     * 
     * @throws Exception
     *             -Exception
     */
    protected LoginResponse validateResponse(MvcResult res) throws Exception {
        MockHttpServletResponse resp = res.getResponse();
        assertTrue(resp.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        return loginResponseConverter.objectify(resp.getContentAsString(), LoginResponse.class);
    }

    /**
     * 
     * Method for login using mock mvc
     * 
     * @throws Exception
     *             -Exception
     */

    protected MvcResult login(String userName, String password) throws Exception {

        return this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                .param(PASSWORD, password).param(IISN, getIisn()).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andReturn();

    }
}
