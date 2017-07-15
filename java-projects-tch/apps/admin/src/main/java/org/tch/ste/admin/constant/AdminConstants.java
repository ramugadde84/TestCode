/**
 * 
 */
package org.tch.ste.admin.constant;

/**
 * @author Karthik.
 * 
 */
public interface AdminConstants {

    /**
     * Login Error.
     */
    String LOGIN_ERROR = "loginError";

    /**
     * The error code for login error.
     */
    String LOGIN_ERR_MSG = "LOGIN_ERROR";

    /**
     * The message code for Log Out.
     */
    String LOGOUT_MSG_CODE = "LOGOUT_MSG";

    /**
     * The message to be displayed for Log Out.
     */
    String LOGOUT_MSG = "logoutMessage";

    /**
     * The message to be displayed for welcome, from vault properties
     */
    String WELCOME_MSG = "welcomeMessage";

    // Query related.
    /**
     * Search using the name from the test issuer.
     */
    String QUERY_FIND_BY_NAME = "searchByName";

    /**
     * Parameter called name.
     */
    String PARAM_NAME = "name";

    /**
     * Query to get all users
     */
    String GET_ALL_USERS = "getAllUsers";

    /**
     * Query name.
     */
    String GET_USER_WITH_USER_ROLES = "getUserWithUserRoles";

    /**
     * Query to update the login time of a user.
     */
    String UPDATE_LOGIN_TIME = "updateLoginTime";

    /**
     * The user id used in the above query.
     */
    String USER_ID = "userId";

    /**
     * The login time used in the above query.
     */
    String LOGIN_TIME = "loginTime";

    /**
     * Fetches the user using the provided user id.
     */
    String GET_USER_BY_USER_ID = "getUserByUserId";

    /**
     * The user id.
     */
    String LOGIN_ID = "loginId";
    /**
     * The password.
     */
    String PASSWORD = "password";

    /**
     * The UserRoles.
     */
    String USER_ROLES = "authorizedRoles";
    /**
     * Last Logged in time.
     */
    String LAST_LOGIN_TIME = "lastLoginTime";

    /**
     * The standard date format.
     */
    String STD_DATE_FMT = "MM/dd/yyyy";

    /**
     * Query to fetch the roles for a given user.
     */
    String GET_ROLES_FOR_USER = "getRolesForUser";

    /**
     * Query to fetch the roles for a given user.
     */
    String GET_ISSUERS_TOKEN_REQUESTERS = "getIssuersTokenRequestors";

    /**
     * Allowed logging levels.
     */
    String[] PERMITTED_LOGGING_LEVELS = new String[] { "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL" };

    /**
     * Query to fetch the token requestors details.
     */
    String GET_TOKEN_REQUESTORS = "getTokenRequestors";
    /**
     * The token requestor network Id
     */
    String NETWORK_TR_ID = "networkId";
    /**
     * Query to update the token requestors.
     */
    String UPDATE_TOKEN_REQUESTORS = "updateTokenRequestors";

    /**
     * Allowed authentication mechanisms.
     */
    String[] AUTHENTICATION_MECHANISMS = new String[] { "Issuer-Hosted w/Real-Time Payment Instrument Provisioning",
            "Issuer-Hosted w/Pre-Loaded Payment Instruments", "TCH-Hosted w/Issuer-Provided Credentials",
            "TCH-Hosted w/Generated Credentials", "TCH-Hosted w/Issuer Auth Web Service" };
    /**
     * Query to fetch list of pan bins.
     */
    String GET_LIST_OF_PAN_BINS = "getListOfPanBins";

    /**
     * iisn value.
     */
    String iisn = "iisn";

    /**
     * Query to fetch issuers by name.
     */
    String GET_ISSUERS_BY_NAME = "getIssuersByName";

    /**
     * Name parameter to above query.
     */
    String ISSUER_NAME = "name";

    /**
     * Iisn parameter to query.
     */
    String ISSUER_IISN = "iisn";

    /**
     * Query to fetch issuers by name.
     */
    String GET_ISSUERS_BY_IISN = "getIssuersByIisn";

    /**
     * Query to fetch token requestors by network TR Id.
     */
    String GET_TOKEN_REQUESTOR_BASED_ON_NETWORK_ID = "getTokenRequestorByNetworkTrId";
    /**
     * Query to fetch token requestors by network TR Id.
     */
    String GET_TOKEN_REQUESTOR_BASED_ON_NAME = "getTokenRequestorByName";

    /**
     * Bin.
     */
    String PARAM_BIN = "bin";

    /**
     * 
     */
    String PARAM_NAME_ISSUER_ID = "id";

    /**
     * Fetches the issuer using the id.
     */
    String GET_ISSUER_BY_ID = "getIssuerById";

    /**
     * Deletes the existing issuer token requestors.
     */
    String DELETE_EXISTING_ISSUER_TOKEN_REQUESTORS = "deleteExistingIssuerTokenRequestors";

    /**
     * Deletes the existing token requestors.
     */
    String DELETE_ISSUER_TOKEN_REQUESTORS = "deleteIssuerTokenRequestors";

    /**
     * The Bins which are fetched by based on IISN.
     */
    String GET_IISN_BIN = "iisnBin";

    /**
     * Get List of Bins by based on IISN.
     */
    String GET_LIST_OF_BINS = "getListOfBins";
    /**
     * Get Pan bins.
     */
    String GET_PAN_BINS = "getPanBins";
    /**
     * Query to fetch given bin will exist in iisn table.
     */
    String GET_REAL_BIN_BASED_ON_GIVEN_BIN = "getRealBin";

    /**
     * Parameter called first digit of real bin.
     */
    String PARAM_NAME_FIRST_DIGIT = "firstDigit";

    /**
     * Query to fetch first digit in network.
     */
    String GET_FIRST_DIGIT_ON_NETWORK = "getBinFirstDigit";
    /**
     * Parameter called real bin.
     */
    String PARAM_REAL_BIN = "realBin";
    /**
     * Parameter called token bin.
     */
    String PARAM_TOKEN_BIN = "tokenBin";

    /**
     * Query to fetch Existing bins.
     */
    String GET_EXISTING_BINS = "getExistingBins";

    /**
     * Bin Type.
     */
    String PARAM_BIN_TYPE = "binType";

    /**
     * Get customer Details
     */
    String GET_CUSTOMER_DTLS = "getCustomerDtls";

    /**
     * Query to fetch token bins.
     */
    String GET_EXISTING_TOKEN_BINS = "getExistingTokenBins";

    /**
     * Query to fetch issuers sorted by name.
     */
    String GET_ISSUERS_SORTED_BY_NAME = "getIssuersSortedByName";

    /**
     * Iisn parameter.
     */
    String PARAM_ISSUER_IISN = "iisn";

    /**
     * Id parameter.
     */
    String PARAM_ID = "id";

    /**
     * Delete roles.
     */
    String DELETE_EXISTING_USER_ROLES = "deleteExistingUserRoles";
    /**
     * Indicates Account locked Manually
     */
    String ACCOUNT_LOCKED_REASON_CODE = "3";
    /**
     * Pan Token Bin mapping details.
     */
    String PAN_TOKEN_BIN_MAPPING = "getPanTokenBinMapping";

    /**
     * Query to fetch bin mappings.
     */
    String GET_BIN_MAPPINGS = "getBinMappings";

    /**
     * Query to fetch existing roles.
     */
    String GET_ROLES_BY_USER_ID = "getRolesByUserId";

    /**
     * Get by role ids.
     */
    String GET_ROLES_BY_ROLE_IDS = "getRolesByRoleIds";

    /**
     * Session Expired Message.
     */
    String SESSION_EXPIRED_MSG = "SESSION_EXPIRED_MSG";

    /**
     * Query to fetch token bins from token bin mappings.
     */
    String GET_TOKEN_BINS_FROM_MAPPINGS = "getTokenBinsFrmMappings";

    /**
     * Query to fetch a list of token requestors based on id.
     */
    String GET_TOKEN_REQUESTORS_BY_ID = "getTokenRequestorsById";

    /**
     * Param for the above query.
     */
    String PARAM_TOKEN_REQUESTORS_IDS = "ids";

    /**
     * Query to delete all the permitted token requestors.
     */
    String DELETE_ALL_PERMITTED_TOKEN_REQUESTORS = "deleteAllPermittedTokenRequestors";
}
