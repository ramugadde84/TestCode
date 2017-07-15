/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implementation of the user details service for authentication.
 * 
 * @author Karthik.
 * 
 */
public class SteAuthUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AuthQueryConstants.PARAM_USER_NAME, username);
        Customer customer = ListUtil.getFirstOrNull(customerDao.findByName(
                AuthQueryConstants.GET_CUSTOMER_BY_USER_NAME, params));
        if (customer != null) {
            Boolean acctLocked = customer.getAccountLocked();
            boolean customerAccountLocked = (acctLocked != null) ? acctLocked : false;
            if (!customerAccountLocked) {
                return new User(username, customer.getHashedPassword(), new ArrayList<GrantedAuthority>());
            }
        }
        throw new UsernameNotFoundException(username);
    }

}
