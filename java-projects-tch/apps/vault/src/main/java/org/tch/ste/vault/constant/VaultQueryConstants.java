/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * Contains query related constants.
 * 
 * @author Karthik.
 * 
 */
public interface VaultQueryConstants {

    /**
     * The file type.
     */
    String BATCH_FILE_TYPE = "fileType";

    /**
     * The query to find by file type.
     */
    String FIND_BY_BATCH_FILE_TYPE = "findByBatchFileType";

    /**
     * Find the issuer using the dropzone path.
     */
    String FIND_ISSUER_BY_DROPZONE_PATH = "findIssuerByDropzonePath";

    /**
     * Dropzone Path.
     */
    String DROPZONE_PATH = "dropzonePath";

    /**
     * Query to fetch the specified configuration param.
     */
    String FIND_CONFIGURATION_PARAM = "findConfigurationParam";

    /**
     * The key used in the query.
     */
    String CONFIGURATION_PARAM_KEY = "key";

    /**
     * Fetch the issuers based on auth type.
     */
    String GET_ISSUERS_BY_AUTH_TYPE = "getIssuersByAuthType";

    /**
     * Parameter for the above query.
     */
    String PARAM_AUTHENTICATION_TYPE = "authenticationType";
    /**
     * Parameter for the above query.
     */
    String PARAM_IISN = "iisn";

    /**
     * Customer User Name.
     */
    String PARAM_USER_NAME = "userName";

    /**
     * Query to check for an existing user name.
     */
    String GET_CUSTOMER_BY_USER_NAME = "getCustomerByUserName";

    /**
     * Query to check for exisitng ARN
     */
    String GET_ID_BY_ARN = "getIdbyArn";

    /**
     * Fetches the details based on the Bin value
     */
    String GET_IISN_BY_BIN = "getIisnbyBin";
    /**
     * Parameter to fetch the bin value
     */
    String PARAM_BIN = "bin";
    /**
     * ARN ID
     */
    String PARAM_ARN_ID = "arn";
    /**
     * ARN_LENGTH
     */
    int ARN_LENGTH = 23;

    /**
     * The timestamp.
     */
    String PARAM_BATCH_FILE_NAME = "batchFileName";

    /**
     * Fetches an existing history.
     */
    String GET_EXISTING_BATCH_FILE_HISTORY = "getExistingBatchFileHistory";

    /**
     * Encrypted Pan.
     */
    String PARAM_ENC_PAN = "encryptedPan";

    /**
     * Expiry Date.
     */
    String PARAM_CUSTOMER_ID = "issuerCustomerId";

    /**
     * Find existing payment instrument.
     */
    String FIND_BY_PAN_AND_CUSTOMER_ID = "findPaymentInstrumentByPanAndCustId";

    /**
     * IID.
     */
    String PARAM_IID = "iid";

    /**
     * Find issuer by IID.
     */
    String FIND_ISSUER_BY_IID = "findIssuerByIid";

    /**
     * random token length
     */
    String ARN_ID = "arnId";

    /**
     * Issuer Customer Id.
     */
    String PARAM_ISSUER_CUSTOMER_ID = "issuerCustomerId";

    /**
     * Find by issuer customer id.
     */
    String FIND_CUSTOMER_BY_ISSUER_CUSTOMER_ID = "findCustomerByIssuerCustomerId";

    /**
     * Query to Get tokens to deactivate by Arn.
     */
    String GET_TOKENS_FOR_DEACTIVATION_BY_ARN = "getTokensForDeactivationByArn";

    /**
     * Query to Get tokens to deactivate by payment instrument.
     */
    String GET_TOKENS_FOR_DEACTIVATION_BY_PAYMENTINSTRUMENT = "getTokensForDeactivationByPaymentInstrument";

    /**
     * Query to deactivate given token.
     */
    String DEACTIVATE_TOKEN = "deactivateToken";

    /**
     * ID.
     */
    String PARAM_ID = "id";
    /**
     * Fetches the issuer based on the IISN.
     */
    String GET_ISSUER_BY_IISN = "getIssuerByIisn";
    /**
     * 
     */
    String ARN = "arnId";

    /**
     * 
     */
    String STORE_TOKEN_VALUES = "StoreTokenValues";

    /**
     * 
     */
    String GENERATED_TOKEN = "GeneratedToken";

    // Newly added

    /**
     * Token requestor details
     */
    String TOKENREQUESTOR_ID = "tokenRequestorId";

    /**
     * Token requestor details
     */
    String GET_TOKENREQUESTOR_DETAILS = "getTokenRequestor";

    /**
     * Token requestor details
     */
    String ARN_DETAILS = "arnId";

    /**
     * Arn details
     */
    String GET_ARN_DETAILS = "getArnDetails";

    /**
     * 
     */
    String CHECK_TOKENREQUESTORID_ARN = "checkTokenRequestorIdArn";

    /**
     * Find issuer by IID.
     */
    String GET_TOKEN_BIN = "getTokenbin";

    /**
     * Real bin
     */
    String PARAM_REALBIN = "realBin";

    /**
     * 
     */
    String ENCRYPTED_TOKEN = "encryptedToken";

    /**
     * 
     */
    String CHECK_TOKEN = "checkToken";

    /**
     * Field type.
     */
    String PARAM_PAN_HASH = "panHash";

    /**
     * Field type.
     */
    String PARAM_EXPIRY_MONTH_YEAR = "expiryMonthYear";

    /**
     * 
     */
    String FETCH_TOKEN_EXPIRATION_TIME = "fetchTokenExpirationTime";

    /**
     * The Ciid.
     */
    String PARAM_CIID = "ciid";

    /**
     * Is Active.
     */
    String PARAM_ACTIVE = "active";

    /**
     * ARN.
     */
    String PARAM_ARN = "arn";

    /**
     * Get pan by ARN.
     */
    String GET_PAN_BY_ARN = "getPanByArn";

    /**
     * Query to deactivate given permitted token requestor arn.
     */
    String DEACTIVATE_PERMITTED_TOKEN_REQUESTOR_ARN = "deactivatePermittedTokenRequestorArn";

    /**
     * Query to get list of active payment instruments.
     */
    String GET_LIST_OF_ACTIVE_PAYMENT_INSTRUMENT = "getListOfActivePaymentInstrument";

    /**
     * Token Reqyest Id.
     */
    String PARAM_TRID = "trId";

    /**
     * Fetches without Ciid.
     */
    String GET_PERMITTED_TOKEN_ARN_WITHOUT_CIID = "getPermittedTokenRequestorArnWithoutCiid";

    /**
     * Fetches with Ciid.
     */
    String GET_PERMITTED_TOKEN_ARN_WITH_CIID = "getPermittedTokenRequestorArnWithCiid";

    /**
     * Fetches tokens without Ciid.
     */
    String GET_TOKENS_WITHOUT_CIID = "getTokensWithoutCiid";

    /**
     * Fetches tokens with Ciid.
     */
    String GET_TOKENS_WITH_CIID = "getTokensWithCiid";

    /**
     * Network tr id.
     */
    String PARAM_NETWORK_TR_ID = "networkTrId";

    /**
     * Fetches token requestor based on network TR id.
     */
    String GET_TOKEN_REQUESTOR_BASED_ON_NETWORK_ID = "getTokenRequestorByNetworkTrId";

}
