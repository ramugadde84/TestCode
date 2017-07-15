/**
 * 
 */
package org.tch.ste.admin.constant;

/**
 * Contains constants for the Controller and Request Mappings.
 * 
 * @author Karthik.
 * 
 */
public interface AdminControllerConstants {

    /**
     * Redirection Path.
     */
    String C_REDIRECT_PATH = "redirect:/app/";

    /**
     * Login Path.
     */
    String C_LOGIN_REQ_MAPPING = "login";

    /**
     * Logout Path.
     */
    String C_LOGOUT_REQ_MAPPING = "logout";

    /**
     * Home Path.
     */
    String C_HOME_REQ_MAPPING = "home";
    /**
     * User ID.
     */
    String C_FIELD_NAME_USER_ID = "userId";
    /**
     * First Name
     */
    String C_FIELD_NAME_FIRST_NAME = "firstName";
    /**
     * Last Name
     */
    String C_FIELD_NAME_LAST_NAME = "lastName";
    /**
     * Authorized Roles
     */
    String C_FIELD_NAME_USER_ROLES = "authorizedRoles";
    /**
     * Authorized Roles
     */
    String C_FIELD_NAME_IISN = "iisn";
    /**
     * issuer Path.
     */
    String C_ISSUER_REQ_MAPPING = "issuer";
    /**
     * User Path.
     */
    String C_USER_URL = "/user/usermgmt";

    /**
     * The issuers list path.
     */
    String C_ISSUER_LIST_REQ_MAPPING = "/list";

    /**
     * The issuers model attribute.
     */
    String C_MODEL_ATTR_ISSUER = "issuers";

    /**
     * The users model attribute.
     */
    String C_MODEL_ATTR_USER = "users";

    /**
     * Vault Configuration Mapping.
     */
    String C_VAULT_CONFIGURATION_MAPPING = "vault/configure";

    /**
     * Redirect to some other url.
     */
    String C_REDIRECT = "redirect:/";

    /**
     * The vault configuration model attribute.
     */
    String C_MODEL_ATTR_VAULT_CONFIGURATION = "vaultConfiguration";

    /**
     * Logging Levels.
     */
    String C_MODEL_ATTR_VAULT_CONFIGURATION_LOGGING_LEVELS = "loggingLevels";

    /**
     * For add issuers page
     */
    String C_ADD_ISSUER_MAPPING = "/add";

    /**
     * For add issuers page
     */
    String C_EDIT_ISSUER_MAPPING = "/edit";

    /**
     * 
     */
    String C_TOKEN_REQUESTOR_LIST_REQ_MAPPING = "/list";

    /**
     * vault configuration path.
     */
    String C_VAULT_MASTER_CONFIGURATION = "/vaultMasterConfiguration";

    /**
     * save vault configuration details.
     */
    String C_VAULT_SAVE_MASTER_CONFIGURATION = "/saveVaultMasterConfiguration";
    /**
     * Token Requestor Mapping
     */
    String C_TOKEN_REQ_MAPPING = "tokenRequestor";

    /**
     * For adding the user.
     */
    String C_ADD_USER_MAPPING = "/user/addUser";

    /**
     * String C_TOKEN_REQUESTOR_LIST_REQ_MAPPING = "/list"; /**
     * 
     */
    String C_LIST_USER_MAPPING = "user/list";
    /**
	 * 
	 */
    String C_MODEL_ATTR_TOKEN_REQUESTOR = "tokenRequestor";
    /**
     * Token Rules attribute.
     */
    String C_TOKEN_RULES_ISSUER = "tokenRulesIssuer";

    /**
     * Token DPI Rules constant.
     */
    String C_TOKEN_DPI_RULES = "/token_requestor/dpi_rules";

    /**
     * Issuer Id.
     */
    String C_REQUEST_PARAM_USER_ID = "id";

    /**
	 * 
	 */
    String C_REQUEST_MAPPING_TOKEN_REQ_DEL = "/delete";
    /**
	 * 
	 */
    String C_REQUEST_MAPPING_USER_DEL = "/delete";

    /**
     * Failed attempts field name.
     */
    String FIELD_NAME_AUTH_FAILED_ATTEMPTS = "failedAttemptsToTriggerLockout";

    /**
     * Auth Endpoint field name
     */
    String FIELD_NAME_AUTH_ENDPOINT = "authEndpoint";

    /**
     * Account List Endpoint
     */
    String FIELD_NAME_ACCOUNTLIST_ENDPOINT = "accountListEndpoint";

    /**
     * Authentication url empty.
     */
    String C_AUTHENTICATION_URL_EMPTY = "AUTHENTICATION_URL_EMPTY";

    /**
     * Account List End point.
     */
    String C_AUTHENTICATION_ACCOUNT_LIST_ENDPOINT = "AUTHENTICATION_ACCOUNT_LIST_ENDPOINT";

    /**
     * Issuer Id.
     */
    String C_REQUEST_PARAM_ISSUER_ID = "id";

    /**
     * Authentication Mechanism List.
     */
    String C_MODEL_ATTR_AUTHENTICATION_MECHANISMS = "authenticationMechanisms";

    /**
     * Wallet Providers.
     */
    String C_MODEL_ATTR_TOKEN_REQUESTORS = "tokenRequestors";

    /**
     * Issuer Model Attribute.
     */
    String C_MODEL_ATTR_ISSUER_DETAILS = "issuer";

    /**
     * Errors.
     */
    String C_MODEL_ATTR_ERRORS = "errors";

    /**
     * Token requestor name.
     */
    String C_FIELD_NAME_TOKEN_REQUESTOR_NAME = "name";

    /**
     * Token requestor NetworkId.
     */
    String C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID = "networkId";

    /**
     * Bin mapping path
     */
    String C_MAPPING_REQ_MAPPING = "/issuer/mapping";

    /**
     * save bin mapping details
     */
    String C_SAVE_BIN_MAPPING = "/saveBinMapping";
    /**
     * Field name which maps to issuer name.
     */
    String FIELD_NAME_ISSUER_NAME = "name";

    /**
     * Field name which maps to issuer name.
     */
    String FIELD_NAME_ISSUER_IISN = "iisn";

    /**
     * Bin mapping list
     */
    String C_BIN_MAPPING_LIST = "/issuer/binMappingList";

    /**
     * Mapping providers.
     */
    String C_MODEL_ATTR_BIN_MAPPING = "binMapping";

    /**
     * Token Rules.
     */
    String C_TOKENS_RULES = "/issuer/tokenRules";
    /**
     * Customer Management.
     */
    String C_CUSTOMER_MANAGEMENT = "/user/customer";

    /**
     * Real bin.
     */
    String C_FIELD_NAME_REAL_BIN = "realBin";

    /**
     * Token bin.
     */
    String C_FIELD_NAME_TOKEN_BIN = "tokenBin";

    /**
     * Roles.
     */
    String C_MODEL_ATTR_ROLES = "roles";

    /**
     * Id.
     */
    String C_REQUEST_PARAM_ID = "id";

    /**
     * Iisn.
     */
    String C_REQUEST_PARAM_ISSUER_IISN = "iisn";

    /**
     * The id.
     */
    String C_REQUEST_PARAM_CUSTOMER_ID = "id";

    /**
     * Reset Password
     * 
     */
    String C_RESET_PASSWORD = "/resetPassword";

    /**
     * Dollar amount for Expire dpi.
     */
    String C_DOLLAR_AMOUNT_EXPIRE_DPI = "dollarAmountForExpireDpi";

    /**
     * DPI per day.
     */
    String C_DPI_PER_DAY = "dpiPerDay";

    /**
     * Number of Detokenized Requests.
     */
    String C_NUMBER_OF_DETOKENIZED_REQUEST = "numberOfDetokenizedRequests";

    /**
     * Real Bin.
     */
    String C_REAL_BIN = "realBin";

    /**
     * TimeInMinutesAfterExpirationForReuse
     */
    String C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE = "timeInMinutesAfterExpirationForReuse";

    /**
     * Token Expiration Minutes.
     */
    String C_TOKEN_EXPIRATION_MINUTES = "tokenExpirationMinutes";

    /**
     * For showing save message.
     */
    String C_REQUEST_PARAM_SAVE_MSG = "showSaveMsg";

    /**
     * Save Message Attribute.
     */
    String C_MODEL_ATTR_ISSUER_SAVE_MSG = "saveMessage";

    /**
     * Save message code.
     */
    String C_MODEL_ATTR_ISSUER_SAVE_MSG_CODE = "SUCCESS_MESSAGE";

    /**
     * TokenExpireAfterSingleDollarAmountTransaction
     */
    String C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION = "tokenExpireAfterSingleDollarAmountTransaction";

    /**
     * TokenPullEnable
     */
    String C_TOKEN_PULL_ENABLE = "tokenPullEnable";

    /**
     * TokenPushOnCreation
     */
    String C_TOKEN_PUSH_CREATION = "tokenPushOnCreation";
    /**
     * Issuer Logo field name
     */
    String FIELD_NAME_ISSUER_LOGO = "issuerLogo";
    /**
     * Lock Account
     */
    String C_LOCK = "/lock";
    /**
     * UnLock Account
     */
    String C_UNLOCK = "/unlock";

    /**
     * Environment.
     */
    String C_MODEL_ATTR_ENVIRONMENT = "environment";

    /**
     * Timestamp.
     */
    String C_MODEL_ATTR_TIMESTAMP = "timestamp";

    /**
     * The issuers model attribute.
     */
    String C_MODEL_ATTR_WELCOME_MSG = "welcomeMessage";

    /**
     * Keep alive.
     */
    String C_KEEP_ALIVE_MAPPING = "keepAlive";

    /**
     * To signify if something is from logout.
     */
    String C_REQUEST_PARAM_FROM_LOGOUT = "fromLogout";

    /**
     * Empty Check.
     */
    String C_EMPTY_CHECK = "CUSTOM_EMPTY";
    /**
     * Exceeds length.
     */
    String C_EXCEEDS_LENGTH = "Length";

    /**
     * Null Check.
     */
    String C_NULL_CHECK = "CUSTOM_NULL";

    /**
     * Wrong pattern.
     */
    String C_WRONG_PATTERN = "WRONG_PATTERN";
    /**
     * In valid bin format.
     */
    String C_BIN_FORMAT_INVALID = "BIN_FORMAT_INVALID";

    /**
     * IID excceeds.
     */
    String C_IID_EXCEEDS = "IID_EXCEEDS";

    /**
     * Bin Value.
     */
    String C_SELECT_BIN_VALUE = "SELECT_BIN_VALUE";

    /**
     * Dollar Amount Expired Tokens.
     */
    String C_DOLLAR_AMOUNT_EXPIRED_TOKENS = "Dollar Amount for Expired Token(s)";

    /**
     * Number of Tokens.
     */
    String C_NUMBER_OF_TOKENS = "Number of Token(s) per day";

    /**
     * Expiration After Number Of Detokenized Requests.
     */
    String C_EXPIRATION_AFTER_NUMBER_OF_DETOKENIZED_REQUESTS = "Expiration After Number of De-tokenized Requests";

    /**
     * Bin.
     */
    String C_BIN = "BIN";

    /**
     * Time After Expiration Token Reuse.
     */
    String C_TIME_AFTER_EXPIRATION_TOKEN_REUSE = "Time After Expiration for Token Reuse";

    /**
     * Token Expiration minutes
     */
    String C_TOKEN_EXPIRAATION_IN_MINUTES = "Expiration from Issuance";

    /**
     * Token Expiration After Single Dollar Amount Transaction.
     */
    String C_TOKEN_EXPIRATION_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION = "Expiration After Single Dollar Amount Transaction";

    /**
     * Real bin label.
     */
    String C_REALBIN_LABEL = "Real BIN";

    /**
     * Token bin label.
     */
    String C_TOKENBIN_LABEL = "Token BIN";
    /**
     * Token requestor label name.
     */
    String C_TOKENREQUESTOR_NAME_LABEL = "Name";

    /**
     * Token requestor label id.
     */
    String C_TOKENREQUESTOR_ID_LABEL = "Token Requestor ID";

    /**
     * Token Pull Enable.
     */
    String C_TOOKEN_PULL_ENABLE = "Pull Enabled";

    /**
     * Token Push on Creation.
     */
    String C_TOOKEN_PUSH_CREATION = "Push on creation";
    /**
     * Label User id.
     */
    String C_USERID_LABEL = "User ID";

    /**
     * Label first name.
     */
    String C_FIRSTNAME_LABEL = "First Name";
    /**
     * Label last name.
     */
    String C_LASTNAME_LABEL = "Last Name";

    /**
     * Label roles.
     */
    String C_ROLES_LABEL = "Roles";
    /**
     * Label iisn.
     */
    String C_IISN_LABEL = "IISN";
    /**
     * Field iid.
     */
    String C_FIELD_IID = "iid";

    /**
     * Field Name.
     */
    String C_FIELD_NAME = "Name";

    /**
     * Field Dropzone Path.
     */
    String C_FIELD_DROPZONE_PATH = "dropzonePath";

    /**
     * Check for session expiry.
     */
    String IS_SESSION_EXPIRED = "isSessionExpired";

    /**
     * Failed Attempts wrong pattern.
     */
    String C_FAILED_ATTEMPTS_WRONG_PATTERN = "FAILED_ATTEMPTS_WRONG_PATTERN";

    /**
     * Failed login attempts.
     */
    String C_FAILED_ATTEMPTS = "FAILED_ATTEMPTS";

    /**
     * Auth app url.
     */
    String FIELD_NAME_AUTH_APP_URL = "authenticationAppUrl";

    /**
     * Authentication APP url empty check.
     */
    String C_FILED_NAME_AUTH_EMPTY = "AUTH_APP_URL_EMPTY";
    /**
     * Save successful message
     */
    String C_TOKEN_REQUESTOR_SAVE_SUCCESS = "TOKEN_REQUESTOR_SAVE_SUCCESS";

    /**
     * Delete successful message
     */
    String C_TOKEN_REQUESTOR_DEL_SUCCESS = "TOKEN_REQUESTOR_DEL_SUCCESS";

    /**
     * Authentication Lock Out message.
     */
    String C_AUTH_LOCK_OUT_MESSAGE = "authLockoutMessage";

    /**
     * Authentication lock out message max length.
     */
    String C_AUTH_LOCK_OUT_MESSAGE_LENGTH_EXCEEDS = "AUTH_LOCK_OUT_MESSAGE_LENGTH_EXCEEDS";

    /**
     * Authentication Error Message.
     */
    String C_AUTH_ERROR_MESSAGE = "authErrorMessage";

    /**
     * Authentication ERROR message max length.
     */
    String C_AUTH_ERROR_MESSAGE_LENGTH_EXCEEDS = "AUTH_ERROR_MESSAGE_LENGTH_EXCEEDS";

    /**
     * Logo File Name length exceeds.
     */
    String C_LOGO_FILE_NAME_LENGTH_EXCEEDS = "LOGO_FILE_NAME_LENGTH_EXCEEDS";

    /**
     * Lock out attempts length.
     */
    String C_LOCK_OUT_ATTEMTPS_LENGTH = "LOCK_OUT_ATTEMPTS_LENGTH";

    /**
     * Length exceeds.
     */
    String C_LENGTH_COMMON_EXCEEDS = "LENGTH_EXCEEDS_COMMON";

}
