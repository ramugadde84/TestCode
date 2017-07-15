/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.annotation.FixedField;

/**
 * Lock or Unlock Customer Account Response.
 * 
 * @author ramug
 * 
 */
public class LockUnlockCustomerAccountResponse {

    @FixedField(offset = 0, length = 1)
    private int type = BatchFileRecordType.CONTENT.ordinal();

    @FixedField(offset = 1, length = 36)
    private String issuerCustomerId;

    @FixedField(offset = 37, length = 15)
    private String userName;

    @FixedField(offset = 52, length = 1)
    private String lockState;

    @FixedField(offset = 53, length = 6)
    private String responseCode;

    /**
     * gets the type.
     * 
     * @return the type.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type.
     * 
     * @param type
     *            - type value.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Gets the Issuer Customer Id.
     * 
     * @return - issuer customer id.
     */
    public String getIssuerCustomerId() {
        return issuerCustomerId;
    }

    /**
     * Sets the Issuer Customer Id.
     * 
     * @param issuerCustomerId
     *            - The customer id.
     */
    public void setIssuerCustomerId(String issuerCustomerId) {
        this.issuerCustomerId = issuerCustomerId;
    }

    /**
     * Gets the user name.
     * 
     * @return user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     * 
     * @param userName
     *            String - The user name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the lock state.
     * 
     * @return lock state.
     */
    public String getLockState() {
        return lockState;
    }

    /**
     * Sets the lock state.
     * 
     * @param lockState
     *            - the lock state.
     */
    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    /**
     * Gets the response code.
     * 
     * @return responseCode - the Response code.
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the response code.
     * 
     * @param responseCode
     *            - the reponse code.
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
