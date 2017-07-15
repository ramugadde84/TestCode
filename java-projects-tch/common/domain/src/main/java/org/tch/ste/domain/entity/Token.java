package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

/**
 * Provides Token information.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "TOKENS")
public class Token implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    /**
     * List of Detokenization requests.
     */
    @OneToMany(mappedBy = "token")
    private List<DetokenizationRequest> detokenizationRequests;

    /**
     * Encrypted pan token.
     */
    @Column(name = "TOKEN_PAN_ENCRYPTED", nullable = false)
    private String encryptedPanToken;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    /**
     * List of Permitted Token Requester arn token.
     */
    @OneToMany(mappedBy = "token")
    private List<PermittedTokenRequestorArnToken> permittedTokenRequestorArnsTokens;

    /**
     * Token created date.
     */
    @Column(name = "CREATED_AT", nullable = false)
    private Date tokencreated;

    /**
     * Token Expired month year.
     */
    @Column(name = "TOKEN_EXP_MONTH_YEAR", nullable = false)
    private String tokenExpMonthYear;

    /**
     * Token Pan Bin first six.
     */
    @Column(name = "TOKEN_PAN_FIRST_SIX_ENCRYPTED", nullable = false)
    private String tokenPanFirstSixEncrypt;

    /**
     * Token pan hash.
     */
    @Column(name = "TOKEN_PAN_HASH", nullable = false)
    private String tokenPanHash;

    @Column(name = "TOKEN_PAN_LAST_FOUR_ENCRYPTED", nullable = false)
    private String tokenPanLastFourEncrypt;

    /**
     * Token pos entry mode information.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXPECTED_TOKEN_POS_ENTRY_MODE", referencedColumnName = "ID", nullable = false)
    private TokenPosEntryMode tokenPosEntryModes;

    @Transient
    private String plainTextToken;

    /**
     * @return list of detokenization request.
     */
    public List<DetokenizationRequest> getDetokenizationRequests() {
        return detokenizationRequests;
    }

    /**
     * @return encrypted pan token.
     */
    public String getEncryptedPanToken() {
        return encryptedPanToken;
    }

    /**
     * @return primary key.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return List of Permitted Token Requester arns token.
     */
    public List<PermittedTokenRequestorArnToken> getPermittedTokenRequestorArnsTokens() {
        return permittedTokenRequestorArnsTokens;
    }

    /**
     * @return Token created date.
     */
    public Date getTokencreated() {
        return tokencreated;
    }

    /**
     * @return Token Expired Month year.
     */
    public String getTokenExpMonthYear() {
        return tokenExpMonthYear;
    }

    /**
     * @return the tokenPanFirstSixEncrypt
     */
    public String getTokenPanFirstSixEncrypt() {
        return tokenPanFirstSixEncrypt;
    }

    /**
     * @return token pan hash.
     */
    public String getTokenPanHash() {
        return tokenPanHash;
    }

    /**
     * @return the tokenPanLastFourEncrypt
     */
    public String getTokenPanLastFourEncrypt() {
        return tokenPanLastFourEncrypt;
    }

    /**
     * @return token pos entry modes.
     */
    public TokenPosEntryMode getTokenPosEntryModes() {
        return tokenPosEntryModes;
    }

    /**
     * @param detokenizationRequests
     *            list of detokenization requests.
     */
    public void setDetokenizationRequests(List<DetokenizationRequest> detokenizationRequests) {
        this.detokenizationRequests = detokenizationRequests;
    }

    /**
     * @param encryptedPanToken
     *            encrypted pan token.
     */
    public void setEncryptedPanToken(String encryptedPanToken) {
        this.encryptedPanToken = encryptedPanToken;
    }

    /**
     * @param id
     *            primary key.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param permittedTokenRequestorArnsTokens
     *            lists of permitted token requester arns tokens.
     */
    public void setPermittedTokenRequestorArnsTokens(
            List<PermittedTokenRequestorArnToken> permittedTokenRequestorArnsTokens) {
        this.permittedTokenRequestorArnsTokens = permittedTokenRequestorArnsTokens;
    }

    /**
     * @param tokencreated
     *            date of token creation.
     */
    public void setTokencreated(Date tokencreated) {
        this.tokencreated = tokencreated;
    }

    /**
     * @param tokenExpMonthYear
     *            date of token exp month year.
     */
    public void setTokenExpMonthYear(String tokenExpMonthYear) {
        this.tokenExpMonthYear = tokenExpMonthYear;
    }

    /**
     * @param tokenPanFirstSixEncrypt
     *            the tokenPanFirstSixEncrypt to set
     */
    public void setTokenPanFirstSixEncrypt(String tokenPanFirstSixEncrypt) {
        this.tokenPanFirstSixEncrypt = tokenPanFirstSixEncrypt;
    }

    /**
     * @param tokenPanHash
     *            token pan hash.
     */
    public void setTokenPanHash(String tokenPanHash) {
        this.tokenPanHash = tokenPanHash;
    }

    /**
     * @param tokenPanLastFourEncrypt
     *            the tokenPanLastFourEncrypt to set
     */
    public void setTokenPanLastFourEncrypt(String tokenPanLastFourEncrypt) {
        this.tokenPanLastFourEncrypt = tokenPanLastFourEncrypt;
    }

    /**
     * @param tokenPosEntryModes
     *            token pos entry modes.
     */
    public void setTokenPosEntryModes(TokenPosEntryMode tokenPosEntryModes) {
        this.tokenPosEntryModes = tokenPosEntryModes;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the plainTextToken
     */
    public String getPlainTextToken() {
        return plainTextToken;
    }

    /**
     * @param plainTextToken
     *            the plainTextToken to set
     */
    public void setPlainTextToken(String plainTextToken) {
        this.plainTextToken = plainTextToken;
    }

}
