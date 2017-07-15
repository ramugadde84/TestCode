/**
 * 
 */
package org.tch.ste.vault.service.internal.generation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class UniqueUserNameGenerationServiceImpl implements UniqueUserNameGenerationService {

    @Autowired
    private UserNameGenerationService userNameGenerationService;

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /**
     * To display all level logs.
     */
    public static final Logger logger = LoggerFactory.getLogger(UniqueUserNameGenerationServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.generation.UniqueUserNameGenerationService
     * #generate()
     */
    @Override
    @Transactional(readOnly = false)
    public Customer generate() {
        Customer retVal = null;
        for (int i = 0; i < VaultConstants.MAX_USERNAME_GENERATION_RETRIES; ++i) {
            String userName = userNameGenerationService.generate();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(VaultQueryConstants.PARAM_USER_NAME, userName);
            if (ListUtil.getFirstOrNull(customerDao.findByName(VaultQueryConstants.GET_CUSTOMER_BY_USER_NAME, params)) == null) {
                try {
                    Customer customer = new Customer();
                    customer.setUserName(userName);
                    customer.setAccountLocked(Boolean.FALSE);
                    retVal = customerDao.saveAndFlush(customer);
                    break;
                } catch (Throwable t) {
                    logger.warn("Error is :", t);
                    // Passthrough in case of a duplicate. Should be a million
                    // to one chance.
                    // Oh no - Terry Pratchet should not read this.
                }
            }
        }
        return retVal;
    }

}
