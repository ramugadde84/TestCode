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
 * This entity maps to TOKEN_HISTORIES table. TOKEN_HISTORIES table contains
 * History of tokens
 * 
 * @author Ramu
 * 
 */
@Entity
@Table(name = "TOKEN_HISTORIES")
public class TokenHistory implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -476826352129915651L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TOKEN_PAN_ENCRYTPED", nullable = false)
    private String encryptedTokenPan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_INSTRUMENT_ID", referencedColumnName = "ID")
    private PaymentInstrument paymentInstrument;

    @Column(name = "CREATED_AT", nullable = false)
    private Date tokenCreatedAt;

    @Column(name = "EXPIRED_AT", nullable = false)
    private Date tokenExpiredAt;

    @Column(name = "TOKEN_EXP_MONTH_YEAR", nullable = false)
    private String tokenExpiryMonthYear;

    /**
     * @return encryptedTokenPan- gets the Token PAN in encrypted form
     */
    public String getEncryptedTokenPan() {
        return encryptedTokenPan;
    }

    /**
     * @return id - gets the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return paymentInstrument - gets Payment instrument related to the token
     */
    public PaymentInstrument getPaymentInstrument() {
        return paymentInstrument;
    }

    /**
     * @return tokenCreatedAt- gets the Timestamp token which was created
     */
    public Date getTokenCreatedAt() {
        return tokenCreatedAt;
    }

    /**
     * @return tokenExpiredAt- gets the timestamp token which was expired
     */
    public Date getTokenExpiredAt() {
        return tokenExpiredAt;
    }

    /**
     * @return tokenExpiryMonthYear - gets the token expiration month and year
     */
    public String getTokenExpiryMonthYear() {
        return tokenExpiryMonthYear;
    }

    /**
     * @param encryptedTokenPan
     *            - sets the Token PAN in encrypted form
     */
    public void setEncryptedTokenPan(String encryptedTokenPan) {
        this.encryptedTokenPan = encryptedTokenPan;
    }

    /**
     * @param id
     *            - sets the field id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param paymentInstrument
     *            - sets Payment instrument related to the token
     */
    public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }

    /**
     * @param tokenCreatedAt
     *            - sets the Timestamp token which was created
     */
    public void setTokenCreatedAt(Date tokenCreatedAt) {
        this.tokenCreatedAt = tokenCreatedAt;
    }

    /**
     * @param tokenExpiredAt
     *            - sets the timestamp token which was expired
     */
    public void setTokenExpiredAt(Date tokenExpiredAt) {
        this.tokenExpiredAt = tokenExpiredAt;
    }

    /**
     * @param tokenExpiryMonthYear
     *            - sets the token expiration month and year
     */
    public void setTokenExpiryMonthYear(String tokenExpiryMonthYear) {
        this.tokenExpiryMonthYear = tokenExpiryMonthYear;
    }

}
