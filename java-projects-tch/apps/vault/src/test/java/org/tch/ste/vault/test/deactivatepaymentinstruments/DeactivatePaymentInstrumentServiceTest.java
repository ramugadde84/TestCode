/**
 * 
 */
package org.tch.ste.vault.test.deactivatepaymentinstruments;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.vault.constant.VaultControllerTest.C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentResponse;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.NonSecureIntegrationTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class DeactivatePaymentInstrumentServiceTest extends NonSecureIntegrationTest {

    private String requestJson1 = "{\"IISN\":\"123456\",\"ARN\":\"310\",\"TRID\":\"4567\"}";
    private String requestJson2 = "{\"IISN\":\"123456\",\"ARN\":\"315\",\"TRID\":\"4567\",\"CIID\":\"123455\"}";
    private String requestJson3 = "{\"IISN\":\"123456\",\"ARN\":\"315\",\"TRID\":\"4567\",\"CIID\":\"123456\"}";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<DeactivatePaymentInstrumentResponse> tokenConverter;

    /**
     * Method used to deactivate the payment instrument using test data with
     * ciid null
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void deactivatePaymentNullCiidTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJson1).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                DeactivatePaymentInstrumentResponse.class);
        assertEquals(response.getNumDeactivatedTokens(), 2);

        MvcResult res2 = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJson1).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response2 = tokenConverter.objectify(res2.getResponse()
                .getContentAsString(), DeactivatePaymentInstrumentResponse.class);
        assertEquals(response2.getNumDeactivatedTokens(), 0);
    }

    /**
     * Method used to deactivate the payment instrument using test data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void deactivatePaymentWithCiid() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJson2).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                DeactivatePaymentInstrumentResponse.class);
        assertEquals(response.getNumDeactivatedTokens(), 1);

    }

    /**
     * Method used to deactivate the payment instrument using test data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void deactivatePaymentWithDifferentToken() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJson3).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                DeactivatePaymentInstrumentResponse.class);
        assertEquals(response.getNumDeactivatedTokens(), 1);
    }
}