/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * @author Karthik.
 *
 */
/**
 * Constants in the vault.
 * 
 * @author Karthik.
 * 
 */
public interface VaultConstants {

    /**
     * Inbound Directory.
     */
    String INBOUND_DIR = "inbound";

    /**
     * Outbound directory.
     */
    String OUTBOUND_DIR = "outbound";

    /**
     * In-Process directory.
     */
    String INPROCESS_DIR = "in-process";

    /**
     * Processed directory.
     */
    String PROCESSED_DIR = "processed";

    /**
     * Error directory.
     */
    String ERROR_DIR = "error";

    /**
     * File type part.
     */
    int FILE_TYPE_PART = 0;

    /**
     * IISN Part.
     */
    int IISN_PART = 1;

    /**
     * Timestamp part.
     */
    int TIMESTAMP_PART = 2;

    /**
     * File part seperator.
     */
    String FILE_PART_SEPERATOR = "_";

    /**
     * Expected parts.
     */
    int NUM_EXPECTED_FILE_NAME_PARTS = 3;

    /**
     * Batch File Name.
     */
    String BATCH_INPUT_FILE_NAME = "batchInputFileName";

    /**
     * Batch Result.
     */
    String BATCH_RESULT = "batchResult";

    /**
     * Expiry time for passwords.
     */
    int PASSWORD_EXPIRY_TIME = -30;

    /**
     * Expiry Date.
     */
    String EXPIRY_DATE = "expiryDate";

    /**
     * Temporary output file.
     */
    String JOB_PARAM_TEMP_OUTPUT_FILE_NAME = "tempOutputFileName";

    /**
     * Actual file name.
     */
    String JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME = "actualOutputFileName";

    /**
     * Max amount of retries.
     */
    int MAX_USERNAME_GENERATION_RETRIES = 10;

    /**
     * Expiry File Name.
     */
    String PASSWORD_EXPIRY_FILE_TYPE = "CREDUPDATEDAILY";

    /**
     * Timestamp used as a job parameter.
     */
    String JOB_PARAM_TIMESTAMP = "timeStamp";

    /**
     * Iisn used as a job parameter.
     */
    String JOB_PARAM_IISN = "iisn";

    /**
     * Appended to a file that has errors.
     */
    String ERROR_FILE_APPEND_STR = "_ERROR";

    /**
     * CSV seperator
     */
    String CSV_SEPERATOR = ",";

    /**
     * Validation of header and trailer.
     */
    String HEADER_TRAILER_VALIDATION_RESULT = "headerTrailerValidationResult";

    /**
     * IID.
     */
    String IID = "iid";

    /**
     * File Type.
     */
    String JOB_PARAM_FILE_TYPE = "fileType";
    /**
     * The token requestor network Id
     */
    String NETWORK_TR_ID = "networkId";

    /**
     * Iisn parameter to query.
     */
    String ISSUER_IISN = "iisn";
    /**
     * Query to fetch issuers by name.
     */
    String GET_ISSUERS_BY_IISN = "getIssuersByIisn";

    /**
     * Number of processed records.
     */
    String NUM_PROCESSED_RECORDS = "numProcessedRecords";

    /**
     * Number of processed records.
     */
    String DETOKENIZATION_REQUEST_ID = "detokenizationRequestId";

    /**
     * Lock or Unlock issuer customer id.
     */
    String LOCK_UNLOCK_ISSUER_CUSTOMER_ID = "issuerCustomerId";

    /**
     * Lock or Unlock User name.
     */
    String LOCK_UNLOCK_USER_NAME = "userName";

    /**
     * Lock or Unlock Customer Account select.
     */
    String LOCK_UNLOCK_CUSTOMER_ACCOUNT_SELECT = "lockUnlockCustomerAccountSelect";

    /**
     * Lock or Unlock Customer Account Locked.
     */
    String LOCK_UNLOCK_CUSTOMER_ACCOUNT_LOCKED = "accountLocked";

    /**
     * Lock or Unlock customer account update.
     */
    String LOCK_UNLOCK_CUSTOMER_ACCOUNT_UPDATE = "updateLockUnlockCustomerAccount";

    /**
     * Lock Out reason code.
     */
    String LOCK_UNLOCK_CUSTOMER_REASON_CODE = "lockReasonCode";

    /**
     * Account Locked Reason code for vault.
     */
    String ACCOUNT_LOCKED_REASON_CODE = "5";

    /**
     * Account Unloced Reason code for Vault.
     */
    String ACCOUNT_UNLOCKED_REASON_CODE = "0";

    /**
     * Account Unlocked.
     */
    String ACCOUNT_UNLOCKED = "U";

    /**
     * Account Unlocked.
     */
    String ACCOUNT_LOCKED = "L";

    /**
     * Success.
     */
    Integer SUCCESS = 1;

    /**
     * Failure.
     */
    Integer FAILURE = 0;

    /**
     * Size of issuer account id.
     */
    int ISSUER_ACCOUNT_ID_SIZE = 15;

    /**
     * Size of issuer customer id.
     */
    int ISSUER_CUSTOMER_ID_SIZE = 15;

    /**
     * Failure message 
     */
    String  FAILURE_MESSAGE = "Invalid state. Something went wrong.";

    /**
     * Retries.
     */
    int MAX_RETRIES_FOR_UNIQUE_CREATION = 100;


}
