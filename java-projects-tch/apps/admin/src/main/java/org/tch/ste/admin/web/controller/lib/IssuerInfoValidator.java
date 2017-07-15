/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_EMPTY_CHECK;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_IID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_WRONG_PATTERN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_IISN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_DROPZONE_PATH;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_NULL_CHECK;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.constant.IssuerInfoErrorCode;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.CompareUtil;
import org.tch.ste.infra.util.ListUtil;

/**
 * Validates the issuer information. Checks for the following: - Duplicate IID.
 * - Duplicate IISN.
 * 
 * @author Karthik.
 * 
 */
@Service("issuerInfoValidator")
public class IssuerInfoValidator implements Validator {

    @Autowired(required = true)
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

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
        String issuerIid = issuerDetail.getIid();
        String issuerName = issuerDetail.getName();
        String iisn = issuerDetail.getIisn();
        String dropZonePath = issuerDetail.getDropzonePath();
        // Validating name
        if (issuerName != null) {
            if (validateEmpty(issuerName)) {
                errors.rejectValue(C_FIELD_NAME, C_EMPTY_CHECK);
            } else if (issuerName.length() > 100) {
                errors.rejectValue(C_FIELD_NAME, AdminControllerConstants.C_EXCEEDS_LENGTH);
            } else {
                validateName(issuerDetail, errors);
            }
        } else {
            errors.rejectValue(C_FIELD_NAME, C_NULL_CHECK);
        }
        // Validating IID
        if (issuerIid != null) {
            if (validateEmpty(issuerIid)) {
                errors.rejectValue(C_FIELD_IID, C_EMPTY_CHECK);
            } else if (issuerIid.length() > 13) {
                errors.rejectValue(C_FIELD_IID, AdminControllerConstants.C_IID_EXCEEDS);
            } else if (validatePattern(issuerIid)) {
                errors.rejectValue(C_FIELD_IID, C_WRONG_PATTERN);
            }
        } else {
            errors.rejectValue(C_FIELD_IID, C_NULL_CHECK);
        }
        // Validating IISN
        if (iisn != null) {
            if (validateEmpty(iisn)) {
                errors.rejectValue(C_FIELD_NAME_IISN, C_EMPTY_CHECK);
            } else if (!checkIisnPattern(iisn)) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_IISN, C_WRONG_PATTERN);
            } else if (iisn.length() == 6) {
                validateIisn(issuerDetail, errors);
            }
        } else {
            errors.rejectValue(C_FIELD_NAME_IISN, C_NULL_CHECK);
        }
        // Validating Dropzone Path
        if (dropZonePath != null) {
            if (validateEmpty(dropZonePath)) {
                errors.rejectValue(C_FIELD_DROPZONE_PATH, C_EMPTY_CHECK);
            } else if (dropZonePath.length() > 255) {
                errors.rejectValue(C_FIELD_DROPZONE_PATH, AdminControllerConstants.C_EXCEEDS_LENGTH);
            }
        } else {
            errors.rejectValue(C_FIELD_DROPZONE_PATH, C_NULL_CHECK);
        }

    }

    /**
     * Checks IISN.
     * 
     * @param iisn
     *            String - The iisn.
     * @return boolean.
     */
    private static boolean checkIisnPattern(String iisn) {
        return iisn.matches("(\\d{6})");
    }

    /**
     * Validates that the IISN is unique.
     * 
     * @param issuerDetail
     *            IssuerDetail - The issuer detail.
     * @param errors
     *            Errors - The errors object.
     */
    private void validateIisn(IssuerDetail issuerDetail, Errors errors) {
        String iisn = issuerDetail.getIisn();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.ISSUER_IISN, iisn);
        IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                AdminConstants.GET_ISSUERS_BY_IISN, params));
        if (issuerConfiguration != null && !CompareUtil.isEqual(issuerConfiguration.getId(), issuerDetail.getId())) {
            errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_IISN,
                    IssuerInfoErrorCode.IISN_ALREADY_EXISTS.name());
        }

    }

    /**
     * Validates that the Name is unique.
     * 
     * @param issuerDetail
     *            IssuerDetail - The issuer detail.
     * @param errors
     *            Errors - The errors object.
     */
    private void validateName(IssuerDetail issuerDetail, Errors errors) {
        String issuerName = issuerDetail.getName();
        if (issuerName != null && !errors.hasFieldErrors(AdminControllerConstants.FIELD_NAME_ISSUER_NAME)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.ISSUER_NAME, issuerName);
            IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                    AdminConstants.GET_ISSUERS_BY_NAME, params));
            if (issuerConfiguration != null && !CompareUtil.isEqual(issuerConfiguration.getId(), issuerDetail.getId())) {
                errors.rejectValue(AdminControllerConstants.FIELD_NAME_ISSUER_NAME,
                        IssuerInfoErrorCode.NAME_ALREADY_EXISTS.name());
            }
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
        return inputValue.trim().isEmpty();
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
        return inputValue.matches("(\\d{6}\\.\\d{6})");
    }
}
