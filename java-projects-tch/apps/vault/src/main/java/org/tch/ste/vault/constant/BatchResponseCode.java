/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * Response code for Payment Instrument Preloading.
 * 
 * @author Karthik.
 * 
 */
public enum BatchResponseCode {
    /**
     * 
     */
    SUCCCESS("TMV000"),
    /**
     * 
     */
    IISN_MISMATCH("TMV001"),
    /**
     * 
     */
    FILE_TYPE_INDICATOR_MISMATCH("TMV002"),
    /**
     * 
     */
    MISSING_MANDATORY_FIELD("TMV003"),
    /**
     * 
     */
    INPUT_VALUE_TYPE_MISMATCH("TMV004"),
    /**
     * 
     */
    TRAILER_RECORD_COUNT_MISMATCH("TMV005"),
    /**
     * 
     */
    RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR("TMV006"),
    /**
     * 
     */
    TRAILER_RECORD_MISSING("TMV007"),
    /**
     * 
     */
    DUPLICATE_RECORD_IN_FILE("TMV008"),
    /**
     * 
     */
    USER_ACCOUNT_NOT_FOUND("TMV100"),
    /**
     * 
     */
    UNABLE_TO_GENERATE_UNIQUE_USERNAME("TMV101"),
    /**
     * 
     */
    UNEXPECTED_ERROR_DURING_USERNAME_GENERATION("TMV149"),
    /**
     * 
     */
    UNSUPPORTED_AUTHENTICATION_OPTION("TMV197"),
    /**
     * During encryption.
     */
    UNEXPECTED_ERROR_DURING_PROCESSING("TMV198"),
    /**
     * 
     */
    UNEXPECTED_ERROR_DURING_PASSWORD_GENERATION("TMV199"),
    /**
     * 
     */
    UNABLE_TO_GENERATE_ARN("TMV200");

    private String responseStr;

    /**
     * Constructor.
     * 
     * @param responseStr
     *            String - The parameter.
     */
    private BatchResponseCode(String responseStr) {
        this.responseStr = responseStr;
    }

    /**
     * Returns the response string.
     * 
     * @return String - The response string.
     */
    public String getResponseStr() {
        return this.responseStr;
    }

}
