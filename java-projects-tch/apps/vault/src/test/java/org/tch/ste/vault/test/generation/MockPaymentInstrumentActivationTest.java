/**
 * 
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
import org.tch.ste.domain.dto.ActivatePaymentResponse;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.NonSecureIntegrationTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * @author kjanani
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class MockPaymentInstrumentActivationTest extends NonSecureIntegrationTest {

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<ActivatePaymentResponse> paymentInstrumentActivation;

    private String responseCode = "0000";
    private String panDigits = "345345";
    private String maskedDigits = "XXXXXX";

    private String inputJson = "{\"IISN\":\"123456\",\"TRID\":\"1234\",\"ARN\":\"220\",\"CIID\":\"23789\"}";

    /**
     * This method is used to test the mock Payment instrument activation
     * controller - 16 digit token
     * 
     * @throws Exception
     *             -
     */
    @SuppressWarnings("null")
    @Test
    public void testMockTokenCreationController() throws Exception {

        MvcResult res = this.mockMvc.perform(
                post("/paymentinstrument/activation").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ActivatePaymentResponse response = paymentInstrumentActivation.objectify(
                res.getResponse().getContentAsString(), ActivatePaymentResponse.class);

        assertEquals(responseCode, response.getResponseCode());

        String maskedToken = response.getMaskedToken();
        assertTrue(maskedToken != null);
        assertTrue(maskedToken.length() == 16);
        assertEquals(panDigits, maskedToken.substring(0, 6));
        assertEquals(maskedDigits, maskedToken.substring(6, 12));

    }

}
