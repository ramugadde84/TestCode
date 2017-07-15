/**
 * 
 */
package org.tch.ste.auth.service.internal.token;

import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Token Generation Life cycle Handler.
 * 
 * @author sharduls
 * 
 */
public interface TokenLifeCycleHandler {

	/**
	 * Create the tokens for Payment Instrument.
	 * 
	 * @param paymentInstrument
	 *            - the Payment Instrument.
	 */
	public void create(PaymentInstrument paymentInstrument);

	/**
	 * De-Activate the Payment Instrument.
	 * 
	 * @param paymentInstrument
	 *            - the Payment Instrument.
	 */
	public void deactivate(PaymentInstrument paymentInstrument);

}
