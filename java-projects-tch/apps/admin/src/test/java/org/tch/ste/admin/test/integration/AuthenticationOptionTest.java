package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ERRORS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER_DETAILS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_ID;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_ISSUER_PAGE;
import static org.tch.ste.admin.constant.AuthenticationOptionError.INVALID_ACCOUNT_LIST_ENDPOINT;
import static org.tch.ste.admin.constant.AuthenticationOptionError.INVALID_AUTH_ENDPOINT;
import static org.tch.ste.admin.constant.AuthenticationOptionError.INVALID_FAILED_ATTEMPTS_TO_LOCKOUT;
import static org.tch.ste.admin.constant.AuthenticationOptionError.INVALID_ISSUER_LOGO_SIZE;
import static org.tch.ste.admin.constant.AuthenticationOptionError.INVALID_ISSUER_LOGO_TYPE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.ISSUER_LIST_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ViewResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * @author sujathas
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class AuthenticationOptionTest extends WebAppIntegrationTest {

    private static String expectedJson = "[{\"id\":1,\"name\":\"hdfc\",\"iid\":null,\"iisn\":\"123456\",\"tokenRequestor\":\"google-wallet,papal-wallet,spring-wallet,yahoo-wallet\"},"
            + "{\"id\":2,\"name\":\"icici\",\"iid\":null,\"iisn\":\"123556\",\"tokenRequestor\":\"jpa-wallet,papal-wallet,zippy-wallet\"}]";

    private static final String name = "abcd";
    private static final String iid = "999967.987654";
    private static final String iisn = "123656";
    private static final String dropZonePath = "abc";
    private static final Boolean disableCredentialsAfterLogin = Boolean.TRUE;
    private static final String authErrorMessage = "TEST";
    private static final String authLockoutMessage = "TEST_LOCK";
    private static final String failedAttemptsToTriggerLockout = "1";
    private static final String authEndpoint = "https://localhost:8090/switch";
    private static final String accountListEndpoint = "https://localhost:8090/switch";
    private static final String newauthEndpoint = "http://localhost:8090/switch";
    private static final String newaccountListEndpoint = "http://localhost:8090/switch";
    private static final String newfailedAttemptsToTriggerLockout = "-1";
    private static final String newauthError = "New Test";
    private static final String empty = "";
    protected ViewResultMatchers mockView = MockMvcResultMatchers.view();
    protected ResultMatcher issuerView = mockView.name(V_ISSUER_PAGE);
    protected ResultMatcher homeView = mockView.name(V_HOME_PAGE);
    protected ResultMatcher issuerDetView = mockView.name("issuerDetails");
    protected String failedAttemptslockout = INVALID_FAILED_ATTEMPTS_TO_LOCKOUT.name();

    protected String issuerLogoType = INVALID_ISSUER_LOGO_TYPE.name();

    @Autowired
    @Qualifier("defaultInjectorImpl")
    private EntityManagerInjector emInjector;

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject("123456");
    }

    /**
     * @throws Exception
     *             Authentication Check
     */
    @Test
    @Transactional
    public void testAuthentication() throws Exception {

        String userId = "tch-configuration";
        String password = "password";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();

        MvcResult res = this.mockMvc.perform(get(ISSUER_LIST_URL).session((MockHttpSession) session))
                .andExpect(okStatus).andExpect(issuerView).andReturn();

        Map<String, Object> model = res.getModelAndView().getModel();
        validateIssuerList((String) model.get(C_MODEL_ATTR_ISSUER));

    }

    private static void validateIssuerList(String issuers) {
        assertEquals(expectedJson, issuers);
    }

    /**
     * @throws Exception
     *             Saving Authentication option details for the option
     *             TTCH-Hosted w/Issuer-Provided Credentials
     */
    @Test
    @Transactional
    public void testSaveProvidedAuth() throws Exception {
        String fileName = "test.jpeg";
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test.jpeg")));

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);
    }

    /**
     * @throws Exception
     *             Saving Authentication option details for the option
     *             TCH-Hosted w/Generated Credentials
     */
    @Test
    @Transactional
    public void testSaveGeneratedAuth() throws Exception {
        String fileName = "test.jpeg";
        Integer authMechanism = 4;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test.jpeg")));
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Saving Authentication option details for the option
     *             TCH-Hosted w/Issuer Auth Web Service
     * 
     */
    @Ignore
    @Transactional
    public void testSaveWebServiceAuth() throws Exception {
        String fileName = "test.jpeg";
        Integer authMechanism = 5;
        String user = "tch-configuration";
        String passwordValue = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test.jpeg")));
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, user)
                                .param(PASSWORD, passwordValue)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();

        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, "0", authEndpoint, accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, "0", authEndpoint, accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Validating the failure scenario for TCH-Hosted w/Generated
     *             Credentials
     */

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveFailureGeneratedAuth() throws Exception {
        Integer authMechanism = 4;
        String userId = "tch-configuration";
        String password = "password";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.jpeg", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.jpeg")));

            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> model = addResult.getModelAndView().getModel();
            Map<String, Object> error = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(error.containsKey("authErrorMessage"));
            assertEquals(error.get("authErrorMessage"), "NotEmpty");
            assertTrue(error.containsKey("authLockoutMessage"));
            assertEquals(error.get("authLockoutMessage"), "NotEmpty");
            assertTrue(error.containsKey("failedAttemptsToTriggerLockout"));
            assertEquals(error.get("failedAttemptsToTriggerLockout"), failedAttemptslockout);
            assertTrue(error.containsKey("issuerLogo"));
            assertEquals(error.get("issuerLogo"), INVALID_ISSUER_LOGO_SIZE.name());
        }
        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.png", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.png")));

            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> model = addResult.getModelAndView().getModel();
            Map<String, Object> errors = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(errors.containsKey("authErrorMessage"));
            assertEquals(errors.get("authErrorMessage"), "NotEmpty");
            assertTrue(errors.containsKey("authLockoutMessage"));
            assertEquals(errors.get("authLockoutMessage"), "NotEmpty");
            assertTrue(errors.containsKey("failedAttemptsToTriggerLockout"));
            assertEquals(errors.get("failedAttemptsToTriggerLockout"), failedAttemptslockout);
            assertTrue(errors.containsKey("issuerLogo"));
            assertEquals(errors.get("issuerLogo"), issuerLogoType);

        }
    }

    /**
     * @throws Exception
     *             Validating the failure scenario for TCH-Hosted
     *             w/Issuer-Provided Credentials
     */

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveFailureProvidedAuth() throws Exception {
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        String invalidLogoSize = INVALID_ISSUER_LOGO_SIZE.name();

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.jpeg", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.jpeg")));

            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> model = addResult.getModelAndView().getModel();
            Map<String, Object> errors = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(errors.containsKey("authErrorMessage"));
            assertEquals(errors.get("authErrorMessage"), "NotEmpty");
            assertTrue(errors.containsKey("authLockoutMessage"));
            assertEquals(errors.get("authLockoutMessage"), "NotEmpty");
            assertTrue(errors.containsKey("failedAttemptsToTriggerLockout"));
            assertEquals(errors.get("failedAttemptsToTriggerLockout"), INVALID_FAILED_ATTEMPTS_TO_LOCKOUT.name());
            assertTrue(errors.containsKey("issuerLogo"));
            assertEquals(errors.get("issuerLogo"), invalidLogoSize);
        }

        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.png", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.png")));

            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> issuerTypeModel = addResult.getModelAndView().getModel();
            Map<String, Object> issuerTypeErrors = (Map<String, Object>) issuerTypeModel.get(C_MODEL_ATTR_ERRORS);
            assertTrue(issuerTypeErrors.containsKey("authErrorMessage"));
            assertEquals(issuerTypeErrors.get("authErrorMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("failedAttemptsToTriggerLockout"));
            assertEquals(issuerTypeErrors.get("failedAttemptsToTriggerLockout"), failedAttemptslockout);
            assertTrue(issuerTypeErrors.containsKey("authLockoutMessage"));
            assertEquals(issuerTypeErrors.get("authLockoutMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("issuerLogo"));
            assertEquals(issuerTypeErrors.get("issuerLogo"), INVALID_ISSUER_LOGO_TYPE.name());

        }

        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure_res_gtr_width.jpeg",
                    "image/jpeg", FileUtils.readFileToByteArray(new File("testFailure_res_gtr_width.jpeg")));

            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> issuerTypeModel = addResult.getModelAndView().getModel();
            Map<String, Object> issuerTypeErrors = (Map<String, Object>) issuerTypeModel.get(C_MODEL_ATTR_ERRORS);
            assertTrue(issuerTypeErrors.containsKey("authErrorMessage"));
            assertEquals(issuerTypeErrors.get("authErrorMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("failedAttemptsToTriggerLockout"));
            assertEquals(issuerTypeErrors.get("failedAttemptsToTriggerLockout"), failedAttemptslockout);
            assertTrue(issuerTypeErrors.containsKey("authLockoutMessage"));
            assertEquals(issuerTypeErrors.get("authLockoutMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("issuerLogo"));
            assertEquals(issuerTypeErrors.get("issuerLogo"), invalidLogoSize);
        }
    }

    /* *//**
     * @throws Exception
     *             Validating the failure logic for TCH-Hosted w/Issuer Auth Web
     *             Service
     * 
     */

    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveFailureWebServiceAuth() throws Exception {
        Integer authMechanism = 5;
        String userId = "tch-configuration";
        String password = "password";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.jpeg", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.jpeg")));
            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> model = addResult.getModelAndView().getModel();
            Map<String, Object> errors = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(errors.containsKey("authErrorMessage"));
            assertEquals(errors.get("authErrorMessage"), "NotEmpty");
            assertTrue(errors.containsKey("authEndpoint"));
            assertTrue(errors.containsKey("authLockoutMessage"));
            assertEquals(errors.get("authLockoutMessage"), "NotEmpty");
            assertEquals(errors.get("authEndpoint"), INVALID_AUTH_ENDPOINT.name());
            assertTrue(errors.containsKey("accountListEndpoint"));
            assertEquals(errors.get("accountListEndpoint"), INVALID_ACCOUNT_LIST_ENDPOINT.name());
            assertTrue(errors.containsKey("issuerLogo"));
            assertEquals(errors.get("issuerLogo"), INVALID_ISSUER_LOGO_SIZE.name());
        }

        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.png", "image/jpeg",
                    FileUtils.readFileToByteArray(new File("testFailure.png")));
            MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> issuerTypeModel = addResult.getModelAndView().getModel();
            Map<String, Object> issuerTypeErrors = (Map<String, Object>) issuerTypeModel.get(C_MODEL_ATTR_ERRORS);
            assertTrue(issuerTypeErrors.containsKey("authErrorMessage"));
            assertEquals(issuerTypeErrors.get("authErrorMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("authLockoutMessage"));
            assertEquals(issuerTypeErrors.get("authLockoutMessage"), "NotEmpty");
            assertTrue(issuerTypeErrors.containsKey("issuerLogo"));
            assertEquals(issuerTypeErrors.get("issuerLogo"), INVALID_ISSUER_LOGO_TYPE.name());
        }

    }

    /**
     * @throws Exception
     *             IssuerLogo greater width
     */
    @Test
    @Transactional
    public void testIssuerLogoGreaterWidth() throws Exception {
        Integer authMechanism = 5;
        String userId = "tch-configuration";
        String password = "password";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure_res_gtr_width.jpeg",
                "image/jpeg", FileUtils.readFileToByteArray(new File("testFailure_res_gtr_width.jpeg")));
        MvcResult addResult = addissuerDetailsFailure(issuerLogo, authMechanism, session);
        Map<String, Object> issuerTypeModel = addResult.getModelAndView().getModel();
        @SuppressWarnings("unchecked")
        Map<String, Object> issuerTypeErrors = (Map<String, Object>) issuerTypeModel.get(C_MODEL_ATTR_ERRORS);
        assertTrue(issuerTypeErrors.containsKey("authErrorMessage"));
        assertEquals(issuerTypeErrors.get("authErrorMessage"), "NotEmpty");
        assertTrue(issuerTypeErrors.containsKey("authLockoutMessage"));
        assertEquals(issuerTypeErrors.get("authLockoutMessage"), "NotEmpty");
        assertTrue(issuerTypeErrors.containsKey("issuerLogo"));
        assertEquals(issuerTypeErrors.get("issuerLogo"), INVALID_ISSUER_LOGO_SIZE.name());
    }

    /**
     * @throws Exception
     *             Validating issuer logo with null value
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveNullIssuerLogo() throws Exception {

        String userId = "tch-configuration";
        String password = "password";
        {

            HttpSession session = this.mockMvc
                    .perform(
                            post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                    .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                    .getRequest().getSession();

            MvcResult result = this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.fileUpload("/issuer").session((MockHttpSession) session)
                                    .param("name", name).param("iid", iid).param("iisn", iisn)
                                    .param("authMechanism", "5").param("dropzonePath", dropZonePath)
                                    .param("selectedTokenRequestors", new String[] { "1", "2" })
                                    .param("disableCredentialsAfterLogin", disableCredentialsAfterLogin.toString())
                                    .param("authEndpoint", newauthEndpoint).param("authLockOutMessage", empty)
                                    .param("accountListEndpoint", newaccountListEndpoint)
                                    .param("authErrorMessage", empty).param("issuerLogo", empty)).andExpect(okStatus)
                    .andReturn();
            Map<String, Object> model = result.getModelAndView().getModel();
            Map<String, Object> errors = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(errors.containsKey("authErrorMessage"));
            assertEquals(errors.get("authErrorMessage"), "NotEmpty");
            assertTrue(errors.containsKey("authEndpoint"));
            assertTrue(errors.containsKey("authLockoutMessage"));
            assertEquals(errors.get("authLockoutMessage"), "NotEmpty");
            assertEquals(errors.get("authEndpoint"), INVALID_AUTH_ENDPOINT.name());
            assertTrue(errors.containsKey("accountListEndpoint"));
            assertEquals(errors.get("accountListEndpoint"), INVALID_ACCOUNT_LIST_ENDPOINT.name());
            assertTrue(errors.containsKey("issuerLogo"));
            assertEquals(errors.get("issuerLogo"), "NotEmpty");
        }

    }

    /**
     * @throws Exception
     *             Validating issuer image with Invalid type
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveInvalidIssuerLogo() throws Exception {

        Integer authMechanism = 5;
        String userId = "tch-configuration";
        String password = "password";
        {
            MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "testFailure.jpeg", "text/plain",
                    FileUtils.readFileToByteArray(new File("testFailure.doc")));
            HttpSession session = this.mockMvc
                    .perform(
                            post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                    .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                    .getRequest().getSession();

            MvcResult result = addissuerDetailsFailure(issuerLogo, authMechanism, session);
            Map<String, Object> model = result.getModelAndView().getModel();
            Map<String, Object> errors = (Map<String, Object>) model.get(C_MODEL_ATTR_ERRORS);
            assertTrue(errors.containsKey("authErrorMessage"));
            assertEquals(errors.get("authErrorMessage"), "NotEmpty");
            assertTrue(errors.containsKey("authEndpoint"));
            assertTrue(errors.containsKey("authLockoutMessage"));
            assertEquals(errors.get("authLockoutMessage"), "NotEmpty");
            assertEquals(errors.get("authEndpoint"), INVALID_AUTH_ENDPOINT.name());
            assertTrue(errors.containsKey("accountListEndpoint"));
            assertEquals(errors.get("accountListEndpoint"), INVALID_ACCOUNT_LIST_ENDPOINT.name());
            assertTrue(errors.containsKey("issuerLogo"));
            assertEquals(errors.get("issuerLogo"), INVALID_ISSUER_LOGO_TYPE.name());
        }
    }

    /**
     * @param issuerLogo
     *            -issuerLogo
     * @param authMechanism
     *            -authMechanism
     * @param session
     *            -session
     * @return Assigns the Issuer Details while Adding
     * @throws Exception
     *             Throws Exception In case of error
     */
    public MvcResult addissuerDetails(MockMultipartFile issuerLogo, Integer authMechanism, HttpSession session)
            throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/issuer").file(issuerLogo)
                .contentType(MediaType.MULTIPART_FORM_DATA).session((MockHttpSession) session).param("name", name)
                .param("iid", iid).param("iisn", iisn).param("authMechanism", authMechanism.toString())
                .param("dropzonePath", dropZonePath).param("selectedTokenRequestors", new String[] { "1", "2" })

                .param("authErrorMessage", authErrorMessage).param("authLockoutMessage", authLockoutMessage);

        if (authMechanism == 5) {
            builder = builder.param("authEndpoint", authEndpoint);
            builder = builder.param("accountListEndpoint", accountListEndpoint);

        } else {
            builder = builder.param("disableCredentialsAfterLogin", disableCredentialsAfterLogin.toString());
            builder = builder.param("failedAttemptsToTriggerLockout", failedAttemptsToTriggerLockout);

        }

        return this.mockMvc.perform(builder).andExpect(redirectedStatus).andReturn();
    }

    /**
     * @param issuerLogo
     *            -issuerLogo
     * @param authMechanism
     *            -authMechanism
     * @param session
     *            -session
     * @return Assigns the Issuer Details while Adding
     * @throws Exception
     *             Throws Exception In case of error
     */
    public MvcResult addissuerDetailsFailure(MockMultipartFile issuerLogo, Integer authMechanism, HttpSession session)
            throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/issuer").file(issuerLogo)
                .contentType(MediaType.MULTIPART_FORM_DATA).session((MockHttpSession) session).param("name", name)
                .param("iid", iid).param("iisn", iisn).param("authMechanism", authMechanism.toString())
                .param("dropzonePath", dropZonePath).param("selectedTokenRequestors", new String[] { "1", "2" })

                .param("authErrorMessage", empty).param("authLockoutMessage", empty);

        if (authMechanism == 5) {
            builder = builder.param("authEndpoint", newauthEndpoint);
            builder = builder.param("accountListEndpoint", newaccountListEndpoint);

        } else {
            builder = builder.param("disableCredentialsAfterLogin", disableCredentialsAfterLogin.toString());
            builder = builder.param("failedAttemptsToTriggerLockout", newfailedAttemptsToTriggerLockout);

        }

        return this.mockMvc.perform(builder).andExpect(okStatus).andReturn();
    }

    /**
     * @param issuerLogo
     *            -issuerLogo
     * @param session
     *            -session
     * @param authMechanism
     *            -authMechanism
     * @param result
     *            -result
     * @return Assigns the Issuer Details while Editing
     * @throws Exception
     *             Throws Exception In case of error
     */
    public MvcResult editissuerDetails(MockMultipartFile issuerLogo, HttpSession session, Integer authMechanism,
            String result) throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/issuer")
                .session((MockHttpSession) session).param("id", result).session((MockHttpSession) session)
                .param("name", name).param("iid", iid).param("iisn", iisn)
                .param("authMechanism", authMechanism.toString()).param("dropzonePath", dropZonePath)
                .param("selectedTokenRequestors", new String[] { "1", "2" })

                .param("authErrorMessage", newauthError).param("authLockoutMessage", authLockoutMessage);

        if (authMechanism == 5) {
            builder = builder.param("authEndpoint", authEndpoint);
            builder = builder.param("accountListEndpoint", accountListEndpoint);

        } else {
            builder = builder.param("disableCredentialsAfterLogin", disableCredentialsAfterLogin.toString());
            builder = builder.param("failedAttemptsToTriggerLockout", failedAttemptsToTriggerLockout);

        }

        return this.mockMvc.perform(builder).andExpect(redirectedStatus).andReturn();
    }

    /**
     * @throws Exception
     *             Validates the issuerLogo with lesser height
     */
    @Test
    @Transactional
    public void testIssuerLogoLessHeight() throws Exception {

        String fileName = "test_res_lwr_height.jpeg";
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test_res_lwr_height.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test_res_lwr_height.jpeg")));

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Validating with height and width with lesser resolutions.
     */
    @Test
    @Transactional
    public void testIssuerLogoBothLesser() throws Exception {

        String fileName = "test_res_both_lesser.jpeg";
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test_res_both_lesser.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test_res_both_lesser.jpeg")));

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Issuer Logo jpg type
     */
    @Test
    @Transactional
    public void testIssuerLogoJPG() throws Exception {

        String fileName = "test.jpg";
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test.jpg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test.jpg")));

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Validates the issuerLogo with lesser width
     */
    @Test
    @Transactional
    public void testIssuerLogoLessWidth() throws Exception {
        String fileName = "test_res_lwr_width.jpeg";
        Integer authMechanism = 3;
        String userId = "tch-configuration";
        String password = "password";
        MockMultipartFile issuerLogo = new MockMultipartFile("issuerLogo", "test_res_lwr_width.jpeg", "image/jpeg",
                FileUtils.readFileToByteArray(new File("test_res_lwr_width.jpeg")));

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus).andExpect(homeView).andReturn()
                .getRequest().getSession();
        MvcResult addResult = addissuerDetails(issuerLogo, authMechanism, session);

        String redirectedUrl = addResult.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.view().name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateFields((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, authErrorMessage, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

        MvcResult editResult = editissuerDetails(issuerLogo, session, authMechanism, resultId);
        String redirUrl = editResult.getResponse().getRedirectedUrl();
        String resId = redirUrl.split("\\?")[1].split("=")[1];
        MvcResult edResult = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(issuerDetView).andReturn();
        Map<String, Object> editmodel = edResult.getModelAndView().getModel();
        validateFields((IssuerDetail) editmodel.get(C_MODEL_ATTR_ISSUER_DETAILS), authMechanism, name, iid, iisn,
                dropZonePath, newauthError, authLockoutMessage, failedAttemptsToTriggerLockout, authEndpoint,
                accountListEndpoint, fileName);

    }

    /**
     * @throws Exception
     *             Field value validation
     * 
     * 
     */

    private static void validateFields(IssuerDetail issuerDetail, Integer expectedAuthMechanism, String Name,
            String Iid, String Iisn, String DropZonePath, String authError, String Authlockoutmessage,
            String FailedAttemptsToTriggerLockout, String Authendpoint, String AccountListEndpoint, String FileName) {

        Integer authenticationMech = issuerDetail.getAuthMechanism();
        assertEquals(issuerDetail.getName(), Name);
        assertEquals(issuerDetail.getIid(), Iid);
        assertEquals(issuerDetail.getIisn(), Iisn);
        assertEquals(issuerDetail.getDropzonePath(), DropZonePath);
        assertEquals(expectedAuthMechanism, authenticationMech);
        switch (authenticationMech) {
        case 3:
        case 4:
            assertEquals(issuerDetail.getAuthErrorMessage(), authError);
            assertEquals(issuerDetail.getAuthLockoutMessage(), Authlockoutmessage);
            assertEquals(issuerDetail.getFailedAttemptsToTriggerLockout(), FailedAttemptsToTriggerLockout);
            assertEquals(issuerDetail.getFileName(), FileName);

            break;

        case 5:
            assertEquals(issuerDetail.getAuthErrorMessage(), authError);
            assertEquals(issuerDetail.getAuthLockoutMessage(), Authlockoutmessage);
            assertEquals(issuerDetail.getAuthEndpoint(), Authendpoint);
            assertEquals(issuerDetail.getAccountListEndpoint(), AccountListEndpoint);
            assertEquals(issuerDetail.getFileName(), FileName);

            break;
        default:
            break;
        }

    }

}