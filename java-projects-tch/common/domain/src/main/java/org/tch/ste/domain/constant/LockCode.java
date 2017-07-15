/**
 * 
 */
package org.tch.ste.domain.constant;

/**
 * Enum for locked code.
 * 
 * @author Karthik.
 * 
 */
public enum LockCode {
    /**
     * Unlocked.
     */
    UNLOCKED("0"),
    /**
     * Failed.
     */
    FAILED_AUTH_ATTEMPT("1"),
    /**
     * Successful Auth Attempt.
     */
    SUCCESSFUL_AUTH("2"),
    /**
     * Locked Manually.
     */
    MANUAL_LOCK("3"),
    /**
     * Locked Manually.
     */
    LOCKED_BY_BATCH("5"),
    /**
     * Other cause.
     */
    OTHER("9");

    private String code;

    private LockCode(String code) {
        this.code = code;
    }

    /**
     * Returns the field code.
     * 
     * @return code String - Get the field.
     */
    public String getCode() {
        return code;
    }

}
