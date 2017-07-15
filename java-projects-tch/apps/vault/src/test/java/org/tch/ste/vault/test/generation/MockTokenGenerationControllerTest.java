/**
 * Test class for Mock token generation controller class
 */
package org.tch.ste.vault.test.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.tch.ste.vault.domain.dto.MockTokenCreationResponse;

/**
 * @author kjanani
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class MockTokenGenerationControllerTest extends NonSecureIntegrationTest {

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<MockTokenCreationResponse> tokenCreation;
    private String ARN_ID = "100";
    private String ARN_19 = "105";
    private String IISN = "123456";
    private String TokenRequestorId = "1234";

    private String inputJson = "{\"IISN\":\"123456\",\"TRID\":\"1234\",\"ARNID\":\"100\"}";
    private String inputJson19 = "{\"IISN\":\"123456\",\"TRID\":\"1234\",\"ARNID\":\"105\"}";
    private String inputJsonInactiveArn = "{\"IISN\":\"123456\",\"TRID\":\"1234\",\"ARNID\":\"200\"}";
    private String inputJsonInvalidArn = "{\"IISN\":\"123456\",\"TRID\":\"1234\",\"ARNID\":\"17956\"}";
    private String inputJsonInvalidTrId = "{\"IISN\":\"123456\",\"TRID\":\"7189\",\"ARNID\":\"200\"}";

    /**
     * This method is used to test the mock token creation controller - 16 digit
     * token
     * 
     * @throws Exception
     *             -
     */
    @Test
    public void testMockTokenCreationController() throws Exception {

        MvcResult res = this.mockMvc.perform(
                post("/MockTokenCreationService").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockTokenCreationResponse response = tokenCreation.objectify(res.getResponse().getContentAsString(),
                MockTokenCreationResponse.class);

        assertEquals(ARN_ID, response.getArnId());
        assertEquals(IISN, response.getIisn());
        assertEquals(TokenRequestorId, response.getTrId());
        assertTrue(response.getToken().length() == 16);

    }

    /**
     * This method is used to test the mock token creation controller - 19 digit
     * token
     * 
     * @throws Exception
     *             -
     */
    @Test
    public void testMockTokenCreationControllerForNineteen() throws Exception {

        MvcResult res = this.mockMvc.perform(
                post("/MockTokenCreationService").content(inputJson19).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockTokenCreationResponse response = tokenCreation.objectify(res.getResponse().getContentAsString(),
                MockTokenCreationResponse.class);

        assertEquals(ARN_19, response.getArnId());
        assertEquals(IISN, response.getIisn());
        assertEquals(TokenRequestorId, response.getTrId());
        assertTrue(response.getToken().length() == 19);

    }

    /**
     * This method is used to test the negative scenario where the payment
     * instrument is inactive
     * 
     * @throws Exception
     *             -
     */
    @Test
    public void testTokenGenerationForInactivePaymentInstrument() throws Exception {

        MvcResult res = this.mockMvc.perform(
                post("/MockTokenCreationService").content(inputJsonInactiveArn).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();

        MockTokenCreationResponse response = tokenCreation.objectify(res.getResponse().getContentAsString(),
                MockTokenCreationResponse.class);

        assertEquals("ERR002", response.getErrorlist().getErrorCode());

    }
    
    /**
     * Test for Invalid ARN/Token Requestor Id.
     * 
     * @throws Exception Thrown.
     */
    @Test
    public void testForInvalidData() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post("/MockTokenCreationService").content(inputJsonInvalidArn).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();

        MockTokenCreationResponse response = tokenCreation.objectify(res.getResponse().getContentAsString(),
                MockTokenCreationResponse.class);

        assertEquals("ERR002", response.getErrorlist().getErrorCode());
        
        res = this.mockMvc.perform(
                post("/MockTokenCreationService").content(inputJsonInvalidTrId).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();

        response = tokenCreation.objectify(res.getResponse().getContentAsString(),
                MockTokenCreationResponse.class);

        assertEquals("ERR002", response.getErrorlist().getErrorCode());
    }

}
