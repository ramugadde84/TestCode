/**
 * 
 */
package org.tch.ste.auth.exception;

/**
 * Thrown when no Payment Instruments are found for the Customer.
 * 
 * @author sharduls
 * 
 */
public class EmptyPaymentInstrumentException extends RuntimeException {
	/**
	 * Default Serial Version UID.
	 * 
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -4710475088427767678L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            String - The error code.
	 */
	public EmptyPaymentInstrumentException(String message) {
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
	public EmptyPaymentInstrumentException(String message, Throwable t) {
		super(message, t);
	}
}