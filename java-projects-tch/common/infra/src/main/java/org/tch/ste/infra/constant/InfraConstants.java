/**
 * 
 */
package org.tch.ste.infra.constant;

/**
 * @author Karthik.
 * 
 */
public interface InfraConstants {

    /**
     * Common Persistent Unit for common tables that is not issuer specific
     */
    String PERSISTENCE_UNIT_COMMON = "stePu_Common";

    /**
     * Persistence Unit 1.
     */
    String PERSISTENCE_UNIT_001 = "stePu_001";
    /**
     * Persistence Unit 2.
     */
    String PERSISTENCE_UNIT_002 = "stePu_002";
    /**
     * Persistence Unit 3.
     */
    String PERSISTENCE_UNIT_003 = "stePu_003";

    /**
     * Order By.
     */
    String ORDER_BY_CLAUSE = " ORDER BY ";

    /**
     * Standard format.
     */
    String STD_TIMESTAMP_FMT = "yyyyMMddHHmmss";
    /**
     * Property to set cloud Switch Endpoint
     */
    String PROPERTY_CLOUD_SWITCH_ENDPOINT = "cloudSwitchEndpoint";
    /**
     * Property to set cloud Switch ID
     */
    String PROPERTY_CLOUD_SWITCH_ID = "cloudSwitchID";
    /**
     * Property to set vault Switch ID
     */
    String PROPERTY_VAULT_SWITCH_ID = "vaultSwitchID";
    /**
     * Property to set vault Source ID
     */
    String PROPERTY_VAULT_SOURCE_ID = "vaultSourceID";
    /**
     * Property to set switch Cloud ID
     */
    String PROPERTY_SWITCH_CLOUD_ID = "switchCloudID";
    /**
     * Property to set batch file Root Directory
     */
    String PROPERTY_BATCH_FILE_ROOT_DIR = "batchfileRootDirectory";
    /**
     * Property to set vault log level
     */
    String PROPERTY_VAULT_LOG_LEVEL = "vaultLogLevel";

    /**
     * Minimum Length of the password.
     */
    int MIN_PASSWORD_LENGTH = 12;

    /**
     * Maximum length of the password.
     */
    int MAX_PASSWORD_LENGTH = 12;

    /**
     * Carriage return.
     */
    char CARRIAGE_RETURN = '\r';

    /**
     * Line Feed.
     */
    char LINE_FEED = '\n';

    /**
     * Format of MMyy.
     */
    String STD_MM_YY_FORMAT = "MMyy";

    /**
     * Xml tag for request
     */
    String PARAM_REQUEST = "CryptoServiceRequest";

    /**
     * XML Tag for response
     */
    String PARAM_RESPONSE = "CryptoServiceResponse";

    /**
     * Encoding type Value
     */
    String ENCODING_VALUE = "UTF-8";

    /**
     * XML Version
     */
    String XML_VERSION = "1.0";

    /**
     * Parameter for xml Attribute
     */
    String XML_ATTRIBUTE = "action";

    /**
     * Constant For Encrypt Value
     */
    String ENCRYPT_VALUE = "encrypt";

    /**
     * Constant for Decrypt Value
     */
    String DECRYPT_VALUE = "decrypt";

    /**
     * Key to Fetch the encryption URL
     */
    String ENCRYPTION_VALUE = "encryptionURL";

    /**
     * Application Type
     */
    String APPLICATION_TYPE = "mime/xml";

    /**
     * Display length of first digits.
     */
    int DISPALY_LENGTH_OF_DIGIT_DISPALY_INFRONT = 6;

    /**
     * Display length of last digits.
     */
    int DISPALY_LENGTH_OF_DIGIT_DISPALY_LAST = 4;
    /**
     * Mask symbol.
     */
    char MASK_SYMBOL = 'X';

    /**
     * Randomizer Algorithm.
     */
    String RANDOM_ALGORITHM = "SHA1PRNG";

    /**
     * Attribute type
     */
    String XML_TYPE = "type";

    /**
     * Attribute type value
     */
    String XML_ATTR_VAL = "decryptedString";

    /**
     * Attribute type Value
     */
    String XML_ATTR_VALUE = "encryptedString";
    

    /**
     * Start time of report.
     */
    String REPORT_START_TIME = "reportStartTime";

    /**
     * End time of report.
     */
    String REPORT_END_TIME = "reportEndTime";

    /**
     * Report Type.
     */
    String REPORT_TYPE = "reportType";
}
