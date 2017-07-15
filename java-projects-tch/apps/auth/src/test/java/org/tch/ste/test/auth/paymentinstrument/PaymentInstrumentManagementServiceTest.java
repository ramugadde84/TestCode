package org.tch.ste.test.auth.paymentinstrument;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.integration.paymentinstrument.PaymentInstrumentManagementService;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.test.auth.base.BaseAuthTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * Test Payment instrument management services
 * 
 * @author janarthanans
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PaymentInstrumentManagementServiceTest extends BaseAuthTest {

    @Autowired
    private PaymentInstrumentManagementService paymentInstrumentManagementService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private VaultProperties vaultProperties;

    /**
     * Sets the entity manager.
     */
    @BeforeTransaction
    public void setEntityManager() {
        emInjector.inject(iisn);
    }

    /**
     * Tests the payment instrument activation.
     */
    @Test
    @Transactional
    public void testPaymentInstrumentActivation() {
        MockRestServiceServer restServer = MockRestServiceServer.createServer(restTemplate);
        restServer.expect(requestTo(vaultProperties.getKey(AuthConstants.PAYMENT_INSTRUMENT_ACTIVATION_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("{\"ResponseCode\":\"0000\"}", MediaType.APPLICATION_JSON));
        PaymentInstrument paymentInstrument = createPaymentInstrument("123456712345612");
        String ciid = "123456";
        String tokenRequestorId = "123456";
        boolean activated = paymentInstrumentManagementService
                .activate(paymentInstrument, ciid, tokenRequestorId, IISN);
        assertTrue(activated);

        restServer.verify();
    }

    /**
     * Test for deactivation.
     */
    @Test
    @Transactional
    public void testPaymentInstrumentDeActivation() {
        MockRestServiceServer restServer = MockRestServiceServer.createServer(restTemplate);
        restServer
                .expect(requestTo(vaultProperties.getKey(AuthConstants.PAYMENT_INSTRUMENT_DEACTIVATION_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                        withSuccess(
                                "{\"ResponseCode\":\"0000\",\"IISN\":\"123456\",\"TRID\":\"123456\",\"CIID\":\"123456\",\"NumberOfDeactivatedTokens\":3}",
                                MediaType.APPLICATION_JSON));
        PaymentInstrument paymentInstrument = createPaymentInstrument("123456712345612");
        String ciid = "123456";
        String tokenRequestorId = "123456";
        boolean deactivated = paymentInstrumentManagementService.deactivate(paymentInstrument, ciid, tokenRequestorId,
                IISN);
        assertTrue(deactivated);

        restServer.verify();
    }

    /**
     * Creates the payment instrument.
     * 
     * @param arn
     *            String - The ARN.
     * @return PaymentInstrument - The payment instrument.
     */
    private static PaymentInstrument createPaymentInstrument(String arn) {
        PaymentInstrument retVal = new PaymentInstrument();
        Arn myArn = new Arn();
        myArn.setArn(arn);
        retVal.setArn(myArn);
        return retVal;
    }
}
