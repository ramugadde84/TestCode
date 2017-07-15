/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.exception.PaymentInstrumentSaveException;
import org.tch.ste.auth.integration.paymentinstrument.PaymentInstrumentManagementService;
import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Payment Instrument Life cycle Handler Implementation which calls the Web
 * Service in Vault.
 * 
 * @author sharduls
 * 
 */
@Service
public class PaymentInstrumentLifeCycleHandlerImpl implements
		PaymentInstrumentLifeCycleHandler {

	@Autowired
	private PaymentInstrumentManagementService paymentInstrumentManagementService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.internal.token.TokenLifeCycleHandler#create(
	 * org.tch.ste.domain.entity.PaymentInstrument)
	 */
	@Override
	public void activate(PaymentInstrument paymentInstrument, String ciid,
			String trId, String iisn) {

		if (!paymentInstrumentManagementService.activate(paymentInstrument,
				ciid, trId, iisn)) {
			throw new PaymentInstrumentSaveException(
					"Error Activating the Payment Instrument "
							+ paymentInstrument);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tch.ste.auth.service.internal.token.TokenLifeCycleHandler#deactivate
	 * (org.tch.ste.domain.entity.PaymentInstrument)
	 */
	@Override
	public void deactivate(PaymentInstrument paymentInstrument, String ciid,
			String trId, String iisn) {
		if (!paymentInstrumentManagementService.deactivate(paymentInstrument,
				ciid, trId, iisn)) {
			throw new PaymentInstrumentSaveException(
					"Error De-Activating the Payment Instrument "
							+ paymentInstrument);
		}
	}

}
