package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Token Rules.
 * 
 * @author ramug
 * 
 */
public class TokenRule implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7545938403086028610L;

    /**
	 * 
	 */
    private Integer id;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Dollar amount for expire DPI.
     */

    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String dollarAmountForExpireDpi;

    /**
     * No of DPis per day.
     */

    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String dpiPerDay;

    /**
     * Number of Detokenized requests.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String numberOfDetokenizedRequests;

    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String realBin;

    /**
     * Times in minutes after expiration for Reuse.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String timeInMinutesAfterExpirationForReuse;

    /**
     * Token Expiration Minutes.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String tokenExpirationMinutes;

    /**
     * Token Expiration dollar amount.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String tokenExpireAfterSingleDollarAmountTransaction;

    /**
     * Token pull is enabled or not.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String tokenPullEnable;

    /**
     * It will store token Push details after creation of token.
     */
    @Pattern(regexp = "^(0|[1-9][0-9]*)$")
    @NotNull
    @NotEmpty
    @NotBlank
    private String tokenPushOnCreation;

    /**
     * @return dollar amount for epiration of dpi.
     */

    public String getDollarAmountForExpireDpi() {
        return dollarAmountForExpireDpi;
    }

    /**
     * @return dpi per day.
     */
    public String getDpiPerDay() {
        return dpiPerDay;
    }

    /**
     * @return number of Detokenized requests.
     */
    public String getNumberOfDetokenizedRequests() {
        return numberOfDetokenizedRequests;
    }

    /**
     * 
     * @return realBin value.
     */
    public String getRealBin() {
        return realBin;
    }

    /**
     * @return time in minutes after expiratin for reuse.
     */
    public String getTimeInMinutesAfterExpirationForReuse() {
        return timeInMinutesAfterExpirationForReuse;
    }

    /**
     * @return token expiration minutes.
     */
    public String getTokenExpirationMinutes() {
        return tokenExpirationMinutes;
    }

    /**
     * @return token expire after single dollar amount transaction.
     */
    public String getTokenExpireAfterSingleDollarAmountTransaction() {
        return tokenExpireAfterSingleDollarAmountTransaction;
    }

    /**
     * @return token pull enable.
     */
    public String getTokenPullEnable() {
        return tokenPullEnable;
    }

    /**
     * @return It will store token Push details after creation of token.
     */
    public String getTokenPushOnCreation() {
        return tokenPushOnCreation;
    }

    /**
     * @param dollarAmountForExpireDpi
     *            -Dollar Amount for expiration of Dpi.
     */
    public void setDollarAmountForExpireDpi(String dollarAmountForExpireDpi) {
        this.dollarAmountForExpireDpi = dollarAmountForExpireDpi;
    }

    /**
     * @param dpiPerDay
     *            - Dpis per day.
     */
    public void setDpiPerDay(String dpiPerDay) {
        this.dpiPerDay = dpiPerDay;
    }

    /**
     * @param numberOfDetokenizedRequests
     *            - number of Detokenized requests.
     */
    public void setNumberOfDetokenizedRequests(String numberOfDetokenizedRequests) {
        this.numberOfDetokenizedRequests = numberOfDetokenizedRequests;
    }

    /**
     * 
     * @param realBin
     *            - Real Bin value.
     */
    public void setRealBin(String realBin) {
        this.realBin = realBin;
    }

    /**
     * @param timeInMinutesAfterExpirationForReuse
     *            -Time in minutes after expiration for reuse.
     */
    public void setTimeInMinutesAfterExpirationForReuse(String timeInMinutesAfterExpirationForReuse) {
        this.timeInMinutesAfterExpirationForReuse = timeInMinutesAfterExpirationForReuse;
    }

    /**
     * @param tokenExpirationMinutes
     *            - Token Expiration in minutes.
     */
    public void setTokenExpirationMinutes(String tokenExpirationMinutes) {
        this.tokenExpirationMinutes = tokenExpirationMinutes;
    }

    /**
     * @param tokenExpireAfterSingleDollarAmountTransaction
     *            -Token Expiration after single dollar amount transaction.
     */
    public void setTokenExpireAfterSingleDollarAmountTransaction(String tokenExpireAfterSingleDollarAmountTransaction) {
        this.tokenExpireAfterSingleDollarAmountTransaction = tokenExpireAfterSingleDollarAmountTransaction;
    }

    /**
     * @param tokenPullEnable
     *            - Token pull enable or not.
     */
    public void setTokenPullEnable(String tokenPullEnable) {
        this.tokenPullEnable = tokenPullEnable;
    }

    /**
     * @param tokenPushOnCreation
     *            - Token push on creation or not.
     */
    public void setTokenPushOnCreation(String tokenPushOnCreation) {
        this.tokenPushOnCreation = tokenPushOnCreation;
    }

}
