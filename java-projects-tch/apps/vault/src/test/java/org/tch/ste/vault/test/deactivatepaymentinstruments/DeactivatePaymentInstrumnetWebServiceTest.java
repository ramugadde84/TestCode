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
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.NonSecureIntegrationTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.domain.dto.DeactivatePaymentInstrumentResponse;

/**
 * @author pamartheepan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class DeactivatePaymentInstrumnetWebServiceTest extends NonSecureIntegrationTest{
    private String requestJson = "{\"IISN\":\"123456\",\"ARN\":\"100\",\"TRID\":\"1234\",\"CIID\":\"12378\"}";
    private String requestJsonInValidData = "{\"IISN\":\"123456\",\"ARN\":\"10\",\"TRID\":\"1234\",\"CIID\":\"12378\"}";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<DeactivatePaymentInstrumentResponse> tokenConverter;

    /**
     * Method used to deactivate the payment instrument using test data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void deactivatePaymentInstrumentTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJson).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                DeactivatePaymentInstrumentResponse.class);
        assertEquals(response.getNumDeactivatedTokens(), 1);
    }

    /**
     * Method used to deactivate the payment instrument using test invalid data
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void deactivatePaymentInstrumentTestInValidDataTest() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_DEACTIVATE_PAYMENT_INSTRUMENT_APP_URL).content(requestJsonInValidData).contentType(
                        MediaType.APPLICATION_JSON)).andReturn();
        DeactivatePaymentInstrumentResponse response = tokenConverter.objectify(res.getResponse().getContentAsString(),
                DeactivatePaymentInstrumentResponse.class);
        assertEquals(response.getNumDeactivatedTokens(), 0);
    }

}
