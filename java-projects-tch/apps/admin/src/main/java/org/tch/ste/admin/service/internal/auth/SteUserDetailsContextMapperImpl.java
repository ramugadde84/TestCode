/**
 * 
 */
package org.tch.ste.admin.service.internal.auth;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.entity.User;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Custom implementation to take care of
 * 
 * @author Karthik.
 * 
 */
public class SteUserDetailsContextMapperImpl extends LdapUserDetailsMapper {

    @Autowired
    @Qualifier("userDao")
    private JpaDao<User, Integer> userDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.ldap.userdetails.LdapUserDetailsMapper#
     * mapUserFromContext(org.springframework.ldap.core.DirContextOperations,
     * java.lang.String, java.util.Collection)
     */
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        LdapUserDetailsImpl ldapUserDetailsImpl = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username,
                authorities);
        SteUserDetailsImpl retVal = new SteUserDetailsImpl(ldapUserDetailsImpl);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.USER_ID, username);
        User user = ListUtil.getFirstOrNull(userDao.findByName(AdminConstants.GET_USER_BY_USER_ID, params));
        if (user != null) {
            retVal.setIisn(user.getIisn());
        }
        return retVal;
    }

}
