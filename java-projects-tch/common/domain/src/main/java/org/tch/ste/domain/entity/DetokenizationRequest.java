package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class contains inforamtion about the DeTokenization requests.
 * 
 * @author sharduls
 * 
 */
@Entity
@Table(name = "DETOKENIZATION_REQUESTS")
public class DetokenizationRequest implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3067244254581046943L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "FAILURE_REASON_CODE")
    private String failureReasonCode;

    @Column(name = "NETWORK_TR_ID")
    private String networkTrId;

    @Column(name = "REQUEST_DATETIME")
    private Date requestDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOKEN_ID", referencedColumnName = "ID", nullable = false)
    private Token token;

    @Column(name = "TOKEN_EXP_MONTH_YEAR")
    private String tokenExpMonthYear;

    @Column(name = "TOKEN_PAN_ENCRYPTED")
    private String tokenPanEncrypted;

    @Column(name = "TOKEN_PAN_FIRST_SIX")
    private String tokenPanFirstSix;

    @Column(name = "TOKEN_PAN_HASH")
    private String tokenPanHash;

    @Column(name = "TOKEN_PAN_LAST_FOUR")
    private String tokenPanLastFour;

    @Column(name = "WAS_SUCCESSFUL")
    private boolean wasSuccessful;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    /**
     * @return Token - The token.
     */
    public Token getToken() {
        return token;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param token
     *            Token - The token.
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Gets the field customerId.
     * 
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the parameter customerId to the field customerId.
     * 
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the field failureReasonCode.
     * 
     * @return the failureReasonCode
     */
    public String getFailureReasonCode() {
        return failureReasonCode;
    }

    /**
     * Sets the parameter failureReasonCode to the field failureReasonCode.
     * 
     * @param failureReasonCode
     *            the failureReasonCode to set
     */
    public void setFailureReasonCode(String failureReasonCode) {
        this.failureReasonCode = failureReasonCode;
    }

    /**
     * Gets the field networkTrId.
     * 
     * @return the networkTrId
     */
    public String getNetworkTrId() {
        return networkTrId;
    }

    /**
     * Sets the parameter networkTrId to the field networkTrId.
     * 
     * @param networkTrId
     *            the networkTrId to set
     */
    public void setNetworkTrId(String networkTrId) {
        this.networkTrId = networkTrId;
    }

    /**
     * Gets the field requestDateTime.
     * 
     * @return the requestDateTime
     */
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    /**
     * Sets the parameter requestDateTime to the field requestDateTime.
     * 
     * @param requestDateTime
     *            the requestDateTime to set
     */
    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    /**
     * Gets the field tokenExpMonthYear.
     * 
     * @return the tokenExpMonthYear
     */
    public String getTokenExpMonthYear() {
        return tokenExpMonthYear;
    }

    /**
     * Sets the parameter tokenExpMonthYear to the field tokenExpMonthYear.
     * 
     * @param tokenExpMonthYear
     *            the tokenExpMonthYear to set
     */
    public void setTokenExpMonthYear(String tokenExpMonthYear) {
        this.tokenExpMonthYear = tokenExpMonthYear;
    }

    /**
     * Gets the field tokenPanEncrypted.
     * 
     * @return the tokenPanEncrypted
     */
    public String getTokenPanEncrypted() {
        return tokenPanEncrypted;
    }

    /**
     * Sets the parameter tokenPanEncrypted to the field tokenPanEncrypted.
     * 
     * @param tokenPanEncrypted
     *            the tokenPanEncrypted to set
     */
    public void setTokenPanEncrypted(String tokenPanEncrypted) {
        this.tokenPanEncrypted = tokenPanEncrypted;
    }

    /**
     * Gets the field tokenPanFirstSix.
     * 
     * @return the tokenPanFirstSix
     */
    public String getTokenPanFirstSix() {
        return tokenPanFirstSix;
    }

    /**
     * Sets the parameter tokenPanFirstSix to the field tokenPanFirstSix.
     * 
     * @param tokenPanFirstSix
     *            the tokenPanFirstSix to set
     */
    public void setTokenPanFirstSix(String tokenPanFirstSix) {
        this.tokenPanFirstSix = tokenPanFirstSix;
    }

    /**
     * Gets the field tokenPanHash.
     * 
     * @return the tokenPanHash
     */
    public String getTokenPanHash() {
        return tokenPanHash;
    }

    /**
     * Sets the parameter tokenPanHash to the field tokenPanHash.
     * 
     * @param tokenPanHash
     *            the tokenPanHash to set
     */
    public void setTokenPanHash(String tokenPanHash) {
        this.tokenPanHash = tokenPanHash;
    }

    /**
     * Gets the field tokenPanLastFour.
     * 
     * @return the tokenPanLastFour
     */
    public String getTokenPanLastFour() {
        return tokenPanLastFour;
    }

    /**
     * Sets the parameter tokenPanLastFour to the field tokenPanLastFour.
     * 
     * @param tokenPanLastFour
     *            the tokenPanLastFour to set
     */
    public void setTokenPanLastFour(String tokenPanLastFour) {
        this.tokenPanLastFour = tokenPanLastFour;
    }

    /**
     * Gets the field wasSuccessful.
     * 
     * @return the wasSuccessful
     */
    public boolean isWasSuccessful() {
        return wasSuccessful;
    }

    /**
     * Sets the parameter wasSuccessful to the field wasSuccessful.
     * 
     * @param wasSuccessful
     *            the wasSuccessful to set
     */
    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    /**
     * Gets the field id.
     * 
     * @return the id
     */
    public long getId() {
        return id;
    }
}
