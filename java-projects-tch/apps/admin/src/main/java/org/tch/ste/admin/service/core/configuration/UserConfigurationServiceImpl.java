package org.tch.ste.admin.service.core.configuration;

import static org.tch.ste.admin.constant.AdminConstants.GET_ALL_USERS;
import static org.tch.ste.admin.constant.AdminConstants.GET_USER_WITH_USER_ROLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.dto.UserConfigurationProperties;
import org.tch.ste.admin.domain.entity.Role;
import org.tch.ste.admin.domain.entity.User;
import org.tch.ste.admin.domain.entity.UserRole;
import org.tch.ste.domain.constant.BoolInt;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * @author sharduls
 * 
 */
@Service
public class UserConfigurationServiceImpl implements UserConfigurationService {

    private static Logger logger = LoggerFactory.getLogger(UserConfigurationServiceImpl.class);

    @Autowired
    @Qualifier("userDao")
    private JpaDao<User, Integer> userDao;

    @Autowired
    @Qualifier("roleDao")
    private JpaDao<Role, String> roleDao;

    @Autowired
    @Qualifier("userRoleDao")
    private JpaDao<UserRole, String> userRoleDao;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, String> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.issuer.UserRoleService#loadUserRole()
     */
    @Override
    public List<UserConfigurationProperties> loadUsers() {
        return convertToDto(userDao.findByName(GET_ALL_USERS, new HashMap<String, Object>()));
    }

    /**
     * Conversion of Entity data to DTO.
     * 
     * @param users
     *            - list of users.
     * @return list of user properties.
     */
    private List<UserConfigurationProperties> convertToDto(List<User> users) {
        List<UserConfigurationProperties> userConfigurationProperties = new LinkedList<UserConfigurationProperties>();
        for (User user : users) {
            userConfigurationProperties.add(convertToDto(user));
        }
        return userConfigurationProperties;
    }

    /**
     * Converts the entity to a DTO.
     * 
     * @param user
     *            User - The user.
     * @return UserConfigurationProperties - The DTO.
     */
    private UserConfigurationProperties convertToDto(User user) {

        UserConfigurationProperties userConfigurationProperty = new UserConfigurationProperties();
        userConfigurationProperty.setUserId(user.getUserId());
        userConfigurationProperty.setIisn(user.getIisn());
        userConfigurationProperty.setFirstName(user.getFirstName());
        userConfigurationProperty.setLastName(user.getLastName());
        userConfigurationProperty.setId(user.getId());
        List<Integer> authorizedRoles = new ArrayList<Integer>();
        List<UserRole> userRoles = user.getUserRoles();
        StringBuilder userRolesString = new StringBuilder();
        boolean isFirst = true;
        String iisn = user.getIisn();
        if (iisn != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.PARAM_ISSUER_IISN, iisn);
            IssuerConfiguration issuer = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                    AdminConstants.GET_ISSUERS_BY_IISN, params));
            if (issuer != null) {
                userConfigurationProperty.setIssuerName(issuer.getIssuerName());
            }
        }
        for (UserRole userRole : userRoles) {
            if (isFirst) {
                userRolesString.append(userRole.getRole().getName());
                isFirst = false;
            } else {
                userRolesString.append(",").append(userRole.getRole().getName());
            }
            authorizedRoles.add(userRole.getRole().getId());
        }
        userConfigurationProperty.setUserRoles(userRolesString.toString());
        userConfigurationProperty.setAuthorizedRoles(toIntegerArray(authorizedRoles));
        return userConfigurationProperty;
    }

    Integer[] toIntegerArray(List<Integer> list) {
        Integer[] ret = new Integer[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.configuration.UserConfigurationService
     * #saveUser(org.tch.ste.admin.domain.dto.UserConfigurationProperties)
     */
    @Override
    @Transactional(readOnly = false)
    public void saveUser(UserConfigurationProperties userConfigurationProperties) {
        Integer id = userConfigurationProperties.getId();
        List<Role> existingRoles = null;
        if (logger.isInfoEnabled() && id != null) {
            existingRoles = getRoles(id);
        }
        deleteExistingUserRoles(id);
        User retVal = userDao.save(convertToEntity(userConfigurationProperties));
        userConfigurationProperties.setId(retVal.getId());
        if (logger.isInfoEnabled()) {
            logSaveAction(retVal, existingRoles);
        }
    }

    /**
     * Logs as per the use case.
     * 
     * @param user
     *            User - The user.
     * @param existingRoles
     *            List<Role> - The user roles object.
     */
    private void logSaveAction(User user, List<Role> existingRoles) {
        List<Role> currentRoles = getCurrentRoles(user);
        List<Role> removedRoles = null;
        List<Role> addedRoles = currentRoles;
        if (existingRoles != null) {
            removedRoles = findRolesMissing(existingRoles, currentRoles);
            addedRoles = findRolesMissing(currentRoles, existingRoles);
        }

        StringBuilder logBuilder = new StringBuilder("Credentials ");
        if (existingRoles == null) {
            logBuilder.append("added by ");
        } else {
            logBuilder.append("updated by ");
        }
        logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
        logBuilder.append(" for ");
        String iisn = user.getIisn();
        if (iisn == null || iisn.isEmpty()) {
            logBuilder.append("TCH user ");
            logBuilder.append(user.getUserId());
        } else {
            logBuilder.append("Issuer admin user ");
            logBuilder.append(user.getUserId());
            logBuilder.append(", IISN = ");
            logBuilder.append(iisn);
        }
        logBuilder.append('.');
        boolean isNotFirst = false;
        if (addedRoles != null && !addedRoles.isEmpty()) {
            logBuilder.append(" Roles added: ");
            for (Role addedRole : addedRoles) {
                if (isNotFirst) {
                    logBuilder.append(", ");
                }
                isNotFirst = true;
                logBuilder.append(addedRole.getName());
            }
            logBuilder.append('.');
        }

        if (removedRoles != null && !removedRoles.isEmpty()) {
            isNotFirst = false;
            logBuilder.append(" Roles removed: ");
            for (Role removedRole : removedRoles) {
                if (isNotFirst) {
                    logBuilder.append(", ");
                }
                isNotFirst = true;
                logBuilder.append(removedRole.getName());
            }
            logBuilder.append('.');
        }
        logger.info(logBuilder.toString());
    }

    private List<Role> getCurrentRoles(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Integer> roleIds = new ArrayList<Integer>();
        for (UserRole userRole : user.getUserRoles()) {
            roleIds.add(userRole.getRole().getId());
        }
        params.put(AdminConstants.PARAM_ID, roleIds);
        return roleDao.findByName(AdminConstants.GET_ROLES_BY_ROLE_IDS, params);
    }

    /**
     * Finds the roles that exist in the first argument but not in the second.
     * 
     * @param roles1
     *            List<UserRole> - The first argument.
     * @param roles2
     *            List<UserRole> - The second argument.
     * @return List<UserRole> - The missing roles.
     */
    private static List<Role> findRolesMissing(List<Role> roles1, List<Role> roles2) {
        Map<String, Boolean> secondRoles = new HashMap<String, Boolean>();
        List<Role> retVal = new ArrayList<Role>();
        for (Role role2 : roles2) {
            secondRoles.put(role2.getName(), Boolean.TRUE);
        }
        for (Role role1 : roles1) {
            if (!secondRoles.containsKey(role1.getName())) {
                retVal.add(role1);
            }
        }
        return retVal;
    }

    /**
     * Fetches existing user roles.
     * 
     * @param id
     *            Integer - The id.
     * @return List<UserRole> - The existing roles.
     */
    private List<Role> getRoles(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_ID, id);
        return roleDao.findByName(AdminConstants.GET_ROLES_BY_USER_ID, params);
    }

    /**
     * Deletes existing user roles.
     * 
     * @param id
     *            Integer - The id.
     */
    private void deleteExistingUserRoles(Integer id) {
        if (id != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.PARAM_ID, id);
            userRoleDao.updateByName(AdminConstants.DELETE_EXISTING_USER_ROLES, params);
        }
    }

    /**
     * Converts the DTO into the entities.
     * 
     * @param userConfigurationProperties
     *            UserConfigurationProperties - The user properties.
     * @return List<VaultConfiguration> - The entity objects.
     */
    private static User convertToEntity(UserConfigurationProperties userConfigurationProperties) {
        User user = new User();
        List<UserRole> userRoles = new ArrayList<UserRole>();
        Integer id = userConfigurationProperties.getId();
        if (id != null && id != 0) {
            user.setId(id);
        }
        user.setUserId(userConfigurationProperties.getUserId());
        user.setFirstName(userConfigurationProperties.getFirstName());
        user.setLastName(userConfigurationProperties.getLastName());
        user.setActive(BoolInt.TRUE.ordinal());
        String iisn = userConfigurationProperties.getIisn();
        if (iisn != null && !iisn.isEmpty()) {
            user.setIisn(iisn);
        }
        Integer[] authorizedRoles = userConfigurationProperties.getAuthorizedRoles();
        if (null != authorizedRoles) {
            for (Integer authRole : authorizedRoles) {
                UserRole userRole = new UserRole();
                Role role = new Role();
                role.setId(authRole);
                userRole.setUser(user);
                userRole.setRole(role);
                userRoles.add(userRole);
            }
        }
        user.setUserRoles(userRoles);
        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.configuration.UserConfigurationService
     * #getAllRoles()
     */
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.configuration.UserConfigurationService
     * #getUser(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public UserConfigurationProperties getUser(Integer id) {
        return convertToDto(getUserById(id));
    }

    /**
     * @param id
     *            - Id of the User.
     * @return List of Users
     */
    public User getUserById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put(AdminConstants.PARAM_ID, id);
        return ListUtil.getFirstOrNull(userDao.findByName(GET_USER_WITH_USER_ROLES, params));

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.configuration.UserConfigurationService
     * #deleteUser()
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteUser(UserConfigurationProperties userConfigurationProperties) {
        userDao.get(userConfigurationProperties.getId()).setActive(BoolInt.FALSE.ordinal());
    }

}
