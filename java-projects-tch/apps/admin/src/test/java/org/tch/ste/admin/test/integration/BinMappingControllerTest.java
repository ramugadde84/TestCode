package org.tch.ste.admin.test.integration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MAPPING_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.BinMappingInfoErrorCode;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.admin.domain.dto.BinMappingSaveResponse;
import org.tch.ste.admin.service.core.mapping.BinMappingService;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class BinMappingControllerTest extends WebAppIntegrationTest {

    private static final String iisn = "123456";

    private static final String userId = "tch-configuration";
    private static final String password = "password";

    @Autowired
    @Qualifier("defaultInjectorImpl")
    private EntityManagerInjector emInjector;

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<BinMappingSaveResponse> binMappingSaveResponseConverter;

    @Autowired
    private BinMappingService binMappingService;

    /**
     * Injects the entity manager.
     */
    @BeforeTransaction
    public void setEntityManagerThreadLocal() {
        emInjector.inject(iisn);
    }

    /**
     * Test whether we save the expected value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void saveBinMapping() throws Exception {
        final String realBin = "254326";
        final String tokenBin = "213456";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult result = this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        BinMappingSaveResponse binMappingSaveResponse = binMappingSaveResponseConverter.objectify(result.getResponse()
                .getContentAsString(), BinMappingSaveResponse.class);

        Integer id = binMappingSaveResponse.getBinMapping().getId();
        this.mockMvc
                .perform(
                        get(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("id", id.toString())
                                .param("iisn", iisn)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", id).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iisn", iisn).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.realBin", realBin).exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tokenBin", tokenBin).exists());
    }

    /**
     * Tests the first digit validation.
     * 
     * @throws Exception
     *             - The exception thrown.
     */
    @Test
    @Transactional
    public void testBinValidationDigitBased() throws Exception {
        final String realBin = "12345";
        final String tokenBin = "23456";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.tokenBinErrMsg",
                                messageSource.getMessage(
                                        BinMappingInfoErrorCode.FIRIST_DIGIT_VALIDATION_FOR_REAL_AND_TOKEN_BIN.name(),
                                        null, defaultLocale)).exists()).andReturn();
    }

    /**
     * Tests that the first digit is part of the Networks table.
     * 
     * @throws Exception
     *             - Any exception thrown.
     */
    @Test
    @Transactional
    public void testValidationDigitBasedOnNetworkId() throws Exception {
        final String realBin = "12345";
        final String tokenBin = "63456";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.tokenBinErrMsg",
                                messageSource.getMessage(
                                        BinMappingInfoErrorCode.BIN_NETWORK_FIRST_DIGIT_VALIDATOR.name(), null,
                                        defaultLocale)).exists()).andReturn();

    }

    /**
     * Tests for an existing real bin.
     * 
     * @throws Exception
     *             - Any Exception thrown.
     */
    @Test
    @Transactional
    public void testForExistingsBins() throws Exception {
        final String iisnUsed = "123556";
        final String realBin = "123469";
        final String tokenBin = "112345";
        ResultMatcher okMatcher = okStatus;
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okMatcher)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisnUsed)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okMatcher)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.realBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.REAL_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisnUsed)
                                .param("realBin", tokenBin).param("tokenBin", realBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.tokenBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.TOKEN_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
    }

    /**
     * The method name should be self explanatory.
     * 
     * @throws Exception
     *             - Any exception thrown.
     */
    @Test
    @Transactional
    public void testRealBinMatchesExistingTokenAndViceVersa() throws Exception {
        String realBin = "123459";
        String tokenBin = "111111";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.realBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.REAL_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
        realBin = "111111";
        tokenBin = "123469";
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.tokenBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.TOKEN_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
    }

    /**
     * Test for duplicate real bin.
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testDuplicateRealBin() throws Exception {
        final String realBin = "123469";
        final String tokenBin = "123499";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("realBin", realBin)
                                .param("tokenBin", tokenBin).param("iisn", iisn))
                .andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        jsonPath(
                                "$.realBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.REAL_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();

    }

    /**
     * Test for duplicate real bin.
     * 
     * 
     * @throws Exception
     *             - Exception
     */
    @Test
    @Transactional
    public void testDuplicateTokenBin() throws Exception {
        final String tokenBin = "123459";
        final String realBin = "123469";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("tokenBin", tokenBin)
                                .param("realBin", realBin).param("iisn", iisn))
                .andExpect(okStatus)
                .andExpect(jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        jsonPath(
                                "$.tokenBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.REAL_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
    }

    /**
     * Test edit bin mapping values.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testEditBinMapping() throws Exception {
        String realBin = "123459";
        String tokenBin = "111111";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.realBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.REAL_BIN_ALREADY_EXISTS.name(), null,
                                        defaultLocale)).exists()).andReturn();
        // Edit real bin and token bin values
        realBin = "123457";
        tokenBin = "111112";
        MvcResult result = this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin)).andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists()).andReturn();

        BinMappingSaveResponse binMappingSaveResponse = binMappingSaveResponseConverter.objectify(result.getResponse()
                .getContentAsString(), BinMappingSaveResponse.class);
        // Validate edited real bin and token bin values.
        Integer id = binMappingSaveResponse.getBinMapping().getId();
        BinMapping binMapping = binMappingService.getBinMapping(id);
        assertEquals(realBin, binMapping.getRealBin());
        assertEquals(tokenBin, binMapping.getTokenBin());

    }

    /**
     * Test to realBin and tokenBin values should be same.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testRealAndTokenBinValuesSame() throws Exception {
        String realBin = "123458";
        String tokenBin = "123458";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", realBin).param("tokenBin", tokenBin))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath(
                                "$.realBinErrMsg",
                                messageSource.getMessage(BinMappingInfoErrorCode.BINS_CANNOT_BE_SAME.name(), null,
                                        defaultLocale)).exists()).andReturn();

    }

    /**
     * Test to real bin values to be empty.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    @Transactional
    public void testRealAndTokenBinValuesEmpty() throws Exception {
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();
        this.mockMvc
                .perform(
                        post(C_MAPPING_REQ_MAPPING).session((MockHttpSession) session).param("iisn", iisn)
                                .param("realBin", "").param("tokenBin", "123458"))
                .andExpect(okStatus)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.FALSE).exists())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.realBinErrMsg",
                                messageSource.getMessage("NotBlank", null, defaultLocale)).exists()).andReturn();

    }
}
