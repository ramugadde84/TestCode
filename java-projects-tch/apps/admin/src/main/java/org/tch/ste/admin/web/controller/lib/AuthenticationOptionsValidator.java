/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_AUTHENTICATION_ACCOUNT_LIST_ENDPOINT;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_AUTHENTICATION_URL_EMPTY;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FAILED_ATTEMPTS_WRONG_PATTERN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FAILED_ATTEMPTS;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.constant.AuthenticationOptionError;
import org.tch.ste.admin.domain.dto.IssuerDetail;

/**
 * Validates the authentication options and applies the business rules as per
 * the use case.
 * 
 * @author Karthik.
 * 
 */
@Service("authenticationOptionsValidator")
public class AuthenticationOptionsValidator implements Validator {
    private static final String JPEG_TYPE = "JPEG";
    private static final String JPG_TYPE = "JPG";

    private static String[] authOption1Fields = new String[] { "authenticationAppUrl" };

    private static String[] authOption3Fields = new String[] { "authErrorMessage", "authLockoutMessage",
            "failedAttemptsToTriggerLockout" };

    private static String[] authOption5Fields = new String[] { "authEndpoint", "authLockoutMessage",
            "accountListEndpoint", "authErrorMessage" };

    private static String[] schemes = { "https" };

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return IssuerDetail.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        IssuerDetail issuerDetail = (IssuerDetail) target;

        String failedAttemptsToTriggerLockout = issuerDetail.getFailedAttemptsToTriggerLockout();
        UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
        // DEF-55 Fix.
        String authLockOutMessage = issuerDetail.getAuthLockoutMessage();
        String authenticationErrorMessage = issuerDetail.getAuthErrorMessage();
        switch (issuerDetail.getAuthMechanism()) {
        case 1:
        case 2:
            for (String field : authOption1Fields) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, NotEmpty.class.getSimpleName());
            }
            String authAppUrl = issuerDetail.getAuthenticationAppUrl();
            boolean isAuthUrlEmpty = (authAppUrl != null) ? authAppUrl.trim().isEmpty() : true;

            if (authAppUrl != null && isAuthUrlEmpty) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL,
                        AdminControllerConstants.C_AUTHENTICATION_URL_EMPTY);
            } else if (authAppUrl != null && !urlValidator.isValid(authAppUrl)) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_APP_URL,
                        AuthenticationOptionError.INVALID_AUTH_APP_URL.name());
            }
            break;
        case 3:
        case 4:
            for (String field : authOption3Fields) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, NotEmpty.class.getSimpleName());
            }

            if (authLockOutMessage != null && authLockOutMessage.length() > 1000) {
                errors.rejectValue(AdminControllerConstants.C_AUTH_LOCK_OUT_MESSAGE,
                        AdminControllerConstants.C_AUTH_LOCK_OUT_MESSAGE_LENGTH_EXCEEDS);
            }
            if (authenticationErrorMessage != null && authenticationErrorMessage.length() > 1000) {
                errors.rejectValue(AdminControllerConstants.C_AUTH_ERROR_MESSAGE,
                        AdminControllerConstants.C_AUTH_ERROR_MESSAGE_LENGTH_EXCEEDS);
            }
            if (failedAttemptsToTriggerLockout != null && failedAttemptsToTriggerLockout.trim().isEmpty()) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_FAILED_ATTEMPTS, C_FAILED_ATTEMPTS);
            } else if (failedAttemptsToTriggerLockout != null
                    && isNumberLengthCheckPattern(failedAttemptsToTriggerLockout)
                    && failedAttemptsToTriggerLockout.length() > 6) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_FAILED_ATTEMPTS,
                        AdminControllerConstants.C_LOCK_OUT_ATTEMTPS_LENGTH);
            } else if (!isNumberPattern(String.valueOf(failedAttemptsToTriggerLockout))) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_FAILED_ATTEMPTS,
                        C_FAILED_ATTEMPTS_WRONG_PATTERN);
            } else if (isNegativePattern(failedAttemptsToTriggerLockout)
                    || Integer.parseInt(failedAttemptsToTriggerLockout) == 0) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_FAILED_ATTEMPTS,
                        AuthenticationOptionError.INVALID_FAILED_ATTEMPTS_TO_LOCKOUT.name());
            }
            validateIssuerLogo(issuerDetail, errors);
            break;
        case 5:
            for (String field : authOption5Fields) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, NotEmpty.class.getSimpleName());
            }
            String authEndPoint = issuerDetail.getAuthEndpoint();
            boolean isAuthEndPointEmpty = (authEndPoint != null) ? authEndPoint.trim().isEmpty() : true;
            // DEF-55 Fix.
            authLockOutMessage = issuerDetail.getAuthLockoutMessage();
            authenticationErrorMessage = issuerDetail.getAuthErrorMessage();
            if (authLockOutMessage != null && authLockOutMessage.length() > 1000) {
                errors.rejectValue(AdminControllerConstants.C_AUTH_LOCK_OUT_MESSAGE,
                        AdminControllerConstants.C_AUTH_LOCK_OUT_MESSAGE_LENGTH_EXCEEDS);
            }
            if (authenticationErrorMessage != null && authenticationErrorMessage.length() > 1000) {
                errors.rejectValue(AdminControllerConstants.C_AUTH_ERROR_MESSAGE,
                        AdminControllerConstants.C_AUTH_ERROR_MESSAGE_LENGTH_EXCEEDS);
            }
            if (authEndPoint != null && isAuthEndPointEmpty) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_ENDPOINT, C_AUTHENTICATION_URL_EMPTY);
            } else if (!isAuthEndPointEmpty && !(urlValidator.isValid(authEndPoint))) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_AUTH_ENDPOINT,
                        AuthenticationOptionError.INVALID_AUTH_ENDPOINT.name());
            }
            String accountListEndPoint = issuerDetail.getAccountListEndpoint();
            boolean isAccountListEndPointEmpty = (accountListEndPoint != null) ? accountListEndPoint.trim().isEmpty()
                    : true;
            if (accountListEndPoint != null && isAccountListEndPointEmpty) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ACCOUNTLIST_ENDPOINT,
                        C_AUTHENTICATION_ACCOUNT_LIST_ENDPOINT);
            } else if (!isAccountListEndPointEmpty && !(urlValidator.isValid(accountListEndPoint))) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ACCOUNTLIST_ENDPOINT,
                        AuthenticationOptionError.INVALID_ACCOUNT_LIST_ENDPOINT.name());
            }
            validateIssuerLogo(issuerDetail, errors);
            break;
        default:
            break;

        }

    }

    /**
     * Performs the validation for issuer logo.
     * 
     * @param issuerDetail
     *            IssuerDetail - The detail.
     * @param errors
     *            Errors - The errors object in which the validation messages
     *            are set.
     * @throws IOException
     */
    private static void validateIssuerLogo(IssuerDetail issuerDetail, Errors errors) {

        MultipartFile issuerImage = issuerDetail.getIssuerLogo();

        if (issuerDetail.getId() == null || (issuerImage != null && issuerImage.getSize() > 0)) {
            if (issuerImage == null || issuerImage.getSize() == 0) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_LOGO, NotEmpty.class.getSimpleName());
            } else {
                String filePath = FilenameUtils.getExtension(issuerDetail.getIssuerLogo().getOriginalFilename());
                if (!(JPEG_TYPE.equalsIgnoreCase(filePath) || JPG_TYPE.equalsIgnoreCase(filePath))) {
                    errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_LOGO,
                            AuthenticationOptionError.INVALID_ISSUER_LOGO_TYPE.name());
                }

                try {
                    BufferedImage issuerimage = ImageIO.read(issuerDetail.getIssuerLogo().getInputStream());

                    if (issuerimage != null) {
                        if (issuerimage.getWidth() > 200 || issuerimage.getHeight() > 500) {
                            errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_LOGO,
                                    AuthenticationOptionError.INVALID_ISSUER_LOGO_SIZE.name());

                        }
                    } else {
                        throw new Exception();
                    }

                } catch (Exception e) {
                    errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_LOGO,
                            AuthenticationOptionError.INVALID_ISSUER_LOGO_TYPE.name());

                }

            }
        }

        MultipartFile multipartFile = issuerDetail.getIssuerLogo();
        if (multipartFile != null) {
            String filePath = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            if (filePath != null && issuerDetail.getFileName() != null && "".equals(issuerDetail.getFileName().trim())
                    && "".equals(filePath.trim())) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_LOGO, NotEmpty.class.getSimpleName());
            }
        }

    }

    /**
     * Validates the pattern.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isNumberPattern(String inputValue) {
        return inputValue.matches("^[-+]?(0|[1-9][0-9]*)$");
    }

    /**
     * Validates the pattern.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isNumberLengthCheckPattern(String inputValue) {
        return inputValue.matches("^[-+]?(0|[1-99999999999][0-99999999999]*)$");
    }

    /**
     * Validates the pattern.
     * 
     * @param inputValue
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean isNegativePattern(String inputValue) {
        return inputValue.matches("^-\\d+$");
    }
}
