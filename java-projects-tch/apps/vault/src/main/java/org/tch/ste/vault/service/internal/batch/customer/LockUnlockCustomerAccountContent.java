package org.tch.ste.vault.service.internal.batch.customer;

/**
 * LockUnlockCustomerAccount data is stored in this bean.
 * 
 * @author ramug
 * 
 */
public class LockUnlockCustomerAccountContent {
    private String issuerCustomerId;

    private String lockState;

    private int type;

    private String userName;

    /**
     * @return issuerCustomerId
     */
    public String getIssuerCustomerId() {
        return issuerCustomerId;
    }

    /**
     * @return lock state.
     */
    public String getLockState() {
        return lockState;
    }

    /**
     * Gets the type.
     * 
     * @return type.
     */
    public int getType() {
        return type;
    }

    /**
     * Username gets.
     * 
     * @return username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * IssuerCustomer Id-sets.
     * 
     * @param issuerCustomerId
     *            String - The issuer customer id.
     */
    public void setIssuerCustomerId(String issuerCustomerId) {
        this.issuerCustomerId = issuerCustomerId;
    }

    /**
     * Lock state sets.
     * 
     * @param lockState
     *            String - The lock state.
     */
    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            int - The type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * username-sets.
     * 
     * @param userName
     *            String - The user name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
