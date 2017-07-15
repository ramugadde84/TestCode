/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.entity.Role;
import org.tch.ste.infra.repository.JpaDao;

/**
 * Implements the interface to fetch the roles from the DB.
 * 
 * @author Karthik.
 * 
 */
public class AdminAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    @Autowired
    @Qualifier("roleDao")
    private JpaDao<Role, Integer> roleDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator
     * #getGrantedAuthorities
     * (org.springframework.ldap.core.DirContextOperations, java.lang.String)
     */
    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.USER_ID, username);
        List<Role> roles = roleDao.findByName(AdminConstants.GET_ROLES_FOR_USER, params);
        List<GrantedAuthority> retVal = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            retVal.add(new SimpleGrantedAuthority(role.getName()));
        }
        return retVal;
    }

}
