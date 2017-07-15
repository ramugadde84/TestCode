/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

/**
 * Content.
 * 
 * @author Karthik.
 * 
 */
public class PaymentInstrumentPreloadContent {
    private int type;

    private String pan;

    private String expirationDate;

    private String issuerCustomerId;

    private String issuerAccountId;

    private String userName;

    private String password;

    private String token;

    private String nickName;

    private String arn;

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
     * Returns the field pan.
     * 
     * @return pan String - Get the field.
     */
    public String getPan() {
        return pan;
    }

    /**
     * Sets the field pan.
     * 
     * @param pan
     *            String - Set the field pan.
     */
    public void setPan(String pan) {
        this.pan = pan;
    }

    /**
     * Returns the field expirationDate.
     * 
     * @return expirationDate String - Get the field.
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the field expirationDate.
     * 
     * @param expirationDate
     *            String - Set the field expirationDate.
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
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
     * Returns the field token.
     * 
     * @return token String - Get the field.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the field token.
     * 
     * @param token
     *            String - Set the field token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Returns the field nickName.
     * 
     * @return nickName String - Get the field.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets the field nickName.
     * 
     * @param nickName
     *            String - Set the field nickName.
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
