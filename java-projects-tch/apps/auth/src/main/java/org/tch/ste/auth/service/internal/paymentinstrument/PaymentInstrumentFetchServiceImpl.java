/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.auth.dto.AuthPaymentInstrument;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.security.EncryptionService;
import org.tch.ste.infra.util.StringUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class PaymentInstrumentFetchServiceImpl implements
		PaymentInstrumentFetchService {

	@Autowired
	@Qualifier("paymentInstrumentDao")
	private JpaDao<PaymentInstrument, Integer> paymentInstrumentDao;

	@Autowired
	private EncryptionService encryptionService;

	@Autowired
	private PaymentInstrumentChangeService paymentInstrumentChangeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tch.ste.auth.service.internal.paymentinstrument.
	 * PaymentInstrumentFetchService
	 * #fetchPaymentInstrumentsForCustomer(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<AuthPaymentInstrument> fetchPaymentInstrumentsForCustomer(
			String userId, String trid, String ciid) throws SteException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(AuthQueryConstants.PARAM_USER_ID, userId);
		return convertToDto(
				paymentInstrumentDao.findByName(
						AuthQueryConstants.FIND_PAYMENT_INSTRUMENTS_BY_CUSTOMER,
						params), trid, ciid);
	}

	/**
	 * Converts to the DTO.
	 * 
	 * @param paymentInstruments
	 *            List<PaymentInstrument> - the payment instruments.
	 * @return List<AuthPaymentInstrument> - The converted values.
	 * @throws Exception
	 *             Thrown.
	 */
	private List<AuthPaymentInstrument> convertToDto(
			List<PaymentInstrument> paymentInstruments, String trid, String ciid)
			throws SteException {
		List<AuthPaymentInstrument> retVal = new ArrayList<AuthPaymentInstrument>();
		Map<String, AuthPaymentInstrument> nickNames = new HashMap<String, AuthPaymentInstrument>();
		for (PaymentInstrument paymentInstrument : paymentInstruments) {
			AuthPaymentInstrument authPaymentInstrument = convertToDto(
					paymentInstrument, trid, ciid);
			String nickName = authPaymentInstrument.getNickName();
			if (!StringUtil.isNullOrBlank(nickName)) {
				AuthPaymentInstrument existingPaymentInstrument=nickNames.get(nickName);
				if (existingPaymentInstrument!=null) {
					authPaymentInstrument.setNickName(String.format("%s - %s",
							nickName, authPaymentInstrument.getPanLastFour()));
					existingPaymentInstrument.setNickName(String.format("%s - %s",
							nickName, existingPaymentInstrument.getPanLastFour()));
				} else {
					nickNames.put(nickName, authPaymentInstrument);
				}
			}
			retVal.add(authPaymentInstrument);
		}
		return retVal;
	}

	/**
	 * Converts a single payment instrument to the DTO.
	 * 
	 * @param paymentInstrument
	 *            PaymentInstrument- The PI.
	 * @return AuthPaymentInstrument- The DTO.
	 * @throws Exception
	 *             Thrown.
	 */
	private AuthPaymentInstrument convertToDto(
			PaymentInstrument paymentInstrument, String trid, String ciid)
			throws SteException {
		AuthPaymentInstrument retVal = new AuthPaymentInstrument();
		retVal.setActive(paymentInstrument.isActive() ? 
		        paymentInstrumentChangeService.isPaymentInstrumentActive(
						paymentInstrument.getArn().getId(), trid, ciid, true):false);
		retVal.setPanLastFour(encryptionService.decrypt(paymentInstrument
				.getPanLastFourDigits()));
		retVal.setExpMonthYear(paymentInstrument.getExpiryMonthYear());
		retVal.setId(paymentInstrument.getId());
		retVal.setNickName(paymentInstrument.getNickName());
		return retVal;
	}
}
