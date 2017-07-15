package org.tch.ste.admin.constant;

/**
 * Enum for errors.
 * 
 * @author sujathas
 * 
 */
public enum AuthenticationOptionError {
    /**
     * Invalid Failed attempts.
     */
    INVALID_FAILED_ATTEMPTS_TO_LOCKOUT,
    /**
     * Invalid Auth Endpoint.
     */
    INVALID_AUTH_ENDPOINT,
    /**
     * Invalid Account List Endpoint.
     */
    INVALID_ACCOUNT_LIST_ENDPOINT,

    /**
     * Invalid file type for Issuer Logo
     */
    INVALID_ISSUER_LOGO_TYPE,

    /**
     * Invalid size for Issuer Logo
     */
    INVALID_ISSUER_LOGO_SIZE,
    /**
     * Invalid Auth app url.
     */
    INVALID_AUTH_APP_URL;

}
