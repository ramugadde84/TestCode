package org.tch.ste.infra.constant;

/**
 * 
 * 
 * @author janarthanans
 * 
 */
public enum LogLevel {

    /**
     * Used to turn on all levels of logging.
     */
    ALL,
    /**
     * Most detailed information. Expect these to be written to logs only.
     */
    TRACE,
    /**
     * Detailed information on the flow through the system. Expect these to be
     * written to logs only.
     */
    DEBUG,
    /**
     * Interesting runtime events (startup/shutdown). Expect these to be
     * immediately visible on a console, so be conservative and keep to a
     * minimum.
     */
    INFO,
    /**
     * Use of deprecated APIs, poor use of API, 'almost' errors, other runtime
     * situations that are undesirable or unexpected, but not necessarily
     * "wrong". Expect these to be immediately visible on a status console
     */
    WARN,
    /**
     * Other runtime errors or unexpected conditions. Expect these to be
     * immediately visible on a status console.
     */
    ERROR,
    /**
     * Severe errors that cause premature termination. Expect these to be
     * immediately visible on a status console.
     */
    FATAL

}
