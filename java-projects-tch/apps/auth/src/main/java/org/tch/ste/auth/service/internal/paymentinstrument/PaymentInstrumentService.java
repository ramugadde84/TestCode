package org.tch.ste.auth.service.internal.paymentinstrument;

import org.tch.ste.auth.dto.PaymentInstrumentRequest;

/**
 * Payment Instrument Service to Activate and Deactivate the Payment
 * Instruments.
 * 
 * @author sharduls
 * 
 */
public interface PaymentInstrumentService {

	/**
	 * Save the Payment Instruments and Generate the Tokens for
	 * PaymentInstruments which are activated by the user.and disable those
	 * which are Deactivated.
	 * 
	 * @param paymentInstrumentRequest
	 *            - PaymentInstrumentRequest DTO.
	 * 
	 */
	public void savePaymentInstrument(
			PaymentInstrumentRequest paymentInstrumentRequest);

}
