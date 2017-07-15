/**
 * 
 */
package org.tch.ste.auth.service.internal.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Salt Source to fetch the salt from the customer table.
 * 
 * @author Karthik.
 * 
 */
public class SteAuthSaltSource implements SaltSource {

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.authentication.dao.SaltSource#getSalt(org
     * .springframework.security.core.userdetails.UserDetails)
     */
    @Override
    public Object getSalt(UserDetails user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AuthQueryConstants.PARAM_USER_NAME, user.getUsername());
        return ListUtil.getFirstOrNull(customerDao.findByName(AuthQueryConstants.GET_CUSTOMER_BY_USER_NAME, params))
                .getPasswordSalt();
    }

}
