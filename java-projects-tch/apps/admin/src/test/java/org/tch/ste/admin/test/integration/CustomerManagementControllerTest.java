package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.CUSTOMER_MANAGEMENT_LIST_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.CUSTOMER_MANAGEMENT_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOCK_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.RESET_PASSWORD_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.UNLOCK_URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.domain.dto.CustomerLockResponse;
import org.tch.ste.admin.domain.dto.CustomerUnLockResponse;
import org.tch.ste.admin.domain.dto.ResetPasswordResponse;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * To test the customer management controller
 * 
 * @author kjanani
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class CustomerManagementControllerTest extends WebAppIntegrationTest {

    private static final String iisn = "123456";
    private static final String id = "1";
    private static int id1 = 1;
    private static final String userid = "John Taylor";
    private static String userId = "issuer-mgmt";
    private static String password = "password";
    private static String lockErrMsg = "Customer is already locked.";
    private static String unlockErrMsg = "Customer is already unlocked.";

    private static final String customerManagementJson = "[{\"id\":1,\"userId\":\"John Taylor\",\"issuerName\":\"hdfc\",\"authenticated\":\"Y\",\"iisn\":null,\"locked\":true},"
            + "{\"id\":2,\"userId\":\"Peter Brokes\",\"issuerName\":\"hdfc\",\"authenticated\":\"Y\",\"iisn\":null,\"locked\":true},"
            + "{\"id\":3,\"userId\":\"Thomas Paul\",\"issuerName\":\"hdfc\",\"authenticated\":\"Y\",\"iisn\":null,\"locked\":true}]";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<ResetPasswordResponse> resetPasswordResponseConverter;

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<CustomerLockResponse> customerLockResponseConverter;

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<CustomerUnLockResponse> customerUnLockResponseConverter;

    /**
     * 
     */
    public CustomerManagementControllerTest() {

    }

    @Autowired
    @Qualifier("defaultInjectorImpl")
    private EntityManagerInjector emInjector;

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject(iisn);
    }

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /**
     * 
     * This method is used to test the reset password functionality
     * 
     * @throws Exception
     *             - The exception
     */
    @Test
    @Transactional
    public void testResetPassword() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(
                        post(RESET_PASSWORD_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andReturn();

        ResetPasswordResponse resetPasswordResponse = resetPasswordResponseConverter.objectify(res.getResponse()
                .getContentAsString(), ResetPasswordResponse.class);

        assertEquals(userid, resetPasswordResponse.getUserPassword().getUserId());
        // TODO - Fix this later.assertEquals(resetPassword,
        // resetPasswordResponse.getUserPassword().getPassword());

        Customer customer = customerDao.get(id1);

        Date date1 = DateUtil.getUtcTime();
        Date date2 = customer.getLastPasswordChange();

        long diff = (date1.getTime() - date2.getTime()) / 1000;
        assertTrue(diff <= 60);

    }

    /**
     * This method is used to test the success status of the customer for
     * lock/unlock
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    @Transactional
    public void testSuccessStatus() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc.perform(post(LOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andExpect(jsonPath("$.success", Boolean.FALSE).exists()).andReturn();
        this.mockMvc.perform(post(UNLOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();
        this.mockMvc.perform(post(LOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

    }

    /**
     * This method is used to test the if the user is locking customer whose
     * account is already locked. Error message is thrown upon locking the
     * locked customer.
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    @Transactional
    public void testLockCustomerFailure() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc.perform(post(LOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andReturn();

        MvcResult res = this.mockMvc
                .perform(post(LOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andReturn();

        CustomerLockResponse customerLockResponse = customerLockResponseConverter.objectify(res.getResponse()
                .getContentAsString(), CustomerLockResponse.class);
        assertEquals(lockErrMsg, customerLockResponse.getErrorMsg());
    }

    /**
     * This method is used to test the if the user is unlocking the customer
     * whose account is already unlocked.. Error message is thrown upon
     * unlocking the unlocked customer
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    @Transactional
    public void testUnlockCustomerFailure() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc.perform(post(UNLOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andReturn();
        MvcResult res = this.mockMvc
                .perform(post(UNLOCK_URL).session((MockHttpSession) session).param("iisn", iisn).param("id", id))
                .andExpect(okStatus).andReturn();

        CustomerUnLockResponse customerUnLockResponse = customerUnLockResponseConverter.objectify(res.getResponse()
                .getContentAsString(), CustomerUnLockResponse.class);
        assertEquals(unlockErrMsg, customerUnLockResponse.getErrorMsg());
    }

    /**
     * Test whether we fetch the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    public void testgetCustomers() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(
                        get(CUSTOMER_MANAGEMENT_URL).session((MockHttpSession) session).param("iisn", iisn)
                                .param("id", id)).andExpect(okStatus).andReturn();

        assertEquals(customerManagementJson, res.getResponse().getContentAsString());

    }

    /**
     * Test whether we fetch the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testListIssuer() throws Exception {
        List<IssuerConfiguration> issuerList = new ArrayList<>();
        IssuerConfiguration issuer = new IssuerConfiguration();
        IssuerConfiguration issuer1 = new IssuerConfiguration();
        issuer.setIssuerName("hdfc");
        issuer1.setIssuerName("icici");
        issuerList.add(issuer);
        issuerList.add(issuer1);
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc.perform(get(CUSTOMER_MANAGEMENT_LIST_URL).session((MockHttpSession) session))
                .andExpect(okStatus).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateIssuerList(issuerList, (List<IssuerConfiguration>) model.get(C_MODEL_ATTR_ISSUER));
    }

    /**
     * @param issuerList
     * @param issuer
     */
    private static void validateIssuerList(List<IssuerConfiguration> issuerList,
            List<IssuerConfiguration> issuerConfigList) {

        int issuerListSize = issuerList.size();
        assertEquals(issuerListSize, issuerConfigList.size());
        for (int i = 0; i < issuerListSize; ++i) {
            validateIssuerConfiguration(issuerList.get(i), issuerConfigList.get(i));
        }
    }

    /**
     * @param issuerConfiguration
     * @param issuerConfiguration2
     */
    private static void validateIssuerConfiguration(IssuerConfiguration issuerConfiguration,
            IssuerConfiguration issuerConfiguration2) {
        assertEquals(issuerConfiguration.getIssuerName(), issuerConfiguration2.getIssuerName());

    }

}
