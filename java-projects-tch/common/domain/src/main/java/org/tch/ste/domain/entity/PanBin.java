package org.tch.ste.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity which contains Token Information related to Real BIN,this Entity have
 * a mapping with Token Bin Mapping(which contains information of PAN_BIN_ID and
 * TOKEN_BIN_ID).
 * 
 * @author Ramu.Gadde
 * @version $Id$
 * 
 */
@Entity
@Table(name = "PAN_BINS")
public class PanBin implements Serializable {

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = -2462763821955324172L;

    /**
     * The ID which is a primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    /**
     * Maximum amount to allow for an expired token.
     */
    @Column(name = "MAX_AMT_FOR_EXPIRED_TOKEN", nullable = false)
    private Integer maxAmountForExpiredToken;

    /**
     * Maximum time in minutes after token expiration to allow expired token use
     * for limited amount transactions.
     */
    @Column(name = "MAX_MINUTES_EXPIRED_TOKEN", nullable = false)
    private Integer maxMinutesExpiredToken;

    /**
     * Maximum number of tokens that should be issued in a day.
     */
    @Column(name = "MAX_TOKENS_PER_DAY", nullable = false)
    private Integer maxTokensPerDay;

    /**
     * Pull of tokens enabled for tokens created from this BIN.
     */
    @Column(name = "PULL_ENABLED", nullable = false)
    private Integer pullEnabled;

    /**
     * Push token on create.
     */
    @Column(name = "PUSH_ON_CREATE", nullable = false)
    private Integer pushOnCreate;

    /**
     * "Real" BIN.
     */

    @Column(name = "BIN", nullable = false)
    private String realBin;

    /**
     * Token expiration after a use of a particular amount.
     */
    @Column(name = "TOKEN_EXPIRATION_AMOUNT", nullable = false)
    private Integer tokenExpirationAmount;

    /**
     * Token expiration after a number of uses.
     */
    @Column(name = "TOKEN_EXPIRATION_USES", nullable = false)
    private Integer tokenExpirationUses;

    /**
     * Token Expiration time in minutes.
     */
    @Column(name = "TOKEN_EXPIRATION_MINUTES", nullable = false)
    private Integer tokenExpriationTimeInMinutes;

    /**
     * Returns primary key value.
     * 
     * @return primary key value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Max amount for Expired Token.
     */
    public Integer getMaxAmountForExpiredToken() {
        return maxAmountForExpiredToken;
    }

    /**
     * @return Max minutes for Expired Token.
     */
    public Integer getMaxMinutesExpiredToken() {
        return maxMinutesExpiredToken;
    }

    /**
     * @return Max Tokens per day.
     */
    public Integer getMaxTokensPerDay() {
        return maxTokensPerDay;
    }

    /**
     * @return Pull Enable or not after create a token.
     */
    public Integer getPullEnabled() {
        return pullEnabled;
    }

    /**
     * @return Push Enable or not after create a token.
     */
    public Integer getPushOnCreate() {
        return pushOnCreate;
    }

    /**
     * @return Real bin.
     */
    public String getRealBin() {
        return realBin;
    }

    /**
     * @return list of tokens and bin mapping information.
     */
    /*
     * public List<TokenBinMapping> getTokenBinMappings() { return
     * tokenBinMappings; }
     */

    /**
     * @return Token Expiration amount.
     */
    public Integer getTokenExpirationAmount() {
        return tokenExpirationAmount;
    }

    /**
     * @return Token Expiration on uses.
     */
    public Integer getTokenExpirationUses() {
        return tokenExpirationUses;
    }

    /**
     * @return Token Expiration time in minutes.
     */
    public Integer getTokenExpriationTimeInMinutes() {
        return tokenExpriationTimeInMinutes;
    }

    /**
     * Stores the Primary key value.
     * 
     * @param id
     *            which contains primary key value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param maxAmountForExpiredToken
     *            Maximum amount to allow for an expired token.
     */
    public void setMaxAmountForExpiredToken(Integer maxAmountForExpiredToken) {
        this.maxAmountForExpiredToken = maxAmountForExpiredToken;
    }

    /**
     * @param maxMinutesExpiredToken
     *            Maximum time in minutes after token expiration to allow
     *            expired token use for limited amount transactions.
     */
    public void setMaxMinutesExpiredToken(Integer maxMinutesExpiredToken) {
        this.maxMinutesExpiredToken = maxMinutesExpiredToken;
    }

    /**
     * @param maxTokensPerDay
     *            Maximum number of tokens that should be issued in a day.
     */
    public void setMaxTokensPerDay(Integer maxTokensPerDay) {
        this.maxTokensPerDay = maxTokensPerDay;
    }

    /**
     * @param pullEnabled
     *            Pull of tokens enabled for tokens created from this BIN.
     */
    public void setPullEnabled(Integer pullEnabled) {
        this.pullEnabled = pullEnabled;
    }

    /**
     * @param pushOnCreate
     *            Push token on create.
     */
    public void setPushOnCreate(Integer pushOnCreate) {
        this.pushOnCreate = pushOnCreate;
    }

    /**
     * @param realBin
     *            "Real" BIN.
     */
    public void setRealBin(String realBin) {
        this.realBin = realBin;
    }

    /**
     * @param tokenBinMappings
     *            Token to Bin mapping it contains PAN_BIN_ID and TOKEN_BIN_ID.
     */
    /*
     * public void setTokenBinMappings(List<TokenBinMapping> tokenBinMappings) {
     * this.tokenBinMappings = tokenBinMappings; }
     */

    /**
     * @param tokenExpirationAmount
     *            Amount to Expire Token.
     */
    public void setTokenExpirationAmount(Integer tokenExpirationAmount) {
        this.tokenExpirationAmount = tokenExpirationAmount;
    }

    /**
     * @param tokenExpirationUses
     *            Uses to Expire Token.
     */
    public void setTokenExpirationUses(Integer tokenExpirationUses) {
        this.tokenExpirationUses = tokenExpirationUses;
    }

    /**
     * @param tokenExpriationTimeInMinutes
     *            Minutes to Expire Token.
     */
    public void setTokenExpriationTimeInMinutes(Integer tokenExpriationTimeInMinutes) {
        this.tokenExpriationTimeInMinutes = tokenExpriationTimeInMinutes;
    }

}
