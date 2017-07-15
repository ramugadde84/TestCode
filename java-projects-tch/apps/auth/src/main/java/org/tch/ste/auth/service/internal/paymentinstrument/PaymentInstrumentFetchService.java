/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import java.util.List;

import org.tch.ste.auth.dto.AuthPaymentInstrument;
import org.tch.ste.infra.exception.SteException;

/**
 * Exposes methods to fetch payment instruments via various methodologies.
 * 
 * @author Karthik.
 * 
 */
public interface PaymentInstrumentFetchService {

	/**
	 * Fetches the payment instruments for the given customer.
	 * 
	 * @param userId
	 *            String - The user id.
	 * @param trid
	 *            String - The Token Requestor Id.
	 * @param ciid
	 *            String - The CIID.
	 * @return List<AuthPaymentInstrument> - The list of payment instruments.
	 * @throws SteException Thrown.
	 */
	List<AuthPaymentInstrument> fetchPaymentInstrumentsForCustomer(
			String userId, String trid, String ciid) throws SteException;

}
