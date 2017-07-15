/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_DOLLAR_AMOUNT_EXPIRE_DPI;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_DPI_PER_DAY;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_EMPTY_CHECK;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_NULL_CHECK;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_NUMBER_OF_DETOKENIZED_REQUEST;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REAL_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRATION_MINUTES;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_PULL_ENABLE;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_TOKEN_PUSH_CREATION;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_WRONG_PATTERN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_LENGTH_COMMON_EXCEEDS;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tch.ste.admin.domain.dto.TokenRule;

/**
 * Token Rules Custom validations.
 * 
 * @author ramug
 */
@Service("tokenRulesValidator")
public class TokenRulesValidator implements Validator {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return TokenRule.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        TokenRule tokenRule = (TokenRule) target;
        String dpiPerDay = tokenRule.getDpiPerDay();
        String dollarAmountForExpireDpi = tokenRule.getDollarAmountForExpireDpi();
        String detokenizedRequests = tokenRule.getNumberOfDetokenizedRequests();
        String realBin = tokenRule.getRealBin();
        String timeInMinutesAfterExpirationForReuse = tokenRule.getTimeInMinutesAfterExpirationForReuse();
        String tokenExpirationMinutes = tokenRule.getTokenExpirationMinutes();
        String tokenExpireAfterSingleDollarAmountTransaction = tokenRule
                .getTokenExpireAfterSingleDollarAmountTransaction();
        String tokenPullEnable = tokenRule.getTokenPullEnable();
        String tokenPushOnCreation = tokenRule.getTokenPushOnCreation();
        if (validateEmpty(dpiPerDay)) {
            errors.rejectValue(C_DPI_PER_DAY, C_EMPTY_CHECK);
        } else if (validatePattern(dpiPerDay)) {
            errors.rejectValue(C_DPI_PER_DAY, C_WRONG_PATTERN);
        } else if (dpiPerDay != null && dpiPerDay.length() > 9) {
            errors.rejectValue(C_DPI_PER_DAY, C_LENGTH_COMMON_EXCEEDS);
        }

        if (validateEmpty(dollarAmountForExpireDpi)) {
            errors.rejectValue(C_DOLLAR_AMOUNT_EXPIRE_DPI, C_EMPTY_CHECK);
        } else if (validatePattern(dollarAmountForExpireDpi)) {
            errors.rejectValue(C_DOLLAR_AMOUNT_EXPIRE_DPI, C_WRONG_PATTERN);
        } else if (dollarAmountForExpireDpi != null && dollarAmountForExpireDpi.length() > 9) {
            errors.rejectValue(C_DOLLAR_AMOUNT_EXPIRE_DPI, C_LENGTH_COMMON_EXCEEDS);
        }
        if (validateEmpty(detokenizedRequests)) {
            errors.rejectValue(C_NUMBER_OF_DETOKENIZED_REQUEST, C_EMPTY_CHECK);
        } else if (validatePattern(detokenizedRequests)) {
            errors.rejectValue(C_NUMBER_OF_DETOKENIZED_REQUEST, C_WRONG_PATTERN);
        } else if (detokenizedRequests != null && detokenizedRequests.length() > 9) {
            errors.rejectValue(C_NUMBER_OF_DETOKENIZED_REQUEST, C_LENGTH_COMMON_EXCEEDS);
        }
        if (validateEmpty(realBin)) {
            errors.rejectValue(C_REAL_BIN, C_EMPTY_CHECK);
        } else if (validatePattern(realBin)) {
            errors.rejectValue(C_REAL_BIN, C_EMPTY_CHECK);
        }
        if (validateEmpty(timeInMinutesAfterExpirationForReuse)) {
            errors.rejectValue(C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE, C_EMPTY_CHECK);
        } else if (validatePattern(timeInMinutesAfterExpirationForReuse)) {
            errors.rejectValue(C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE, C_WRONG_PATTERN);
        } else if (timeInMinutesAfterExpirationForReuse != null && timeInMinutesAfterExpirationForReuse.length() > 9) {
            errors.rejectValue(C_TIME_IN_MINUTES_AFTER_EXPIRATION_REUSE, C_LENGTH_COMMON_EXCEEDS);
        }
        if (validateEmpty(tokenExpirationMinutes)) {
            errors.rejectValue(C_TOKEN_EXPIRATION_MINUTES, C_EMPTY_CHECK);
        } else if (validatePattern(tokenExpirationMinutes)) {
            errors.rejectValue(C_TOKEN_EXPIRATION_MINUTES, C_WRONG_PATTERN);
        } else if (tokenExpirationMinutes != null && tokenExpirationMinutes.length() > 9) {
            errors.rejectValue(C_TOKEN_EXPIRATION_MINUTES, C_LENGTH_COMMON_EXCEEDS);
        }
        if (validateEmpty(tokenExpireAfterSingleDollarAmountTransaction)) {
            errors.rejectValue(C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION, C_EMPTY_CHECK);
        } else if (validatePattern(tokenExpireAfterSingleDollarAmountTransaction)) {
            errors.rejectValue(C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION, C_WRONG_PATTERN);
        } else if (tokenExpireAfterSingleDollarAmountTransaction != null
                && tokenExpireAfterSingleDollarAmountTransaction.length() > 9) {
            errors.rejectValue(C_TOKEN_EXPIRE_AFTER_SINGLE_DOLLAR_AMOUNT_TRANSACTION, C_LENGTH_COMMON_EXCEEDS);
        }
        if (validateNull(tokenPullEnable)) {
            errors.rejectValue(C_TOKEN_PULL_ENABLE, C_NULL_CHECK);
        } else if (validatePattern(tokenPullEnable)) {
            errors.rejectValue(C_TOKEN_PULL_ENABLE, C_WRONG_PATTERN);
        }
        if (validateNull(tokenPushOnCreation)) {
            errors.rejectValue(C_TOKEN_PUSH_CREATION, C_NULL_CHECK);
        } else if (validatePattern(tokenPushOnCreation)) {
            errors.rejectValue(C_TOKEN_PUSH_CREATION, C_WRONG_PATTERN);
        }

    }

    /**
     * Validates if it is empty.
     * 
     * @param value
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean validateEmpty(String value) {
        return isEmpty(value);
    }

    /**
     * Validates if it is empty.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isEmpty(String inputValue) {
        return inputValue != null && inputValue.trim().isEmpty();
    }

    /**
     * Validates the pattern.
     * 
     * @param value
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean validatePattern(String value) {
        return !isPattern(value);
    }

    /**
     * Validates the pattern.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isPattern(String inputValue) {
        return inputValue.matches("^(0|[1-9][0-9]*)$");
    }

    /**
     * Validates for Null.
     * 
     * @param value
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean validateNull(String value) {
        return isNull(value);
    }

    /**
     * Validates for Null.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isNull(String inputValue) {
        return inputValue == null;
    }

}
