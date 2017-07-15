package org.tch.ste.test;

/**
 * Runtime Exception for test classes
 * 
 * @author: Ganeshji Marwaha
 * @since: 4/22/2014
 */
public class TestRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5213138231643488963L;

    /**
     * default constructor.
     */
    public TestRuntimeException() {
        super();
    }

    /**
     * Constructor which accepts messages.
     * 
     * @param message
     *            accepts message as parameter.
     */
    public TestRuntimeException(final String message) {
        super(message);
    }

    /**
     * constructor which accepts message and error cause.
     * 
     * @param message
     *            accepts parameter as message.
     * @param cause
     *            accepts cause as parameter.
     */
    public TestRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor which accepts error cause.
     * 
     * @param cause
     *            accepts cause as parameter.
     */
    public TestRuntimeException(final Throwable cause) {
        super(cause);
    }
}
