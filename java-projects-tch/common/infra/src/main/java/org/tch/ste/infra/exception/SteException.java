package org.tch.ste.infra.exception;

/**
 * Base Exception Class.
 * 
 * @author sujathas
 * 
 */
public class SteException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -1729197306625860001L;

    /**
     * Constructor.
     * 
     * @param message
     *            String - The error code.
     */
    public SteException(String message) {
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
    public SteException(String message, Throwable t) {
        super(message, t);
    }
}
