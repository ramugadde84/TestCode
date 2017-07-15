package org.tch.ste.auth.integration.paymentinstrument;

import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Service to manage activation and deactivation of payment instruments.
 * Consume the REST service published by vault application. 
 * 
 * @author janarthanans
 *
 */
public interface PaymentInstrumentManagementService {

	/**
	 * Activate the specified payment instrument.
	 * 
	 * @param paymentInstrument - The payment instrument to be activated.
	 * @param ciid - Customer instance Id
	 * @param tokenRequestorId - Token requestor unique Id
	 * @param iisn - Issuer unique Id
	 * @return boolean - True if the activation was successful, false otherwise.
	 */
	public boolean activate(PaymentInstrument paymentInstrument, String ciid, String tokenRequestorId, String iisn);
	
	
	/**
	 * Deactivate the specified payment instrument.
	 * 
	 * @param paymentInstrument - The payment instrument to be deactivated.
	 * @param ciid - Customer instance Id
	 * @param tokenRequestorId - Token requestor unique Id
	 * @param iisn - Issuer unique Id
	 * @return boolean - True if the de-activation was successful, false otherwise.
	 */
	public boolean deactivate(PaymentInstrument paymentInstrument, String ciid, String tokenRequestorId, String iisn);
}
