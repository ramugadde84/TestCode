/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_BIN_FORMAT_INVALID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_EMPTY_CHECK;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_REAL_BIN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.constant.BinMappingInfoErrorCode;
import org.tch.ste.admin.constant.BinType;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.domain.entity.IisnBin;
import org.tch.ste.domain.entity.Network;
import org.tch.ste.domain.entity.TokenBinMapping;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.CompareUtil;
import org.tch.ste.infra.util.ListUtil;

/**
 * Validates the bin mappings as per the business rules defined in the use-case.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BinMappingValidatorImpl implements BinMappingValidator {

    @Autowired
    @Qualifier("iisnBinDao")
    private JpaDao<IisnBin, String> iisnBinDao;

    @Autowired(required = true)
    @Qualifier("newtorkDao")
    private JpaDao<Network, Integer> networkDao;

    @Autowired
    @Qualifier("tokenBinMappingDao")
    private JpaDao<TokenBinMapping, Integer> tokenBinMappingDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return BinMapping.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        BinMapping binMapping = (BinMapping) target;
        String realBin = binMapping.getRealBin();
        String tokenBin = binMapping.getTokenBin();
        if (validateBinValues(realBin, tokenBin, errors)) {
            validateBinEquals(binMapping, errors);
            validateFirstDigitOfRealAndTokenBin(binMapping, errors);
            validateUniqueBins(binMapping, errors);
            validateExistingBinMapping(binMapping, errors);
        }

    }

    /**
     * @param realBin
     *            validating real bin.
     * @param tokenBin
     *            validating real bin.
     * @param errors
     *            Errors- the errors.
     * @return isValidate
     */
    private static boolean validateBinValues(String realBin, String tokenBin, Errors errors) {
        // Validating real bin.
        if (null != realBin && !errors.hasFieldErrors(C_FIELD_NAME_REAL_BIN)) {
            boolean isValidateRealBin = true;
            if (validateEmpty(realBin)) {
                errors.rejectValue(C_FIELD_NAME_REAL_BIN, C_EMPTY_CHECK);
                isValidateRealBin = false;
            } else if (validateLength(realBin)) {
                errors.rejectValue(C_FIELD_NAME_REAL_BIN, C_BIN_FORMAT_INVALID);
                isValidateRealBin = false;
            }

            if (isValidateRealBin) {
                if (!validatePattern(realBin)) {
                    errors.rejectValue(C_FIELD_NAME_REAL_BIN, C_BIN_FORMAT_INVALID);
                }
            }
        }
        // Validating token bin.
        if (null != tokenBin && !errors.hasFieldErrors(C_FIELD_NAME_TOKEN_BIN)) {
            boolean isValidateTokenBin = true;
            if (validateEmpty(tokenBin)) {
                errors.rejectValue(C_FIELD_NAME_TOKEN_BIN, C_EMPTY_CHECK);
                isValidateTokenBin = false;
            } else if (validateLength(tokenBin)) {
                errors.rejectValue(C_FIELD_NAME_TOKEN_BIN, C_BIN_FORMAT_INVALID);
                isValidateTokenBin = false;
            }
            if (isValidateTokenBin) {
                if (!validatePattern(tokenBin)) {
                    errors.rejectValue(C_FIELD_NAME_TOKEN_BIN, C_BIN_FORMAT_INVALID);
                }
            }
        }
        if (!errors.hasErrors()) {
            return true;
        }
        return false;

    }

    /**
     * Checks whether the two bins are equal.
     * 
     * @param binMapping
     *            BinMapping - The bin mapping.
     * @param errors
     *            Errors- the errors.
     */
    private static void validateBinEquals(BinMapping binMapping, Errors errors) {
        if (CompareUtil.isEqual(binMapping.getRealBin(), binMapping.getTokenBin())) {
            errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_REAL_BIN,
                    BinMappingInfoErrorCode.BINS_CANNOT_BE_SAME.name());
        }
    }

    /**
     * Validates whether the bins are unique.
     * 
     * @param binMapping
     * @param errors
     */
    private void validateUniqueBins(BinMapping binMapping, Errors errors) {
        String realBin = binMapping.getRealBin();
        if (realBin != null && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_REAL_BIN)) {
            validateBin(binMapping, realBin, errors, BinType.REAL_BIN);
        }
        String tokenBin = binMapping.getTokenBin();
        if (tokenBin != null && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN)) {
            validateBin(binMapping, tokenBin, errors, BinType.TOKEN_BIN);
        }

    }

    /**
     * Validates the following: - Same IISN (If bin already exists) - Same Type
     * (Real Bin or Token Bin)
     * 
     * @param binMapping
     * @param bin
     * @param errors
     * @param binType
     */
    private void validateBin(BinMapping binMapping, String bin, Errors errors, BinType binType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_BIN, bin);
        IisnBin binInDb = ListUtil.getFirstOrNull(iisnBinDao.findByName(AdminConstants.GET_EXISTING_BINS, params));
        if (binInDb != null) {
            if (!binInDb.getIisn().equals(binMapping.getIisn())) {
                errors.rejectValue(
                        (binType == BinType.REAL_BIN) ? AdminControllerConstants.C_FIELD_NAME_REAL_BIN
                                : AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN,
                        (binType == BinType.REAL_BIN) ? BinMappingInfoErrorCode.REAL_BIN_SHOULD_NOT_MAP_OTHER_IISN
                                .name() : BinMappingInfoErrorCode.TOKEN_BIN_SHOULD_NOT_MAP_OTHER_IISN.name());
            } else if (!binInDb.getBinType().equals(binType.toString())) {
                errors.rejectValue(
                        (binType == BinType.REAL_BIN) ? AdminControllerConstants.C_FIELD_NAME_REAL_BIN
                                : AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN,
                        (binType == BinType.REAL_BIN) ? BinMappingInfoErrorCode.REAL_BIN_SHOULD_NOT_BE_SAME_AS_TOKEN_BIN
                                .name() : BinMappingInfoErrorCode.TOKEN_BIN_SHOULD_NOT_BE_SAME_AS_REAL_BIN.name());
            }
        }
    }

    /**
     * Validating first digit of real bin equal to first digit of token bin
     * 
     * @param binMapping
     *            - the bin mapping
     * @param errors
     *            - returns proper error message.
     */

    private void validateFirstDigitOfRealAndTokenBin(BinMapping binMapping, Errors errors) {
        String realBin = binMapping.getRealBin();
        String tokenBin = binMapping.getTokenBin();
        if ((realBin != null && !realBin.trim().isEmpty()) && (tokenBin != null && !tokenBin.trim().isEmpty())) {
            if (!realBin.startsWith(tokenBin.substring(0, 1))) {
                errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN,
                        BinMappingInfoErrorCode.FIRIST_DIGIT_VALIDATION_FOR_REAL_AND_TOKEN_BIN.name());
            }
            binNetworkFirstDigitValidator(binMapping, errors);
        }
    }

    /**
     * validating Token and Real bin first digit match with Networks table first
     * digits of bin
     * 
     * @param binMapping
     *            - the bin mapping
     * @param errors
     *            - returns proper error message.
     */
    private void binNetworkFirstDigitValidator(BinMapping binMapping, Errors errors) {
        String realBin = binMapping.getRealBin();
        String tokenBin = binMapping.getTokenBin();
        if (realBin != null && tokenBin != null
                && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN)
                && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_REAL_BIN)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.PARAM_NAME_FIRST_DIGIT, tokenBin.substring(0, 1));
            Network network = ListUtil.getFirstOrNull(networkDao.findByName(AdminConstants.GET_FIRST_DIGIT_ON_NETWORK,
                    params));
            if (network == null) {
                errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_TOKEN_BIN,
                        BinMappingInfoErrorCode.BIN_NETWORK_FIRST_DIGIT_VALIDATOR.name());
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
     * Validates bins should less than six digits.
     * 
     * @param bin
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean validateLength(String bin) {
        if (bin.trim().length() != 6) {
            return true;
        }
        return false;
    }

    /**
     * Validates the pattern.
     * 
     * @param value
     *            String - The value.
     * @return boolean - True or false.
     */
    private static boolean validatePattern(String value) {
        return isPattern(value);
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
     * Validates the Real bin has been mapped to another token BIN
     * 
     * @param binMapping
     *            BinMapping - The bin mapping.
     * @param errors
     *            Errors- the errors.
     */
    private void validateExistingBinMapping(BinMapping binMapping, Errors errors) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_BIN, binMapping.getRealBin());
        TokenBinMapping tokenBinMapping = ListUtil.getFirstOrNull(tokenBinMappingDao.findByName(
                AdminConstants.GET_TOKEN_BINS_FROM_MAPPINGS, params));
        if (tokenBinMapping != null && !tokenBinMapping.getId().equals(binMapping.getId())) {
            errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_REAL_BIN,
                    BinMappingInfoErrorCode.REAL_BIN_USED_OTHER_TOKEN_BIN_MAPPING.name());
        }
    }
}
