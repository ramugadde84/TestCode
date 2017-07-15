package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_TOKEN_REQUESTOR;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_TOKEN_REQUESTOR_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.TOKEN_REQUESTOR_DELETE_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.TOKEN_REQUESTOR_LIST_URL;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.domain.dto.TokenRequestorSaveResponse;
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
public class TokenRequestorControllerTest extends WebAppIntegrationTest {

    /**
     * 
     */
    private static String userId = "tch-configuration";
    private static String password = "password";
    private static String tokenRequestorUrl = "/tokenRequestor";
    private static String name = "name";
    private static String networkId = "networkId";
    private static String newName = "newName";
    private static String newNetworkId = "65432";
    private static String validateName = "google-wallet";
    private static String validateNetworkId = "1234";
    private static Integer id = 1;
    private static String editName = "google-wallet1";
    private static String editNetworkId = "1234";

    private static final String tokenRequestorJson = "[{\"id\":1,\"name\":\"google-wallet\",\"networkId\":\"1234\"},"
            + "{\"id\":6,\"name\":\"jpa-wallet\",\"networkId\":\"3456\"},"
            + "{\"id\":4,\"name\":\"papal-wallet\",\"networkId\":\"1233\"},"
            + "{\"id\":3,\"name\":\"spring-wallet\",\"networkId\":\"8089\"},"
            + "{\"id\":2,\"name\":\"yahoo-wallet\",\"networkId\":\"4566\"},"
            + "{\"id\":5,\"name\":\"zippy-wallet\",\"networkId\":\"6809\"}]";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<TokenRequestorSaveResponse> tokenRequestorSaveResponseConverter;

    /**
     * Default Constructor.
     * 
     */
    public TokenRequestorControllerTest() {
    }

    /**
     * Test whether we fetch the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    public void testList() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc.perform(get(TOKEN_REQUESTOR_LIST_URL).session((MockHttpSession) session))
                .andExpect(okStatus).andExpect(viewResultMatcher.name(V_TOKEN_REQUESTOR_PAGE)).andReturn();

        Map<String, Object> model = res.getModelAndView().getModel();
        validatetokenRequestorJsonList((String) model.get(C_MODEL_ATTR_TOKEN_REQUESTOR));

    }

    /**
     * Validates the save logic.
     * 
     * @throws Exception
     *             Exception - The thrown exception.
     */
    @Test
    @Transactional
    public void testsave() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        MvcResult res = this.mockMvc
                .perform(
                        post(tokenRequestorUrl).session((MockHttpSession) session).param(name, newName)
                                .param(networkId, newNetworkId)).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();
        String outputJson = res.getResponse().getContentAsString();

        TokenRequestorSaveResponse output = tokenRequestorSaveResponseConverter.objectify(outputJson,
                TokenRequestorSaveResponse.class);

        this.mockMvc
                .perform(
                        get(tokenRequestorUrl).param("id", output.getTokenRequestor().getId().toString()).session(
                                (MockHttpSession) session)).andExpect(okStatus)
                .andExpect(jsonPath("$.networkId", newNetworkId).exists())
                .andExpect(jsonPath("$.name", newName).exists()).andReturn();
        // to test edit
        MvcResult res1 = this.mockMvc
                .perform(
                        post(tokenRequestorUrl).session((MockHttpSession) session).param("id", id.toString())
                                .param(name, editName).param(networkId, editNetworkId)).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();
        String outputJson1 = res1.getResponse().getContentAsString();

        TokenRequestorSaveResponse output1 = tokenRequestorSaveResponseConverter.objectify(outputJson1,
                TokenRequestorSaveResponse.class);

        this.mockMvc
                .perform(
                        get(tokenRequestorUrl).param("id", output1.getTokenRequestor().getId().toString()).session(
                                (MockHttpSession) session)).andExpect(okStatus)
                .andExpect(jsonPath("$.networkId", editNetworkId).exists())
                .andExpect(jsonPath("$.name", editName).exists()).andReturn();
    }

    /**
     * Validate the tokenRequestorJson list * @param tokenRequestorJson - the
     * list of tokenRequestorJson in JSON format
     */
    private static void validatetokenRequestorJsonList(String tokenRequestor) {
        assertEquals(tokenRequestorJson, tokenRequestor);
    }

    /**
     * 
     * @throws Exception
     *             Exception.
     */
    @Test
    @Transactional
    public void validateSave() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc.perform(post(tokenRequestorUrl).session((MockHttpSession) session)).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.nameErrorMsg", "Please enter a value.").exists()).andReturn();

        this.mockMvc
                .perform(
                        post(tokenRequestorUrl).session((MockHttpSession) session).param(name, "")
                                .param(networkId, newNetworkId)).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.nameErrorMsg", "Please enter a value.").exists()).andReturn();

        this.mockMvc
                .perform(
                        post(tokenRequestorUrl).session((MockHttpSession) session).param(name, newName)
                                .param(networkId, "")).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.networkIdErrorMsg", "Please enter a value.").exists()).andReturn();
    }

    /**
     * This is used to test the save token requestor function, when a duplicate
     * token requestor name is given
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testDuplicateTokenRequestorName() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc.perform(post(tokenRequestorUrl).session((MockHttpSession) session).param("name", validateName))
                .andExpect(okStatus).andExpect(jsonPath("$.success", Boolean.TRUE).exists())
                .andExpect(jsonPath("$.nameErrorMsg", "Token Requestor Name already exists.").exists()).andReturn();

    }

    /**
     * This is used to test the save token requestor function, when a duplicate
     * token requestor ID is given
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testDuplicateTokenRequestorID() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(tokenRequestorUrl).session((MockHttpSession) session)
                                .param("networkId", validateNetworkId)).andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.TRUE).exists())
                .andExpect(jsonPath("$.networkIdErrorMsg", "Token Requestor ID already exists.").exists()).andReturn();
    }

    /**
     * Validates the delete logic.
     * 
     * @throws Exception
     *             Exception - The thrown exception.
     */
    @Test
    @Transactional
    public void testdelete() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(post(TOKEN_REQUESTOR_DELETE_URL).session((MockHttpSession) session).param("id", id.toString()))
                .andExpect(okStatus).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        MvcResult res = this.mockMvc
                .perform(get(tokenRequestorUrl).param("id", id.toString()).session((MockHttpSession) session))
                .andExpect(okStatus).andReturn();

        String result = res.getResponse().getContentAsString();
        assertTrue(result == null || result.isEmpty());

    }
}
