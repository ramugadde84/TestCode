package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MAPPING_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ERRORS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER_DETAILS;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_ID;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_ISSUER_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.ISSUER_LIST_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.admin.domain.dto.BinMappingSaveResponse;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.admin.service.core.mapping.BinMappingService;
import org.tch.ste.domain.entity.PermittedTokenRequestor;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * Test for issuer controller.
 * 
 * @author Karthik.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class IssuerControllerTest extends WebAppIntegrationTest {

    private static String expectedJson = "[{\"id\":1,\"name\":\"hdfc\",\"iid\":null,\"iisn\":\"123456\",\"tokenRequestor\":\"google-wallet,papal-wallet,spring-wallet,yahoo-wallet\"},"
            + "{\"id\":2,\"name\":\"icici\",\"iid\":null,\"iisn\":\"123556\",\"tokenRequestor\":\"jpa-wallet,papal-wallet,zippy-wallet\"}]";

    private static final String issuerConfigurationUrl = "/issuer";
    private static final String id = "1";
    private static final String issuerIid = "567856.123456";
    private static final String issuerIisn = "123456";
    private static final String issuerName = "hdfc";
    private static final String issuerDropzonePath = "/hdfc";
    private static final String name = "iciic";
    private static final String iid = "999967.987654";
    private static final String iisn = "123656";
    private static final String dropZonePath = "abc";
    private static final Integer authenticationMechanism = 1;
    private static final Integer changeAuthenticationMechanism = 2;
    private static final String duplicateName = "hdfc";
    private static final String duplicateIisn = "123456";
    private static final String realBin = "254324";
    private static final String tokenBin = "213454";
    private static final String userId = "tch-configuration";
    private static final String password = "password";
    private static Integer savedId = 0;

    private static final java.util.Map<Integer, Boolean> mapvalue() {

        Map<Integer, Boolean> supportedTokens = new HashMap<Integer, Boolean>();

        supportedTokens.put(1, Boolean.TRUE);
        supportedTokens.put(2, Boolean.TRUE);
        supportedTokens.put(3, Boolean.TRUE);
        supportedTokens.put(4, Boolean.TRUE);

        return supportedTokens;
    }

    private static final java.util.Map<Integer, Boolean> mapvalueAfterEdit() {

        Map<Integer, Boolean> supportedTokens = new HashMap<Integer, Boolean>();

        supportedTokens.put(1, Boolean.TRUE);
        supportedTokens.put(2, Boolean.TRUE);
        supportedTokens.put(3, Boolean.TRUE);

        return supportedTokens;
    }

    @Autowired
    @Qualifier("defaultInjectorImpl")
    private EntityManagerInjector emInjector;

    @Autowired
    @Qualifier("permittedTokenRequestorDao")
    private JpaDao<PermittedTokenRequestor, Integer> permittedTokenRequestorDao;

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject("123456");
    }

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<BinMappingSaveResponse> binMappingSaveResponseConverter;

    @Autowired
    private BinMappingService binMappingService;

    // private static String emptyProperty = "";

    /**
     * Default Constructor.
     */
    public IssuerControllerTest() {
    }

    /**
     * Test whether we fetch the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc.perform(get(ISSUER_LIST_URL).session((MockHttpSession) session))
                .andExpect(okStatus).andExpect(viewResultMatcher.name(V_ISSUER_PAGE)).andReturn();

        Map<String, Object> model = res.getModelAndView().getModel();
        validateIssuerList((String) model.get(C_MODEL_ATTR_ISSUER));

    }

    /**
     * Validate the issuer list
     * 
     * @param issuers
     *            - the list of issuers in JSON format
     */
    private static void validateIssuerList(String issuers) {
        assertEquals(expectedJson, issuers);
    }

    /**
     * Validates the edit issuer details page
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testEditIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(get(issuerConfigurationUrl).param("id", id).session((MockHttpSession) session))
                .andExpect(okStatus).andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        IssuerDetail issuerDetail = (IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS);

        assertEquals(issuerIid, issuerDetail.getIid());
        assertEquals(issuerIisn, issuerDetail.getIisn());
        assertEquals(issuerName, issuerDetail.getName());
        assertEquals(issuerDropzonePath, issuerDetail.getDropzonePath());

    }

    /**
     * 
     * Tests the save functionality in add issuer page
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testSaveIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("name", name).param("iid", iid)
                                .param("iisn", iisn).param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();
        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL));

        result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("name", name).param("iid", iid)
                                .param("iisn", iisn).param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL, "http://google.com")
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();
        errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL));

        result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("id", "1").param("name", name)
                                .param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL, "https://google.com")
                                .param("selectedTokenRequestors", new String[] { "1", "2" }))
                .andExpect(redirectedStatus).andReturn();

        validatePermittedTokenRequestors(1234, 4566);
        String redirectedUrl = result.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        validateNewProperties((IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS));

        this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("id", "1").param("name", name)
                                .param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL, "https://google.com")
                                .param("selectedTokenRequestors", new String[] { "1", "3" }))
                .andExpect(redirectedStatus).andReturn();
        validatePermittedTokenRequestors(1234, 8089);
    }

    /**
     * Validates the token requestors.
     * 
     * @param trId1
     *            int - The first.
     * @param trdId2
     *            int - The second.
     */
    private void validatePermittedTokenRequestors(int trId1, int trdId2) {
        List<PermittedTokenRequestor> permittedTokenRequestors = permittedTokenRequestorDao.getAll();
        assertTrue(permittedTokenRequestors.size() == 2);
        for (PermittedTokenRequestor permittedTokenRequestor : permittedTokenRequestors) {
            assertTrue(permittedTokenRequestor.getNetworkTokenRequestorId() == trId1
                    || permittedTokenRequestor.getNetworkTokenRequestorId() == trdId2);
        }
    }

    /**
     * @param configuration
     * 
     */
    private static void validateNewProperties(IssuerDetail issuerDetail) {
        assertEquals(issuerDetail.getName(), name);
        assertEquals(issuerDetail.getIid(), iid);
        assertEquals(issuerDetail.getIisn(), iisn);
        assertEquals(issuerDetail.getDropzonePath(), dropZonePath);
        assertEquals(issuerDetail.getAuthMechanism(), authenticationMechanism);
    }

    /**
     * This method is used to test the validation for issuer name where null
     * value is sent while saving the issuer details
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testNullNameForSaveIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("name"));
        if (errors.get("name").equals("NotEmpty")) {
            assertEquals("NotEmpty", errors.get("name"));
        } else {
            assertEquals("NotBlank", errors.get("name"));
        }
    }

    /**
     * This is used to test the save issuer function, when the name of the
     * issuer is empty
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testEmptyNameForSaveIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", authenticationMechanism.toString()).param("name", "")
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("name"));
        if (errors.get("name").equals("NotEmpty")) {
            assertEquals("NotEmpty", errors.get("name"));
        } else {
            assertEquals("NotBlank", errors.get("name"));
        }

    }

    /**
     * 
     * This method is used to test the validation for iisn where null value is
     * sent while saving the issuer details
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testNullIisnForSaveIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid).param("name", name)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("iisn"));
        assertEquals("CUSTOM_NULL", errors.get("iisn"));
    }

    /**
     * This is used to test the save issuer function, when the iisn of the
     * issuer is empty
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testEmptyIisnForSaveIssuer() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid).param("iisn", "")
                                .param("authMechanism", authenticationMechanism.toString()).param("name", name)
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("iisn"));
        assertEquals("CUSTOM_EMPTY", errors.get("iisn"));

    }

    /**
     * This is used to test the save issuer function, when a duplicate issuer
     * name is given
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDuplicateIssuerName() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("name", duplicateName).param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("name"));
        assertEquals("NAME_ALREADY_EXISTS", errors.get("name"));

    }

    /**
     * This is used to test the save issuer function, when a duplicate iisn is
     * given
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @SuppressWarnings("unchecked")
    @Test
    @Transactional
    public void testDuplicateIisnName() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", iid)
                                .param("iisn", duplicateIisn)
                                .param("authMechanism", authenticationMechanism.toString()).param("name", name)
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" })).andExpect(okStatus)
                .andReturn();

        Map<String, Object> errors = (Map<String, Object>) result.getModelAndView().getModel().get(C_MODEL_ATTR_ERRORS);
        assertTrue(errors.containsKey("iisn"));
        assertEquals("IISN_ALREADY_EXISTS", errors.get("iisn"));

    }

    /**
     * 
     * This is to test the supported token requestors is saved correctly after
     * its edited
     * 
     * @throws Exception
     *             - Exception
     */
    @Ignore
    @Transactional
    public void testEditSupportedTokenRequestors() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(get(issuerConfigurationUrl).param("id", id).session((MockHttpSession) session))
                .andExpect(okStatus).andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> model = res.getModelAndView().getModel();
        IssuerDetail issuerDetail = (IssuerDetail) model.get(C_MODEL_ATTR_ISSUER_DETAILS);

        assertEquals(issuerIid, issuerDetail.getIid());
        assertEquals(issuerIisn, issuerDetail.getIisn());
        assertEquals(issuerName, issuerDetail.getName());
        assertEquals(issuerDropzonePath, issuerDetail.getDropzonePath());
        Integer expectedAuthenticationMechanism = 3;
        assertEquals(expectedAuthenticationMechanism, issuerDetail.getAuthMechanism());
        assertEquals(mapvalue(), issuerDetail.getCurrentTokenRequestors());

        MvcResult result = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("iid", issuerIid)
                                .param("iisn", issuerIisn).param("id", id)
                                .param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", issuerDropzonePath).param("name", issuerName)
                                .param("selectedTokenRequestors", new String[] { "1", "2", "3" }))
                .andExpect(redirectedStatus).andReturn();

        String redirectedUrl = result.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        MvcResult res1 = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> model1 = res1.getModelAndView().getModel();
        validateNewPropertiesforEdit((IssuerDetail) model1.get(C_MODEL_ATTR_ISSUER_DETAILS));

    }

    /**
     * @param issuerDetail
     */
    private static void validateNewPropertiesforEdit(IssuerDetail issuerDetail) {

        assertEquals(issuerIid, issuerDetail.getIid());
        assertEquals(issuerIisn, issuerDetail.getIisn());
        assertEquals(issuerName, issuerDetail.getName());
        assertEquals(issuerDropzonePath, issuerDetail.getDropzonePath());
        assertEquals(mapvalueAfterEdit(), issuerDetail.getCurrentTokenRequestors());

    }

    /**
     * 
     * Tests the issuer add/edit/add bin mapping/change authentication mechanism
     * for an issuer
     * 
     * @throws Exception
     *             - Exception
     */
    @Ignore
    @Transactional
    public void testIssuerConfiguration() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        // Add New issuer.
        MvcResult addIssuerResult = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("name", name).param("iid", iid)
                                .param("iisn", iisn).param("authMechanism", authenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" }))
                .andExpect(redirectedStatus).andReturn();
        String addRedirectedUrl = addIssuerResult.getResponse().getRedirectedUrl();
        String addResultId = addRedirectedUrl.split("\\?")[1].split("=")[1];
        // Validating the saved issuer details.
        MvcResult addRes = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, addResultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> addModel = addRes.getModelAndView().getModel();
        validateNewProperties((IssuerDetail) addModel.get(C_MODEL_ATTR_ISSUER_DETAILS));

        // Add bin mapping for added issuer
        MvcResult result = this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        BinMappingSaveResponse binMappingSaveResponse = binMappingSaveResponseConverter.objectify(result.getResponse()
                .getContentAsString(), BinMappingSaveResponse.class);

        // Validating a bin mapping details for same issuer.
        Integer mappingId = binMappingSaveResponse.getBinMapping().getId();
        this.mockMvc
                .perform(
                        get(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("id", mappingId.toString())
                                .param("iisn", iisn)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", savedId).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iisn", iisn).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.realBin", realBin).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokenBin", tokenBin).exists());

        BinMapping binMapping = binMappingService.getBinMapping(mappingId);
        assertEquals(realBin, binMapping.getRealBin());
        assertEquals(tokenBin, binMapping.getTokenBin());

        // Change authentication mechanism for above issuer and save it.
        MvcResult result1 = this.mockMvc
                .perform(
                        post("/issuer").session((MockHttpSession) session).param("id", addResultId).param("name", name)
                                .param("iid", iid).param("iisn", iisn)
                                .param("authMechanism", changeAuthenticationMechanism.toString())
                                .param("dropzonePath", dropZonePath)
                                .param("selectedTokenRequestors", new String[] { "1", "2" }))
                .andExpect(redirectedStatus).andReturn();

        String redirectedUrl = result1.getResponse().getRedirectedUrl();
        String resultId = redirectedUrl.split("\\?")[1].split("=")[1];
        // Verify the updated mechanism information.
        MvcResult res1 = this.mockMvc
                .perform(
                        get("/issuer").param(C_REQUEST_PARAM_ISSUER_ID, resultId).session((MockHttpSession) session)
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name("issuerDetails")).andReturn();
        Map<String, Object> model1 = res1.getModelAndView().getModel();
        validateModifiedProperties((IssuerDetail) model1.get(C_MODEL_ATTR_ISSUER_DETAILS));

    }

    /**
     * Verify the updated issuer authentication mechanism details.
     * 
     * @param issuerDetail
     */
    private static void validateModifiedProperties(IssuerDetail issuerDetail) {
        assertEquals(issuerDetail.getName(), name);
        assertEquals(issuerDetail.getIid(), iid);
        assertEquals(issuerDetail.getIisn(), iisn);
        assertEquals(issuerDetail.getDropzonePath(), dropZonePath);
        assertEquals(issuerDetail.getAuthMechanism(), changeAuthenticationMechanism);
    }

}
