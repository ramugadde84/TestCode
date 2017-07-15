/**
 * 
 */
package org.tch.ste.admin.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.admin.constant.AdminConstants.LOGIN_ID;
import static org.tch.ste.admin.constant.AdminConstants.PASSWORD;
import static org.tch.ste.admin.constant.AdminViewConstants.V_HOME_PAGE;
import static org.tch.ste.admin.test.integration.AdminTestConstants.LOGIN_URL;

import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.tch.ste.admin.domain.dto.TokenRule;
import org.tch.ste.admin.domain.dto.TokenRuleLoadResponse;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * Test Controller for Token Rules.
 * 
 * @author ramug
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class TokensRuleControllerTest extends WebAppIntegrationTest {

    private static final String iisn = "123456";
    private static final String bin = "123466";

    /**
     * Default controller.
     */
    public TokensRuleControllerTest() {

    }

    /**
     * Used for testing token rules,By using this method we are sending iisn
     * value to Token Rules controller and we are fetching the token rules and
     * doing null checks.
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    public void testTokenRulesResponseNotNull() throws Exception {
        String userId = "tch-configuration";
        String password = "password";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(get("/issuer/tokenRules").param("iisn", iisn).session((MockHttpSession) session))
                .andExpect(okStatus).andReturn();

        String responseTokenData = res.getResponse().getContentAsString();
        TokenRuleLoadResponse responseTokenRules = new JsonObjectConverter<TokenRuleLoadResponse>().objectify(
                responseTokenData, TokenRuleLoadResponse.class);
        // Null Checks.
        Assert.assertNotNull(responseTokenRules.getBins());
    }

    /**
     * Based on Bin token rules are loading,Here IISN is need as input why
     * because schema selection is done only when we send iisn as input.
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    public void testForBasedOnBinTokenRulesNullCheck() throws Exception {
        String userId = "tch-configuration";
        String password = "password";

        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer/tokenRules").param("iisn", iisn).param("bin", bin)
                                .session((MockHttpSession) session)).andExpect(okStatus).andReturn();
        String responseTokenData = res.getResponse().getContentAsString();
        TokenRuleLoadResponse responseTokenRules = new JsonObjectConverter<TokenRuleLoadResponse>().objectify(
                responseTokenData, TokenRuleLoadResponse.class);

        // Null Checks.
        TokenRule tokenRule = responseTokenRules.getTokenRule();
        Assert.assertNotNull(tokenRule.getDollarAmountForExpireDpi());
        Assert.assertNotNull(tokenRule.getDpiPerDay());
        Assert.assertNotNull(tokenRule.getNumberOfDetokenizedRequests());
        Assert.assertNotNull(tokenRule.getTimeInMinutesAfterExpirationForReuse());
        Assert.assertNotNull(tokenRule.getTokenExpirationMinutes());
        Assert.assertNotNull(tokenRule.getTokenExpireAfterSingleDollarAmountTransaction());
        Assert.assertNotNull(tokenRule.getTokenPullEnable());
        Assert.assertNotNull(tokenRule.getTokenPushOnCreation());

    }

    /**
     * Token rules will be saved in database.
     * 
     * @throws Exception
     *             - The exception.
     */
    @Test
    public void saveTokenRules() throws Exception {
        String userId = "tch-configuration";
        String password = "password";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc
                .perform(
                        get("/issuer/tokenRules").param("iisn", iisn).param("bin", bin)
                                .session((MockHttpSession) session)).andExpect(okStatus).andReturn();
        String responseTokenData = res.getResponse().getContentAsString();
        TokenRuleLoadResponse responseTokenRules = new JsonObjectConverter<TokenRuleLoadResponse>().objectify(
                responseTokenData, TokenRuleLoadResponse.class);

        TokenRule result = responseTokenRules.getTokenRule();
        // now saving logic is going.
        res = this.mockMvc
                .perform(
                        post("/issuer/tokenRules/saveTokenRules")
                                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("iisn", iisn)
                                .param("dollarAmountForExpireDpi", result.getDollarAmountForExpireDpi())
                                .param("dpiPerDay", result.getDpiPerDay())
                                .param("id", String.valueOf(result.getId()))
                                .param("numberOfDetokenizedRequests", result.getNumberOfDetokenizedRequests())
                                .param("realBin", result.getRealBin())
                                .param("timeInMinutesAfterExpirationForReuse",
                                        result.getTimeInMinutesAfterExpirationForReuse())
                                .param("tokenExpirationMinutes", result.getTokenExpirationMinutes())
                                .param("tokenExpireAfterSingleDollarAmountTransaction",
                                        result.getTokenExpireAfterSingleDollarAmountTransaction())
                                .param("tokenPullEnable", result.getTokenPullEnable())
                                .param("tokenPushOnCreation", result.getTokenPushOnCreation())
                                .session((MockHttpSession) session)).andReturn();
        String responseTokenData1 = res.getResponse().getContentAsString();
        Assert.assertNotNull(responseTokenData1);
    }

    /**
     * Token rules validations.
     * 
     * @throws Exception
     *             -
     */
    @Test
    public void tokenRulesValidations() throws Exception {
        String userId = "tch-configuration";
        String password = "password";
        HttpSession session = this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_FORM_URLENCODED).param(LOGIN_ID, userId)
                                .param(PASSWORD, password)).andExpect(okStatus)
                .andExpect(viewResultMatcher.name(V_HOME_PAGE)).andReturn().getRequest().getSession();

        MvcResult res = this.mockMvc.perform(
                post("/issuer/tokenRules/saveTokenRules").accept(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("iisn", iisn).param("dollarAmountForExpireDpi", "abc").param("dpiPerDay", "bcd")
                        .param("id", "1").param("numberOfDetokenizedRequests", "abc").param("realBin", "abb")
                        .param("timeInMinutesAfterExpirationForReuse", "adfd").param("tokenExpirationMinutes", "aaa")
                        .param("tokenExpireAfterSingleDollarAmountTransaction", "aaa").param("tokenPullEnable", "aaa")
                        .param("tokenPushOnCreation", "aaa").session((MockHttpSession) session)).andReturn();
        Assert.assertNotNull(res);

    }
}
