package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Provides Information about card's(Credit etc),Eg:ExpiryDate,PAN first six,PAN
 * last four etc. It is mapped to ARN and to Customer.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "PAYMENT_INSTRUMENTS")
public class PaymentInstrument implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Primary key of Payment Instrument.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * Provides information about ARN_ID.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARN_ID", nullable = false, referencedColumnName = "ID")
    private Arn arn;

    /**
     * Provides information about CUSTOMER_ID.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false, referencedColumnName = "ID")
    private Customer customer;

    /**
     * Encrypted base64 encoded PAN.
     */
    @Column(name = "PAN_ENCRYPTED", nullable = false)
    private String encryptedPan;

    /**
     * Expiration month and year.
     */
    @Column(name = "EXP_MONTH_YEAR", nullable = false)
    private String expiryMonthYear;

    /**
     * Indicates if the payment instrument is active.
     */
    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive = false;

    /**
     * Issuer provided account ID.
     */
    @Column(name = "ISSUER_ACCOUNT_ID", nullable = false)
    private String issuerAccountId;

    /**
     * Account nickname as provided by the issuer.
     */
    @Column(name = "NICKNAME", nullable = false)
    private String nickName;

    /**
     * First six of the PAN.
     */
    @Column(name = "PAN_FIRST_SIX_ENCRYPTED", nullable = false)
    private String panFirstSixDigits;

    /**
     * Hashed version of the PAN.
     */
    @Column(name = "PAN_HASH", nullable = false)
    private String panHash;

    /**
     * Last four of the PAN.
     */
    @Column(name = "PAN_LAST_FOUR_ENCRYPTED", nullable = false)
    private String panLastFourDigits;

    /**
     * Length required to generate the token.
     */
    @Column(name = "PAN_LENGTH")
    private int panLength;

    /**
     * For the Token History Payment instrument ID is the primary key.
     */
    @OneToMany(mappedBy = "paymentInstrument")
    private List<TokenHistory> tokenHistories;

    /**
     * @return Arn object is returned.
     */
    public Arn getArn() {
        return arn;
    }

    /**
     * @return Customer object is returned.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return Encrypted PAN.
     */
    public String getEncryptedPan() {
        return encryptedPan;
    }

    /**
     * @return Expiry Month and Year.
     */
    public String getExpiryMonthYear() {
        return expiryMonthYear;
    }

    /**
     * @return primary key.
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @return Issuer Acount id.
     */
    public String getIssuerAccountId() {
        return issuerAccountId;
    }

    /**
     * 
     * @return Nick name of the customer.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 
     * @return Pan First six digits.
     */
    public String getPanFirstSixDigits() {
        return panFirstSixDigits;
    }

    /**
     * @return Pan Hash.
     */
    public String getPanHash() {
        return panHash;
    }

    /**
     * 
     * @return Last four digits of card.
     */
    public String getPanLastFourDigits() {
        return panLastFourDigits;
    }

    /**
     * @return the list of token histories.
     */
    public List<TokenHistory> getTokenHistories() {
        return tokenHistories;
    }

    /**
     * @param arn
     *            Arn object.
     */
    public void setArn(Arn arn) {
        this.arn = arn;
    }

    /**
     * @param customer
     *            Customer object.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @param encryptedPan
     *            Encrypted PAN.
     */
    public void setEncryptedPan(String encryptedPan) {
        this.encryptedPan = encryptedPan;
    }

    /**
     * @param expiryMonthYear
     *            Expiry Month and year.
     */
    public void setExpiryMonthYear(String expiryMonthYear) {
        this.expiryMonthYear = expiryMonthYear;
    }

    /**
     * @param id
     *            Payment Instrumetn ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param issuerAccountId
     *            Customer's account id.
     */
    public void setIssuerAccountId(String issuerAccountId) {
        this.issuerAccountId = issuerAccountId;
    }

    /**
     * @param nickName
     *            Nickname of the customer.
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @param panFirstSixDigits
     *            First Six Digits of the Payment Instrument.
     */
    public void setPanFirstSixDigits(String panFirstSixDigits) {
        this.panFirstSixDigits = panFirstSixDigits;
    }

    /**
     * @param panHash
     *            Pan Hash.
     */
    public void setPanHash(String panHash) {
        this.panHash = panHash;
    }

    /**
     * @param panLastFourDigits
     *            Last Four Digits of the Payment Instrument.
     */
    public void setPanLastFourDigits(String panLastFourDigits) {
        this.panLastFourDigits = panLastFourDigits;
    }

    /**
     * @param tokenHistories
     *            Token histories.
     */
    public void setTokenHistories(List<TokenHistory> tokenHistories) {
        this.tokenHistories = tokenHistories;
    }

    /**
     * Returns the field isActive.
     * 
     * @return isActive boolean - Get the field.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the field isActive.
     * 
     * @param isActive
     *            boolean - Set the field isActive.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Returns the field panLength.
     * 
     * @return panLength int - Get the field.
     */
    public int getPanLength() {
        return panLength;
    }

    /**
     * Sets the field panLength.
     * 
     * @param panLength
     *            int - Set the field panLength.
     */
    public void setPanLength(int panLength) {
        this.panLength = panLength;
    }

}
