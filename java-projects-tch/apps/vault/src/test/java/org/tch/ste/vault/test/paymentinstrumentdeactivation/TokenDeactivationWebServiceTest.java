/**
 * 
 */
package org.tch.ste.vault.test.paymentinstrumentdeactivation;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.vault.constant.VaultControllerTest.C_TOKEN_PAYMENTDEACTIVATION_APP_URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.NonSecureIntegrationTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.domain.dto.TokenDeactivationResponse;

/**
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class TokenDeactivationWebServiceTest extends NonSecureIntegrationTest {
    private String requestJsonByArn = "{\"IISN\":\"123456\",\"ARN\":\"215\"}";
    private String requestJsonByArnInValidData = "{\"IISN\":\"12346\",\"ARN\":\"215\"}";
    private String requestJsonByPanAndDate = "{\"IISN\":\"123456\",\"PAN\":\"234\",\"DATE\":\"2017\"}";
    private String requestJsonByPanAndDateInValidData = "{\"IISN\":\"12356\",\"PAN\":\"234\",\"DATE\":\"201\"}";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<TokenDeactivationResponse> tokenConverter;

    /**
     * Method used to test token deactivation made by arn with valid data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void tokenDeactivationByArnTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_TOKEN_PAYMENTDEACTIVATION_APP_URL).content(requestJsonByArn).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        TokenDeactivationResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                TokenDeactivationResponse.class);
        assertEquals(response.getTotalNoOfTokensDeactivated(), 1);
    }

    /**
     * Method used to test token deactivation made by arn with invalid data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void tokenDeactivationByArnInValidDataTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_TOKEN_PAYMENTDEACTIVATION_APP_URL).content(requestJsonByArnInValidData).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        TokenDeactivationResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                TokenDeactivationResponse.class);
        assertEquals(response.getTotalNoOfTokensDeactivated(), 0);
    }

    /**
     * Method used to test token deactivation made by Pan and Date with valid
     * data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void tokenDeactivationByPanAndDateTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_TOKEN_PAYMENTDEACTIVATION_APP_URL).content(requestJsonByPanAndDate).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        TokenDeactivationResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                TokenDeactivationResponse.class);
        assertEquals(response.getTotalNoOfTokensDeactivated(), 1);
    }

    /**
     * Method used to test token deactivation made by Pan and Date with invalid
     * data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void tokenDeactivationByPanAndDateInValidDataTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_TOKEN_PAYMENTDEACTIVATION_APP_URL).content(requestJsonByPanAndDateInValidData).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        TokenDeactivationResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                TokenDeactivationResponse.class);
        assertEquals(response.getTotalNoOfTokensDeactivated(), 0);
    }
}
