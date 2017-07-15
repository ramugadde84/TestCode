/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Payment Instrument Life cycle Handler.
 * 
 * @author sharduls
 * 
 */
public interface PaymentInstrumentLifeCycleHandler {

	/**
	 * Activate the Payment Instrument.
	 * 
	 * @param paymentInstrument
	 *            - the Payment Instrument.
	 * @param ciid
	 *            String - The ciid.
	 * @param trId
	 *            String - The Token Requestor Id.
	 * @param iisn
	 *            String - The IISN Value.
	 */
	public void activate(PaymentInstrument paymentInstrument, String ciid,
			String trId, String iisn);

	/**
	 * Deactivate the Payment Instrument.
	 * 
	 * @param paymentInstrument
	 *            - the Payment Instrument.
	 * @param ciid
	 *            String - The ciid.
	 * @param trId
	 *            String - The Token Requestor Id.
	 * @param iisn
	 *            String - The IISN Value.
	 */
	public void deactivate(PaymentInstrument paymentInstrument, String ciid,
			String trId, String iisn);

}
