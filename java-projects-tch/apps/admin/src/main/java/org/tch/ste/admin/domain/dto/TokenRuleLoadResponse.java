/**
 * 
 */
package org.tch.ste.admin.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author ramug
 * 
 */
public class TokenRuleLoadResponse implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 2889873947324056325L;

    private List<String> bins;

    private String dollarAmountForExpireDpiErrorMessage;

    private String dpiPerDayErrorMessage;

    private String numberOfDetokenizedRequestsErrorMessage;

    private String realBinErrorMessage;

    private boolean success;

    private String timeInMinutesAfterExpirationForReuseErrorMessage;

    private String tokenExpirationMinutesErrorMessage;

    private String tokenExpireAfterSingleDollarAmountTransactionErrorMessage;

    private String tokenPullEnableErrorMessage;

    private String tokenPushOnCreationErrorMessage;

    private TokenRule tokenRule;

    private String successMessage;

    /**
     * @return the bins
     */
    public List<String> getBins() {
        return bins;
    }

    /**
     * @return the dollarAmountForExpireDpiErrorMessage
     */
    public String getDollarAmountForExpireDpiErrorMessage() {
        return dollarAmountForExpireDpiErrorMessage;
    }

    /**
     * @return the dpiPerDayErrorMessage
     */
    public String getDpiPerDayErrorMessage() {
        return dpiPerDayErrorMessage;
    }

    /**
     * @return the numberOfDetokenizedRequestsErrorMessage
     */
    public String getNumberOfDetokenizedRequestsErrorMessage() {
        return numberOfDetokenizedRequestsErrorMessage;
    }

    /**
     * @return the realBinErrorMessage
     */
    public String getRealBinErrorMessage() {
        return realBinErrorMessage;
    }

    /**
     * @return the timeInMinutesAfterExpirationForReuseErrorMessage
     */
    public String getTimeInMinutesAfterExpirationForReuseErrorMessage() {
        return timeInMinutesAfterExpirationForReuseErrorMessage;
    }

    /**
     * @return the tokenExpirationMinutesErrorMessage
     */
    public String getTokenExpirationMinutesErrorMessage() {
        return tokenExpirationMinutesErrorMessage;
    }

    /**
     * @return the tokenExpireAfterSingleDollarAmountTransactionErrorMessage
     */
    public String getTokenExpireAfterSingleDollarAmountTransactionErrorMessage() {
        return tokenExpireAfterSingleDollarAmountTransactionErrorMessage;
    }

    /**
     * @return the tokenPullEnableErrorMessage
     */
    public String getTokenPullEnableErrorMessage() {
        return tokenPullEnableErrorMessage;
    }

    /**
     * @return the tokenPushOnCreationErrorMessage
     */
    public String getTokenPushOnCreationErrorMessage() {
        return tokenPushOnCreationErrorMessage;
    }

    /**
     * @return the tokenRule
     */
    public TokenRule getTokenRule() {
        return tokenRule;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param bins
     *            the bins to set
     */
    public void setBins(List<String> bins) {
        this.bins = bins;
    }

    /**
     * @param dollarAmountForExpireDpiErrorMessage
     *            the dollarAmountForExpireDpiErrorMessage to set
     */
    public void setDollarAmountForExpireDpiErrorMessage(String dollarAmountForExpireDpiErrorMessage) {
        this.dollarAmountForExpireDpiErrorMessage = dollarAmountForExpireDpiErrorMessage;
    }

    /**
     * @param dpiPerDayErrorMessage
     *            the dpiPerDayErrorMessage to set
     */
    public void setDpiPerDayErrorMessage(String dpiPerDayErrorMessage) {
        this.dpiPerDayErrorMessage = dpiPerDayErrorMessage;
    }

    /**
     * @param numberOfDetokenizedRequestsErrorMessage
     *            the numberOfDetokenizedRequestsErrorMessage to set
     */
    public void setNumberOfDetokenizedRequestsErrorMessage(String numberOfDetokenizedRequestsErrorMessage) {
        this.numberOfDetokenizedRequestsErrorMessage = numberOfDetokenizedRequestsErrorMessage;
    }

    /**
     * @param realBinErrorMessage
     *            the realBinErrorMessage to set
     */
    public void setRealBinErrorMessage(String realBinErrorMessage) {
        this.realBinErrorMessage = realBinErrorMessage;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @param timeInMinutesAfterExpirationForReuseErrorMessage
     *            the timeInMinutesAfterExpirationForReuseErrorMessage to set
     */
    public void setTimeInMinutesAfterExpirationForReuseErrorMessage(
            String timeInMinutesAfterExpirationForReuseErrorMessage) {
        this.timeInMinutesAfterExpirationForReuseErrorMessage = timeInMinutesAfterExpirationForReuseErrorMessage;
    }

    /**
     * @param tokenExpirationMinutesErrorMessage
     *            the tokenExpirationMinutesErrorMessage to set
     */
    public void setTokenExpirationMinutesErrorMessage(String tokenExpirationMinutesErrorMessage) {
        this.tokenExpirationMinutesErrorMessage = tokenExpirationMinutesErrorMessage;
    }

    /**
     * @param tokenExpireAfterSingleDollarAmountTransactionErrorMessage
     *            the tokenExpireAfterSingleDollarAmountTransactionErrorMessage
     *            to set
     */
    public void setTokenExpireAfterSingleDollarAmountTransactionErrorMessage(
            String tokenExpireAfterSingleDollarAmountTransactionErrorMessage) {
        this.tokenExpireAfterSingleDollarAmountTransactionErrorMessage = tokenExpireAfterSingleDollarAmountTransactionErrorMessage;
    }

    /**
     * @param tokenPullEnableErrorMessage
     *            the tokenPullEnableErrorMessage to set
     */
    public void setTokenPullEnableErrorMessage(String tokenPullEnableErrorMessage) {
        this.tokenPullEnableErrorMessage = tokenPullEnableErrorMessage;
    }

    /**
     * @param tokenPushOnCreationErrorMessage
     *            the tokenPushOnCreationErrorMessage to set
     */
    public void setTokenPushOnCreationErrorMessage(String tokenPushOnCreationErrorMessage) {
        this.tokenPushOnCreationErrorMessage = tokenPushOnCreationErrorMessage;
    }

    /**
     * @param tokenRule
     *            the tokenRule to set
     */
    public void setTokenRule(TokenRule tokenRule) {
        this.tokenRule = tokenRule;
    }

    /**
     * @return the successMessage
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * @param successMessage
     *            the successMessage to set
     */
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

}
