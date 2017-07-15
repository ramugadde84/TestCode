/**
 * 
 */
package org.tch.ste.auth.exception;

/**
 * Payment Instrument Save Exception.
 * 
 * @author sharduls
 * 
 */
public class PaymentInstrumentSaveException extends RuntimeException {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4710475088427767678L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            String - The error code.
	 */
	public PaymentInstrumentSaveException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            String - The error code.
	 * @param t
	 *            Throwable - The cause.
	 */
	public PaymentInstrumentSaveException(String message, Throwable t) {
		super(message, t);
	}
}
