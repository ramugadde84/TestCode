/**
 * 
 */
package org.tch.ste.auth.constant;

/**
 * Other constants.
 * 
 * @author Karthik.
 * 
 */
public interface AuthConstants {
    /**
     * Login Error Message.
     */
    String FIELD_NAME_LOGIN = "login";
    /**
     * UTF-8.
     */
    String CHARSET_UTF8 = "UTF-8";

    /**
     * Lock Out Code for Exceeding Configured limit for Authentication Attempts.
     */
    String LOCKOUT_CODE_FOR_EXCEEDING_FAILED_AUTHENTICATION_ATTEMPTS = "1";
    /**
     * Error code for any Error occured while saving Payment Instruments.
     * Parameter for the above query.
     */
    String AUTH_PAYMENT_INSTRUMENT_ERROR_CODE = "0";
    /**
     * IISN Value.
     */
    String PARAM_IISN = "iisn";

    /**
     * Fetches the issuer based on the IISN.
     */
    String GET_ISSUER_BY_IISN = "getIssuerByIisn";

    /**
     * Outbound directory.
     */
    String OUTBOUND_DIR = "outbound";
    /**
     * Temp out file name.
     */
    String JOB_PARAM_TEMP_OUTPUT_FILE_NAME = "tempOutputFileName";
    /**
     * Login history id.
     */
    String LOGIN_HISTORY_ID = "loginHistoryId";
    /**
     * Time stamp.
     */
    String JOB_PARAM_TIMESTAMP = "timeStamp";

    /**
     * IISN.
     */
    String JOB_PARAM_IISN = "iisn";
    /**
     * Actual Output File Name.
     */
    String JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME = "actualOutputFileName";

    /**
     * CSV seperator.
     */
    String CSV_SEPERATOR = ",";

    /**
     * Last locked out attempt report history time stamp.
     */
    String LAST_LOCKED_OUT_ATTEMPT_REPORT_HISTORY_TIME_STAMP = "lastLockedOutAttemptReportHistoryTimeStamp";

    /**
     * Payment Instrument activation URL
     */
    String PAYMENT_INSTRUMENT_ACTIVATION_URL = "paymentInstrumentActivationURL";

    /**
     * Payment Instrument deactivation URL
     */
    String PAYMENT_INSTRUMENT_DEACTIVATION_URL = "paymentInstrumentDeactivationURL";
    /**
     * Payment Instrument Web Service URL.
     */
    String PAYMENT_INSTRUMENT_SERVICE = "/PaymentInstrumentService";
    /**
     * IISN Value.
     */
    String ARNID = "arnId";
    /**
     * Token Requestor Id.
     */
    String TOKEN_REQUESTOR_ID = "tokenRequestorId";
    /**
     * Customer Instance Id.
     */
    String CIID = "ciid";
    /**
     * Payment Instrument Active State.
     */
    String ACTIVE = "active";
    /**
     * 
     */
    String REPORT_TIME = "reportTime";
   
    /**
     * Length of user agent.
     */
    int CLIENT_USER_AGENT_LEN = 30;

    /**
     * Number of Records.
     */
    String NUMBER_OF_RECORDS = "numberOfRecords";

    /**
     * value.
     */
    String VALUE = "value";

    /**
     * Zero.
     */
    int ZERO = 0;

    /**
     * Authentication type 3.
     */
    int AUTHENTICATION_TYPE_THREE = 3;

    /**
     * Authentication type 4.
     */
    int AUTHENTICATION_TYPE_FOUR = 4;
}
