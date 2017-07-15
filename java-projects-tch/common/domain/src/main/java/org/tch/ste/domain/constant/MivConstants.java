/**
 * 
 */
package org.tch.ste.domain.constant;

/**
 * @author Karthik.
 * 
 */
public interface MivConstants {

    /**
     * The current persistence unit.
     */
    String CURRENT_PERSISTENCE_UNIT = "currentPersistenceUnit";

    /**
     * The online transaction manager.
     */
    String ONLINE_TRANSACTION_MANAGER = "onlineTransactionManager";

    /**
     * The transaction manager for batches.
     */
    String BATCH_TRANSACTION_MANAGER = "batchTransactionManager";

    /**
     * Current transaction manager.
     */
    String CURRENT_TRANSACTION_MANAGER = "currentTransactionManager";

    /**
     * The switch end point.
     */
    String VAULT_CONFIGURATION_SWITCH_ENDPOINT = "switchEndpoint";

    /**
     * Base log level.
     */
    String VAULT_CONFIGURATION_BASE_LOG_LEVEL = "baseLogLevel";

    /**
     * Batch Root.
     */
    String VAULT_CONFIGURATION_BATCH_ROOT_DIRECTORY = "batchfileRootDirectory";

    /**
     * Cloud switch id.
     */
    String VAULT_CONFIGURATION_CLOUD_SWITCH_ID = "cloudSwitchID";

    /**
     * Vault switch id.
     */
    String VAULT_CONFIGURATION_VAULT_SWITCH_ID = "vaultSwitchId";

    /**
     * Password expiration days.
     */
    String VAULT_CONFIGURATION_PASSWORD_EXPIRATION_DAYS = "passwordExpirationDays";

    /**
     * Authentication app URL
     */
    String VAULT_AUTHENTICATION_APP_URL = "authenticationAppUrl";

    /**
     * Iisn.
     */
    String ISSUER_ENROLLMENT_URL_PARAM_IISN = "iisn";

    /**
     * Token requestor Id.
     */
    String ISSUER_ENROLLMENT_URL_PARAM_TRID = "trId";

    /**
     * WIID.
     */
    String ISSUER_ENROLLMENT_URL_PARAM_CIID = "ciid";
    /**
     * Source id.
     */
    String VAULT_CONFIGURATION_SOURCE_ID = "vaultSourceID";
    /**
     * 
     */
    String REPORT_TIME = "reportTime";

}
