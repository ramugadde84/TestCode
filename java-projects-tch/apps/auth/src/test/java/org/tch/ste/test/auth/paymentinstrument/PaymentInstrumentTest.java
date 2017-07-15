/**
 * 
 */
package org.tch.ste.test.auth.paymentinstrument;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.auth.dto.AuthPaymentInstrument;
import org.tch.ste.auth.dto.LoginResponse;
import org.tch.ste.auth.service.internal.paymentinstrument.PaymentInstrumentFetchService;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.test.auth.base.BaseAuthTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;

/**
 * Payment Instrument Tests.
 * 
 * @author sharduls
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PaymentInstrumentTest extends BaseAuthTest {

	/**
	 * Injects the entity manager.
	 */
	@BeforeTransaction
	public void setEntityManagerThreadLocal() {
		emInjector.inject(getIisn());
	}

	@Autowired
	@Qualifier("jsonConverter")
	private ObjectConverter<LoginResponse> loginResponseConverter;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private VaultProperties vaultProperties;

	@Autowired
	private PaymentInstrumentFetchService paymentInstrumentFetchService;

	@Autowired
	@Qualifier("customerDao")
	private JpaDao<Customer, Integer> customerDao;

	private String LOGIN_USERNAME = "1234qwe1";
	private String LOGIN_USERNAME_FOR_EMPTY_PI = "1234qwe3";
	private String LOGIN_PASSWORD = "password1234";
	private String CIID = "123456";

	/**
	 * Unlock the User before Every Test.
	 */
	@Before
	public void setUp() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AuthQueryConstants.PARAM_USER_NAME, LOGIN_USERNAME);
		Customer customer = ListUtil.getFirstOrNull(customerDao.findByName(
				AuthQueryConstants.GET_CUSTOMER_BY_USER_NAME, params));
		customer.setAccountLocked(Boolean.FALSE);
		customerDao.save(customer);

	}

	/**
	 * Test to check whether Payment Instruments are listed Successfully for the
	 * Customer.
	 * 
	 * @throws Exception
	 *             - Exception Occured.
	 */
	@Test
	@Transactional
	public void testPaymentInstrumentList() throws Exception {

		MvcResult result = this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, LOGIN_USERNAME)
								.param(PASSWORD, LOGIN_PASSWORD)
								.param(IISN, getIisn()).param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.TRUE)).andReturn();

		MockHttpServletResponse respsonse = result.getResponse();
		assertTrue(respsonse.getContentType().startsWith(
				MediaType.APPLICATION_JSON_VALUE));
		LoginResponse response = loginResponseConverter.objectify(
				respsonse.getContentAsString(), LoginResponse.class);
		List<AuthPaymentInstrument> paymentInstrumentsActual = response
				.getPaymentInstruments();
		List<AuthPaymentInstrument> paymentInstrumentsExpected = paymentInstrumentFetchService
				.fetchPaymentInstrumentsForCustomer(LOGIN_USERNAME, trId, CIID);
		Assert.assertEquals(paymentInstrumentsActual.size(),
				paymentInstrumentsExpected.size());
		int i = 0;
		for (AuthPaymentInstrument authPaymentInstrument : paymentInstrumentsActual) {

			Assert.assertEquals(authPaymentInstrument.getId(),
					paymentInstrumentsExpected.get(i++).getId());

		}

	}

	/**
	 * Check Activation of PaymentInstruments With or Without CIID.
	 * 
	 * @throws Exception
	 *             - Exception
	 * 
	 */
	@Test
	@Transactional
	public void testActivatePaymentInstruments() throws Exception {

		String paymentInstrumentJsonWithOutCiid = "{\"iisn\": \"123456\",\"ciid\": null,\"trId\": \"123456\",\"activePaymentInstruments\": [\"4\",\"5\",\"6\"],\"inactivePaymentInstruments\": []}";
		MockRestServiceServer restServer = MockRestServiceServer
				.createServer(restTemplate);
		restServer
				.expect(requestTo(vaultProperties
						.getKey(AuthConstants.PAYMENT_INSTRUMENT_ACTIVATION_URL)))
				.andExpect(method(HttpMethod.POST))
				.andRespond(
						withSuccess("{\"ResponseCode\":\"0000\"}",
								MediaType.APPLICATION_JSON));
		// Test Activate Payment Instrument Without CIID.
		this.mockMvc
				.perform(
						post(AuthConstants.PAYMENT_INSTRUMENT_SERVICE).accept(
								MediaType.APPLICATION_JSON).content(
								paymentInstrumentJsonWithOutCiid))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.TRUE)).andReturn();
		restServer.verify();

	}

	/**
	 * Check De-Activation of PaymentInstruments.
	 * 
	 * @throws Exception
	 *             -Exception Occured.
	 */
	@Test
	@Transactional
	public void testDeActivatePaymentInstrument() throws Exception {

		String paymentInstrumentJsonwithOutCiid = "{\"iisn\": \"123456\",\"ciid\": null,\"trId\": \"123456\",\"activePaymentInstruments\": [\"6\"],\"inactivePaymentInstruments\": [\"4\",\"5\"]}";
		MockRestServiceServer restServer = MockRestServiceServer
				.createServer(restTemplate);
		restServer
				.expect(requestTo(vaultProperties
						.getKey(AuthConstants.PAYMENT_INSTRUMENT_DEACTIVATION_URL)))
				.andExpect(method(HttpMethod.POST))
				.andRespond(
						withSuccess("{\"ResponseCode\":\"0000\"}",
								MediaType.APPLICATION_JSON));

		// Test DeActivate Payment Instrument Without CIID.
		this.mockMvc
				.perform(
						post(AuthConstants.PAYMENT_INSTRUMENT_SERVICE).accept(
								MediaType.APPLICATION_JSON).content(
								paymentInstrumentJsonwithOutCiid))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.TRUE)).andReturn();

		restServer.verify();

	}

	/**
	 * Tests The Response With Invalid Json.
	 * 
	 * @throws Exception
	 *             - Exception Occurred will be Thrown.
	 */
	@Test
	@Transactional
	public void testBadJson() throws Exception {

		String paymentInstrumentJson = "{\"iisn\":: \"123456\",\"ciid\":: null,\"trId\":: \"123456\",\"activePaymentInstruments\": [\"4\",\"5\",\"6\"],\"inactivePaymentInstruments\": []}";

		this.mockMvc
				.perform(
						post(AuthConstants.PAYMENT_INSTRUMENT_SERVICE).accept(
								MediaType.APPLICATION_JSON).content(
								paymentInstrumentJson))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.FALSE)).andReturn();

	}

	/**
	 * Tests Failure while Activating Payment Instrument.
	 * 
	 * @throws Exception
	 *             - Exception Occured.
	 * 
	 */
	@Test
	@Transactional
	public void testActivationFailure() throws Exception {

		String paymentInstrumentJson = "{\"iisn\": \"123456\",\"ciid\": null,\"trId\": \"123456\",\"activePaymentInstruments\": [\"4\",\"5\",\"6\"],\"inactivePaymentInstruments\": []}";

		MockRestServiceServer restServer = MockRestServiceServer
				.createServer(restTemplate);
		restServer
				.expect(requestTo(vaultProperties
						.getKey(AuthConstants.PAYMENT_INSTRUMENT_ACTIVATION_URL)))
				.andExpect(method(HttpMethod.POST))
				.andRespond(
						withSuccess("{\"ResponseCode\":\"0001\"}",
								MediaType.APPLICATION_JSON));
		this.mockMvc
				.perform(
						post(AuthConstants.PAYMENT_INSTRUMENT_SERVICE).accept(
								MediaType.APPLICATION_JSON).content(
								paymentInstrumentJson))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.FALSE)).andReturn();

	}
	
	/**
	 * Tests Failure while Activating Payment Instrument.
	 * 
	 * @throws Exception
	 *             - Exception Occured.
	 * 
	 */
	@Test
	@Transactional
	@Ignore
	public void testEmptyPaymentInstrument() throws Exception {
		
		MvcResult result = this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, LOGIN_USERNAME_FOR_EMPTY_PI)
								.param(PASSWORD, LOGIN_PASSWORD)
								.param(IISN, getIisn()).param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success").value(
								Boolean.TRUE)).andReturn();
		MockHttpServletResponse respsonse = result.getResponse();
		assertTrue(respsonse.getContentType().startsWith(
				MediaType.APPLICATION_JSON_VALUE));
		LoginResponse response = loginResponseConverter.objectify(
				respsonse.getContentAsString(), LoginResponse.class);		
		List<AuthPaymentInstrument> paymentInstruments = response
				.getPaymentInstruments();
		Assert.assertNull(paymentInstruments);
		
		
		
		
	}
	
}