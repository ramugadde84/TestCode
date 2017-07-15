/**
 * 
 */
package org.tch.ste.admin.test.integration;

/**
 * Standard constants used in multiple tests.
 * 
 * @author Karthik.
 * 
 */
public interface AdminTestConstants {

    /**
     * Login Url.
     */
    static final String LOGIN_URL = "/login";

    /**
     * Issuer list URL
     */
    static final String ISSUER_LIST_URL = "/issuer/list";
    /**
     * User list URL
     */
    static final String USER_LIST_URL = "/user/usermgmt/list";
    /**
     * User Add URL
     */
    static final String USER_ADD_URL = "/user/add";
    /**
     * User Add URL
     */
    static final String USER_URL = "/user";

    /**
     * Token Requestor list URL
     */
    static final String TOKEN_REQUESTOR_LIST_URL = "/tokenRequestor/list";

    /**
     * Issuer list URL
     */
    static final String SAVE_BIN_MAPPING_DETAILS = "/issuer/binMapping/";
    /**
     * Token Requestor delete URL
     */
    static final String TOKEN_REQUESTOR_DELETE_URL = "/tokenRequestor/delete";
    /**
     * User delete URL
     */
    static final String USER_DELETE_URL = "/user/usermgmt/delete";

    /**
     * Reset password URL
     */
    static final String RESET_PASSWORD_URL = "/user/customer/resetPassword";

    /**
     * Lock the customer
     */
    static final String LOCK_URL = "/user/customer/lock";

    /**
     * Unlock the customer
     */
    static final String UNLOCK_URL = "/user/customer/unlock";
    /**
     * customer management URL
     */
    static final String CUSTOMER_MANAGEMENT_URL = "/user/customer";

    /**
     * customer management List URL
     */
    static final String CUSTOMER_MANAGEMENT_LIST_URL = "/user/customer/list";
}
