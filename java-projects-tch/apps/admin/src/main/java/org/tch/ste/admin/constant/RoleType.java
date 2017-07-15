package org.tch.ste.admin.constant;

/**
 * TODO: Document Me
 * 
 * @author: Ganeshji Marwaha
 * @since: 4/25/14
 */
public enum RoleType {

    /**
     * Can view unprotected pages
     */
    ROLE_ANONYMOUS,
    /**
     * Can CRUD other users.
     */
    ROLE_TCH_USER_MGMT,

    /**
     * Can perform Issuer, Vault and Wallet Configuration
     */
    ROLE_TCH_CONFIGURATION,

    /**
     * Can manage Issuer's & Customer's credentials (lock/unlock etc.)
     */
    ROLE_ISSUER_MGMT;

    private RoleType() {
    }
}
