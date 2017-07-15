/**
 * 
 */
package org.tch.ste.admin.service.internal.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private LdapTemplate ldapTemplate;

    private String baseDn;

    private static final String userIdToReplace = "{0}";

    /**
     * Base Constructor.
     * 
     * @param baseDn
     *            String - The base dn.
     */
    public UserSearchServiceImpl(String baseDn) {
        this.baseDn = baseDn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.internal.user.UserSearchService#checkForUser
     * (java.lang.String)
     */
    @Override
    public boolean checkForUser(String userId) {
        try {
            return ldapTemplate.lookup(baseDn.replace(userIdToReplace, userId)) != null;
        } catch (Throwable ex) {
            return false;
        }
    }

}
