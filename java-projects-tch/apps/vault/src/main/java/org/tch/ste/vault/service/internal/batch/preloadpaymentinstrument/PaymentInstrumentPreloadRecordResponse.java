/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.annotation.FixedField;

/**
 * Response for a preload record.
 * 
 * @author Karthik.
 * 
 */
public class PaymentInstrumentPreloadRecordResponse {

    @FixedField(offset = 0, length = 1)
    private int type = BatchFileRecordType.CONTENT.ordinal();

    @FixedField(offset = 1, length = 19)
    private String panMasked;

    @FixedField(offset = 20, length = 4)
    private String expiryDate;

    @FixedField(offset = 24, length = 36)
    private String issuerCustomerId;

    @FixedField(offset = 60, length = 36)
    private String issuerAccountId;

    @FixedField(offset = 96, length = 36)
    private String providedUserName;

    @FixedField(offset = 132, length = 16)
    private String providedPassword;

    @FixedField(offset = 148, length = 15)
    private String providedRegistrationToken;

    @FixedField(offset = 163, length = 20)
    private String accountNickName;

    @FixedField(offset = 183, length = 36)
    private String arn;

    @FixedField(offset = 219, length = 15)
    private String userName;

    @FixedField(offset = 234, length = 15)
    private String password;

    @FixedField(offset = 249, length = 15)
    private String registrationToken;

    @FixedField(offset = 264, length = 6)
    private String responseCode;

    /**
     * Returns the field type.
     * 
     * @return type int - Get the field.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the field type.
     * 
     * @param type
     *            int - Set the field type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the field responseCode.
     * 
     * @return responseCode String - Get the field.
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the field responseCode.
     * 
     * @param responseCode
     *            String - Set the field responseCode.
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * Returns the field userName.
     * 
     * @return userName String - Get the field.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the field userName.
     * 
     * @param userName
     *            String - Set the field userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the field password.
     * 
     * @return password String - Get the field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the field password.
     * 
     * @param password
     *            String - Set the field password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the field arn.
     * 
     * @return arn String - Get the field.
     */
    public String getArn() {
        return arn;
    }

    /**
     * Sets the field arn.
     * 
     * @param arn
     *            String - Set the field arn.
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

    /**
     * Returns the field panMasked.
     * 
     * @return panMasked String - Get the field.
     */
    public String getPanMasked() {
        return panMasked;
    }

    /**
     * Sets the field panMasked.
     * 
     * @param panMasked
     *            String - Set the field panMasked.
     */
    public void setPanMasked(String panMasked) {
        this.panMasked = panMasked;
    }

    /**
     * Returns the field expiryDate.
     * 
     * @return expiryDate String - Get the field.
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the field expiryDate.
     * 
     * @param expiryDate
     *            String - Set the field expiryDate.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Returns the field issuerCustomerId.
     * 
     * @return issuerCustomerId String - Get the field.
     */
    public String getIssuerCustomerId() {
        return issuerCustomerId;
    }

    /**
     * Sets the field issuerCustomerId.
     * 
     * @param issuerCustomerId
     *            String - Set the field issuerCustomerId.
     */
    public void setIssuerCustomerId(String issuerCustomerId) {
        this.issuerCustomerId = issuerCustomerId;
    }

    /**
     * Returns the field issuerAccountId.
     * 
     * @return issuerAccountId String - Get the field.
     */
    public String getIssuerAccountId() {
        return issuerAccountId;
    }

    /**
     * Sets the field issuerAccountId.
     * 
     * @param issuerAccountId
     *            String - Set the field issuerAccountId.
     */
    public void setIssuerAccountId(String issuerAccountId) {
        this.issuerAccountId = issuerAccountId;
    }

    /**
     * Returns the field providedUserName.
     * 
     * @return providedUserName String - Get the field.
     */
    public String getProvidedUserName() {
        return providedUserName;
    }

    /**
     * Sets the field providedUserName.
     * 
     * @param providedUserName
     *            String - Set the field providedUserName.
     */
    public void setProvidedUserName(String providedUserName) {
        this.providedUserName = providedUserName;
    }

    /**
     * Returns the field providedPassword.
     * 
     * @return providedPassword String - Get the field.
     */
    public String getProvidedPassword() {
        return providedPassword;
    }

    /**
     * Sets the field providedPassword.
     * 
     * @param providedPassword
     *            String - Set the field providedPassword.
     */
    public void setProvidedPassword(String providedPassword) {
        this.providedPassword = providedPassword;
    }

    /**
     * Returns the field providedRegistrationToken.
     * 
     * @return providedRegistrationToken String - Get the field.
     */
    public String getProvidedRegistrationToken() {
        return providedRegistrationToken;
    }

    /**
     * Sets the field providedRegistrationToken.
     * 
     * @param providedRegistrationToken
     *            String - Set the field providedRegistrationToken.
     */
    public void setProvidedRegistrationToken(String providedRegistrationToken) {
        this.providedRegistrationToken = providedRegistrationToken;
    }

    /**
     * Returns the field accountNickName.
     * 
     * @return accountNickName String - Get the field.
     */
    public String getAccountNickName() {
        return accountNickName;
    }

    /**
     * Sets the field accountNickName.
     * 
     * @param accountNickName
     *            String - Set the field accountNickName.
     */
    public void setAccountNickName(String accountNickName) {
        this.accountNickName = accountNickName;
    }

    /**
     * Returns the field registrationToken.
     * 
     * @return registrationToken String - Get the field.
     */
    public String getRegistrationToken() {
        return registrationToken;
    }

    /**
     * Sets the field registrationToken.
     * 
     * @param registrationToken
     *            String - Set the field registrationToken.
     */
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
