package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_USER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_USER_URL;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.constant.AdminViewConstants.V_USER_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.USER_DELETE_URL;
import static org.tch.ste.admin.test.integration.AdminTestConstants.USER_LIST_URL;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.domain.dto.UserConfigurationProperties;
import org.tch.ste.admin.domain.dto.UserConfigurationSaveResponse;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

//import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_USER_ID;

/**
 * @author sharduls
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class UserControllerTest extends WebAppIntegrationTest {

    private String USER_ROLES = "authorizedRoles";
    private String ID = "id";
    private String USER_ID = "userId";
    private String FIRST_NAME = "firstName";
    private String LAST_NAME = "lastName";
    private String IISN = "iisn";
    private String IS_ISSUER_USER = "isIssuerUser";
    private String ISSUER_NAME = "issuerName";
    String loginId = "tch-user-mgmt";
    String password = "password";
    String userId = "tch-test-user-1";
    String firstName = "John";
    String lastName = "Wahberg";
    String isIssuerUser = "N";
    String issuerName = "hdfc";
    ResultMatcher httpStatusMatcher = MockMvcResultMatchers.status().isOk();

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<UserConfigurationSaveResponse> userConfigurationSaveResponseConverter;

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<UserConfigurationProperties> userConfigurationPropertiesConverter;

    private static String expectedJson = "[{\"id\":3,\"userId\":\"issuer-mgmt\",\"firstName\":\"Issuer\",\"lastName\":\"Manager\",\"userRoles\":\"ROLE_ISSUER_MGMT\",\"isIssuerUser\":null,\"issuerName\":\"hdfc\",\"iisn\":\"123456\",\"authorizedRoles\":[3]},"
            + "{\"id\":2,\"userId\":\"tch-configuration\",\"firstName\":\"Configuration\",\"lastName\":\"Manager\",\"userRoles\":\"ROLE_TCH_CONFIGURATION\",\"isIssuerUser\":null,\"issuerName\":null,\"iisn\":null,\"authorizedRoles\":[2]},"
            + "{\"id\":1,\"userId\":\"tch-user-mgmt\",\"firstName\":\"User\",\"lastName\":\"Manager\",\"userRoles\":\"ROLE_TCH_USER_MGMT\",\"isIssuerUser\":null,\"issuerName\":null,\"iisn\":null,\"authorizedRoles\":[1]},"
            + "{\"id\":4,\"userId\":\"unauthorized-issuer\",\"firstName\":\"Unauthorized\",\"lastName\":\"User\",\"userRoles\":\"UN_AUTHORIZED\",\"isIssuerUser\":null,\"issuerName\":null,\"iisn\":null,\"authorizedRoles\":[4]}]";

    /**
     * Default Constructor.
     */
    public UserControllerTest() {
    }

    /**
     * Test whether we fetch the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testUser() throws Exception {

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc.perform(get(USER_LIST_URL).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andExpect(viewResultMatcher.name(V_USER_PAGE)).andReturn();

        Map<String, Object> model = res.getModelAndView().getModel();
        validateUserList((String) model.get(C_MODEL_ATTR_USER));

    }

    /**
     * Validate the issuer list
     * 
     * @param issuers
     *            - the list of issuers in JSON format
     */
    private static void validateUserList(String users) {

        assertEquals(expectedJson, users);
    }

    /**
     * Test When Null Id is passed .
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testGetUserWithNullOrZeroId() throws Exception {

        String id = "0";
        // String NULL = null;
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult mvcResult = this.mockMvc.perform(get(C_USER_URL).param(ID, id).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andReturn();
        String outputJson = mvcResult.getResponse().getContentAsString();

        UserConfigurationProperties userConfigurationProperties = userConfigurationPropertiesConverter.objectify(
                outputJson, UserConfigurationProperties.class);
        assertNull(userConfigurationProperties.getId());
        assertNull(userConfigurationProperties.getUserId());
        assertNull(userConfigurationProperties.getAuthorizedRoles());
        assertNull(userConfigurationProperties.getFirstName());
        assertNull(userConfigurationProperties.getIsIssuerUser());
        assertNull(userConfigurationProperties.getIssuerName());
        assertNull(userConfigurationProperties.getLastName());
        assertNull(userConfigurationProperties.getUserRoles());
    }

    /**
     * Validates the delete logic.
     * 
     * @throws Exception
     *             Exception - The thrown exception.
     */
    @Test
    @Transactional
    public void testdeleteUser() throws Exception {
        String userIdToDelete = "unauthorized-issuer";
        String[] authorizedRoles = { "4" };
        Integer id = 4;
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(USER_DELETE_URL).session((MockHttpSession) session).param(USER_ID, userIdToDelete)
                                .param(USER_ROLES, authorizedRoles).param(ID, id.toString()))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();
    }

    /**
     * Validates the delete logic.
     * 
     * @throws Exception
     *             Exception - The thrown exception.
     */
    @Test
    @Transactional
    public void testdeleteIssuer() throws Exception {
        String userIdToDelete = "issuer-mgmt";
        String[] authorizedRoles = { "3" };
        Integer id = 3;
        String isIssuer = "Y";
        String iisn = "123456";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(USER_DELETE_URL).session((MockHttpSession) session).param(USER_ID, userIdToDelete)
                                .param(IS_ISSUER_USER, isIssuer).param(FIRST_NAME, firstName)
                                .param(LAST_NAME, lastName).param(ISSUER_NAME, issuerName).param(IISN, iisn)
                                .param(USER_ROLES, authorizedRoles).param(ID, id.toString()))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();
    }

    /**
     * This is used to test the save User function, when a duplicate UserId is
     * given
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testDuplicateUserId() throws Exception {
        String duplicateUserId = "unauthorized-issuer";
        String[] authorizedRoles = { "1", "2" };

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, duplicateUserId).param(USER_ROLES, authorizedRoles)
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists()).andReturn();

        String outputJson = mvcResult.getResponse().getContentAsString();

        UserConfigurationSaveResponse output = userConfigurationSaveResponseConverter.objectify(outputJson,
                UserConfigurationSaveResponse.class);
        assertEquals("This user already exists.", output.getUserIdErrorMsg());

    }

    /**
     * Test Adding and Editing a Issuer.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Transactional
    @Test
    public void testAddIssuer() throws Exception {
        String[] authorizedRoles = { "3" };
        Object[] authRoles = new Object[] { 3 };
        String iisn = "123456";
        String isIssuer = "Y";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userId).param(USER_ROLES, authorizedRoles)
                                .param(IS_ISSUER_USER, isIssuer).param(FIRST_NAME, firstName)
                                .param(LAST_NAME, lastName).param(ISSUER_NAME, issuerName).param(IISN, iisn)
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.success", Boolean.TRUE).exists())
                .andExpect(jsonPath("$.userConfigurationProperties.authorizedRoles", authRoles).exists()).andReturn();

        String outputJson = result.getResponse().getContentAsString();

        UserConfigurationSaveResponse output = userConfigurationSaveResponseConverter.objectify(outputJson,
                UserConfigurationSaveResponse.class);
        this.mockMvc
                .perform(
                        get(C_USER_URL).param(ID, output.getUserConfigurationProperties().getId().toString()).session(
                                (MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.userId", userId).exists())
                .andExpect(jsonPath("$.authorizedRoles", authRoles).exists()).andReturn();

    }

    /**
     * Test Adding and Editing a User
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testAddUser() throws Exception {

        String[] authorizedRoles = { "1", "2" };
        Object[] authRoles = new Object[] { 1, 2 };

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userId).param(USER_ROLES, authorizedRoles)
                                .param(IS_ISSUER_USER, isIssuerUser).param(FIRST_NAME, firstName)
                                .param(LAST_NAME, lastName).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        String outputJson = result.getResponse().getContentAsString();

        UserConfigurationSaveResponse output = userConfigurationSaveResponseConverter.objectify(outputJson,
                UserConfigurationSaveResponse.class);
        this.mockMvc
                .perform(
                        get(C_USER_URL).param(ID, output.getUserConfigurationProperties().getId().toString()).session(
                                (MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.userId", userId).exists())
                .andExpect(jsonPath("$.authorizedRoles", authRoles).exists()).andReturn();

    }

    /**
     * Test User doesn't exist in Active Directory.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testUserDoesNotExistInAd() throws Exception {

        String userIdForUserNotInAd = "john";
        String[] authorizedRoles = { "1", "2" };

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userIdForUserNotInAd).param(USER_ROLES, authorizedRoles)
                                .param(IS_ISSUER_USER, isIssuerUser).param(FIRST_NAME, firstName)
                                .param(LAST_NAME, lastName).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.userIdErrorMsg", "This user does not exist in Active Directory.").exists())
                .andReturn();

    }

    /**
     * Test User doesn't exist in Active Directory.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testUserWithOutRequiredParams() throws Exception {
        String isIssuer = "Y";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userId).param(IS_ISSUER_USER, isIssuer)
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.firstNameErrorMsg", "Please enter a value.").exists())
                .andExpect(jsonPath("$.lastNameErrorMsg", "Please enter a value.").exists())
                .andExpect(jsonPath("$.iisnErrorMsg", "Please select the issuer.").exists())
                .andExpect(jsonPath("$.rolesErrorMsg", "Please select a role.").exists()).andReturn();

        this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userId).param(IS_ISSUER_USER, isIssuer).param(IISN, issuerName)
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.firstNameErrorMsg", "Please enter a value.").exists())
                .andExpect(jsonPath("$.lastNameErrorMsg", "Please enter a value.").exists())
                .andExpect(jsonPath("$.rolesErrorMsg", "Please select a role.").exists()).andReturn();
    }

    /**
     * Test Remove User Roles .
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testInvalidUserRoles() throws Exception {

        String id = "3";
        String userIdRemoveRoles = "issuer-mgmt";
        String[] authorizedRoles = new String[1];

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc.perform(get(C_USER_URL).param(ID, id).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.userId", userIdRemoveRoles).exists()).andReturn();

        String outputJson = result.getResponse().getContentAsString();
        UserConfigurationProperties output = userConfigurationPropertiesConverter.objectify(outputJson,
                UserConfigurationProperties.class);
        this.mockMvc
                .perform(
                        post(C_USER_URL).session((MockHttpSession) session).param(USER_ID, output.getUserId())
                                .param(FIRST_NAME, output.getFirstName()).param(LAST_NAME, output.getLastName())
                                .param(IS_ISSUER_USER, output.getIsIssuerUser()).param(USER_ROLES, authorizedRoles)
                                .param(ID, id)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.rolesErrorMsg", "Please select a role.").exists()).andReturn();

    }

    /**
     * Test whether the Issuer User works Correctly.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testIssuerUserFailure() throws Exception {

        String[] authorizedRoles = { "3" };
        String isIssuer = "Y";
        String firstNameErrorMsg = "First Name is required ";
        String lastNameErrorMsg = "Last Name is required";
        String iisnErrorMsg = "Please select the issuer.";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, userId).param(USER_ROLES, authorizedRoles)
                                .param(IS_ISSUER_USER, isIssuer).session((MockHttpSession) session))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(jsonPath("$.firstNameErrorMsg", firstNameErrorMsg).exists())
                .andExpect(jsonPath("$.lastNameErrorMsg", lastNameErrorMsg).exists())
                .andExpect(jsonPath("$.iisnErrorMsg", iisnErrorMsg).exists()).andReturn();

    }

    /**
     * Test Adding and Removing Roles of a User.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testAddAndRemoveRole() throws Exception {

        String userIdTestRoles = "tch-test-user-2";
        String authorizedRolesToAdd[] = { "1" };
        String authorizedRolesToSave[] = { "2", "3" };
        Integer authorizedRolesToCheck[] = { 2, 3 };
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, loginId)
                                .param(PASSWORD, password)).andExpect(httpStatusMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult addUserResult = this.mockMvc
                .perform(
                        post(C_USER_URL).session((MockHttpSession) session).param(USER_ID, userIdTestRoles)
                                .param(FIRST_NAME, firstName).param(LAST_NAME, lastName)
                                .param(IS_ISSUER_USER, isIssuerUser).param(USER_ROLES, authorizedRolesToAdd))
                .andExpect(httpStatusMatcher).andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        String addUserJson = addUserResult.getResponse().getContentAsString();
        UserConfigurationSaveResponse addUserResponse = userConfigurationSaveResponseConverter.objectify(addUserJson,
                UserConfigurationSaveResponse.class);

        MvcResult editUserResult = this.mockMvc
                .perform(
                        get(C_USER_URL).param(ID, addUserResponse.getUserConfigurationProperties().getId().toString())
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher).andReturn();

        String editUserJson = editUserResult.getResponse().getContentAsString();
        UserConfigurationProperties editUserOutput = userConfigurationPropertiesConverter.objectify(editUserJson,
                UserConfigurationProperties.class);

        MvcResult saveUserResult = this.mockMvc
                .perform(
                        post(C_USER_URL).param(USER_ID, editUserOutput.getUserId())
                                .param(FIRST_NAME, editUserOutput.getFirstName())
                                .param(LAST_NAME, editUserOutput.getLastName())
                                .param(IS_ISSUER_USER, editUserOutput.getIsIssuerUser())
                                .param(USER_ROLES, authorizedRolesToSave).param(ID, editUserOutput.getId().toString())
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher)
                .andExpect(jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        String saveUserjson = saveUserResult.getResponse().getContentAsString();
        UserConfigurationSaveResponse saveUserResponse = userConfigurationSaveResponseConverter.objectify(saveUserjson,
                UserConfigurationSaveResponse.class);

        MvcResult verifyUserResult = this.mockMvc
                .perform(
                        get(C_USER_URL).param(ID, saveUserResponse.getUserConfigurationProperties().getId().toString())
                                .session((MockHttpSession) session)).andExpect(httpStatusMatcher).andReturn();
        String verifyUserJson = verifyUserResult.getResponse().getContentAsString();
        UserConfigurationProperties verifyOutput = userConfigurationPropertiesConverter.objectify(verifyUserJson,
                UserConfigurationProperties.class);
        assertArrayEquals(authorizedRolesToCheck, verifyOutput.getAuthorizedRoles());

    }

}
