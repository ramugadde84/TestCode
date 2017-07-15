package org.tch.ste.admin.service.core.configuration;

import java.util.List;

import org.tch.ste.admin.domain.dto.UserConfigurationProperties;
import org.tch.ste.admin.domain.entity.Role;

/**
 * Exposes methods to allow an admin user to configure the following: - save
 * User.
 * 
 * @author Shardul Srivastava
 * 
 */
public interface UserConfigurationService {

    /**
     * This returns the list of Users and their respective roles in the system.
     * 
     * @return userRoleList - Return the list of UserRole.
     */
    public List<UserConfigurationProperties> loadUsers();

    /**
     * @param userConfigurationProperties
     */
    void saveUser(UserConfigurationProperties userConfigurationProperties);

    /**
     * @return List<Role> - get all roles
     */
    List<Role> getAllRoles();

    /**
     * Fetches the user.
     * 
     * @param id
     *            - The user Id of the user.
     * @return UserConfigurationProperties instance.
     */
    UserConfigurationProperties getUser(Integer id);

    /**
     * @param userConfigurationProperties
     *            - Delete the user with this this Property.
     */
    public void deleteUser(UserConfigurationProperties userConfigurationProperties);
}
