/**
 * 
 */
package org.tch.ste.admin.service.core.login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.RoleType;
import org.tch.ste.admin.domain.dto.Login;
import org.tch.ste.admin.domain.entity.User;
import org.tch.ste.admin.service.internal.auth.AuthenticationService;
import org.tch.ste.admin.service.internal.auth.AuthorizationService;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("userDao")
    private JpaDao<User, String> userDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.login.LoginService#login(java.lang.String,
     * java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public boolean login(Login login) {
        boolean isAuthenticated = false;
        String userId = login.getLoginId();
        logger.info("TCH Administrative user login attempt by user {}", userId);
        if (authenticationService.authenticate(login)
                && (authorizationService.hasRole(userId, RoleType.ROLE_TCH_USER_MGMT)
                        || authorizationService.hasRole(userId, RoleType.ROLE_TCH_CONFIGURATION) || authorizationService
                            .hasRole(userId, RoleType.ROLE_ISSUER_MGMT))) {
            isAuthenticated = true;
            logger.info("TCH Administrator user " + login.getLoginId() + " logged in ");
        } else {
            logger.info("Failed login attempt for user " + login.getLoginId());
        }

        return isAuthenticated;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.login.LoginService#setCurrentLoginTime
     * (java.lang.String, java.util.Date)
     */
    @Override
    @Transactional(readOnly = false)
    public void setCurrentLoginTime(String userId, Date loginTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.USER_ID, userId);
        params.put(AdminConstants.LOGIN_TIME, loginTime);
        userDao.updateByName(AdminConstants.UPDATE_LOGIN_TIME, params);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.login.LoginService#getLastLoginTime(java
     * .lang.String)
     */
    @Override
    public Date getLastLoginTime(String userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.USER_ID, userId);
        User user = ListUtil.getFirstOrNull(userDao.findByName(AdminConstants.GET_USER_BY_USER_ID, params));
        return (user != null) ? user.getLastLoginTime() : null;
    }

}
