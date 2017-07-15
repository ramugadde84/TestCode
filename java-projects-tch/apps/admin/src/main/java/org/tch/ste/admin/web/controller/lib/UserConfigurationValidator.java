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
import org.tch.ste.admin.constant.UserConfigurationErrorCode;
import org.tch.ste.admin.domain.dto.UserConfigurationProperties;
import org.tch.ste.admin.domain.entity.User;
import org.tch.ste.admin.service.internal.user.UserSearchService;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Validates the USer information. Checks for the following: - Duplicate User
 * ID. - Duplicate User Name.
 * 
 * @author sharduls
 * 
 */
@Service("userConfigurationValidator")
public class UserConfigurationValidator implements Validator {
    @Autowired
    @Qualifier("userDao")
    private JpaDao<User, String> userDao;

    @Autowired
    private UserSearchService userSearchService;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return UserConfigurationProperties.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserConfigurationProperties userConfigurationProperties = (UserConfigurationProperties) target;
        validateUserId(userConfigurationProperties, errors);
        validateAuthorizedRoles(userConfigurationProperties, errors);
        validateIisn(userConfigurationProperties, errors);

    }

    /**
     * validate UserId.
     * 
     * @param validate
     *            AuthorizedRoles
     * @param errors
     */
    private void validateUserId(UserConfigurationProperties userConfigurationProperties, Errors errors) {
        String userId = userConfigurationProperties.getUserId();
        if (!userSearchService.checkForUser(userId)) {
            errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_USER_ID,
                    UserConfigurationErrorCode.USER_NOT_EXISTS_IN_AD.name());
        }
        if (userId != null && !errors.hasFieldErrors(AdminControllerConstants.C_FIELD_NAME_USER_ID)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.USER_ID, userId);
            User user = ListUtil.getFirstOrNull(userDao.findByName(AdminConstants.GET_USER_BY_USER_ID, params));
            if (user != null && !user.getId().equals(userConfigurationProperties.getId())) {
                errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_USER_ID,
                        UserConfigurationErrorCode.USER_ALREADY_EXISTS.name());
            }
        }
    }

    /**
     * Validated the UserRoles.
     * 
     * @param userConfigurationProperties
     * @param errors
     */
    private static void validateAuthorizedRoles(UserConfigurationProperties userConfigurationProperties, Errors errors) {
        Integer[] authorizedRoles = userConfigurationProperties.getAuthorizedRoles();
        if (null == authorizedRoles || authorizedRoles.length == 0) {
            errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_USER_ROLES,
                    UserConfigurationErrorCode.SHOULD_SELECT_ATLEAST_ONE_ROLE.name());
        }
    }

    /**
     * Validated the Issuer.
     * 
     * @param userConfigurationProperties
     * @param errors
     */
    private static void validateIisn(UserConfigurationProperties userConfigurationProperties, Errors errors) {
        if ("Y".equalsIgnoreCase(userConfigurationProperties.getIsIssuerUser())
                && (null == userConfigurationProperties.getIisn() || userConfigurationProperties.getIisn().length() == 0)) {
            errors.rejectValue(AdminControllerConstants.C_FIELD_NAME_IISN,
                    UserConfigurationErrorCode.SHOULD_SELECT_AN_ISSUER.name());
        }
    }
}
