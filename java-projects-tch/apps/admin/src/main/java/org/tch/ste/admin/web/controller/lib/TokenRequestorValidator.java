/**
 * 
 */
package org.tch.ste.admin.web.controller.lib;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.AdminControllerConstants;
import org.tch.ste.admin.constant.TokenRequestorErrorCode;
import org.tch.ste.admin.domain.dto.TokenRequestor;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Validates the Token Requestor information. Checks for the following: -
 * Duplicate Network ID. - Duplicate Token Requestor Name.
 * 
 * @author anus
 * 
 */
@Service("tokenRequestorValidator")
public class TokenRequestorValidator implements Validator {

    @Autowired(required = true)
    @Qualifier("tokenRequestorConfigurationDao")
    private JpaDao<TokenRequestorConfiguration, Integer> tokenRequestorConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return TokenRequestor.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    @Override
    public void validate(Object target, Errors errors) {
        TokenRequestor tokenRequestor = (TokenRequestor) target;
        validatetokenRequestorID(tokenRequestor, errors);
        validatetokenRequestorName(tokenRequestor, errors);

    }

    /**
     * @param tokenRequestor
     * @param errors
     */
    private void validatetokenRequestorID(TokenRequestor tokenRequestor, Errors errors) {
        String networkId = tokenRequestor.getNetworkId();
        if (networkId != null
                && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.NETWORK_TR_ID, networkId);
            TokenRequestorConfiguration tokenRequestorConfiguration = ListUtil
                    .getFirstOrNull(tokenRequestorConfigurationDao.findByName(
                            AdminConstants.GET_TOKEN_REQUESTOR_BASED_ON_NETWORK_ID, params));
            if (tokenRequestorConfiguration != null
                    && !tokenRequestorConfiguration.getId().equals(tokenRequestor.getId())) {
                errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NETWORK_ID,
                        TokenRequestorErrorCode.NETWORK_ID_ALREADY_EXISTS.name());
            }
        }
    }

    /**
     * @param tokenRequestor
     * @param errors
     */
    private void validatetokenRequestorName(TokenRequestor tokenRequestor, Errors errors) {
        String name = tokenRequestor.getName();
        if (name != null && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NAME)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.PARAM_NAME, name);
            TokenRequestorConfiguration tokenRequestorConfiguration = ListUtil
                    .getFirstOrNull(tokenRequestorConfigurationDao.findByName(
                            AdminConstants.GET_TOKEN_REQUESTOR_BASED_ON_NAME, params));
            if (tokenRequestorConfiguration != null
                    && !tokenRequestorConfiguration.getId().equals(tokenRequestor.getId())) {
                errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_TOKEN_REQUESTOR_NAME,
                        TokenRequestorErrorCode.TOKEN_REQUESTOR_NAME_ALREADY_EXISTS.name());
            }
        }

    }
}
