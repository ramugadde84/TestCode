/**
 * 
 */
package org.tch.ste.admin.service.core.customermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.dto.CustomerManagement;
import org.tch.ste.admin.domain.dto.UserPassword;
import org.tch.ste.admin.service.internal.issuer.IssuerFetchService;
import org.tch.ste.domain.constant.BoolStr;
import org.tch.ste.domain.constant.LockCode;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.password.PasswordGenerationService;
import org.tch.ste.infra.util.DateUtil;

/**
 * @author anus
 * 
 */
@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {

    private static Logger logger = LoggerFactory.getLogger(CustomerManagementServiceImpl.class);

    @Autowired
    private PasswordGenerationService passwordGenerationService;

    @Autowired
    private IssuerFetchService issuerFetchService;

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.customermanagement.CustomerManagementService
     * #resetPassword(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = false)
    public UserPassword resetPassword(Integer id, String iisn) {
        UserPassword retVal = new UserPassword();
        Customer customer = customerDao.get(id);
        String userName = customer.getUserName();
        Password password = passwordGenerationService.generatePassword();
        customer.setHashedPassword(password.getHashedPassword());
        customer.setPasswordSalt(password.getPasswordSalt());
        customer.setLastPasswordChange(DateUtil.getUtcTime());
        retVal.setUserId(userName);
        retVal.setPassword(password.getPlainTextPassword());
        logger.info("The password for user {} for IISN = {} was reset by {}.", new Object[] { userName, iisn,
                SecurityContextHolder.getContext().getAuthentication().getName() });

        return retVal;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.customermanagement.CustomerManagementService
     * #updateAccountLockStatus()
     */
    @Override
    @Transactional(readOnly = false)
    public boolean lock(Integer id, String iisn) {
        boolean lockStatus = false;
        Customer customer = customerDao.get(id);
        // TODO - Validations.
        if (!customer.getAccountLocked()) {
            lockStatus = true;
            customer.setAccountLocked(Boolean.TRUE);
            customer.setAccountLockedReasonCode(LockCode.MANUAL_LOCK.getCode());
            customer.setAccountLockedAt(DateUtil.getUtcTime());
            if (logger.isInfoEnabled()) {
                StringBuilder logBuilder = new StringBuilder("The account for user ");
                logBuilder.append(customer.getUserName());
                logBuilder.append(" for IISN = ");
                logBuilder.append(iisn);
                logBuilder.append(" was locked by ");
                logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
                logger.info(logBuilder.toString());
            }
        }
        return lockStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.customermanagement.CustomerManagementService
     * #getCustomers(java.lang.String)
     */
    @Override
    public List<CustomerManagement> getCustomers(String iisn) {
        IssuerConfiguration issuerConfiguration = issuerFetchService.getIssuerByIisn(iisn);
        List<Customer> customers = customerDao.findByName(AdminConstants.GET_CUSTOMER_DTLS,
                new HashMap<String, Object>());
        return convertToDto(customers, issuerConfiguration);
    }

    /**
     * Converts the DTO into the entities.
     * 
     * @param issuerConfiguration
     *            IssuerConfiguration- The issuer configuration.
     * @param customers
     *            List<Customer> - The customers.
     * @return List<CustomerManagement> - The customer list.
     */
    private static List<CustomerManagement> convertToDto(List<Customer> customers,
            IssuerConfiguration issuerConfiguration) {
        List<CustomerManagement> customerManagement = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerManagement custmgmt = new CustomerManagement();
            custmgmt.setUserId(customer.getUserName());
            custmgmt.setId(customer.getId());
            custmgmt.setIssuerName(issuerConfiguration.getIssuerName());
            if (customer.getLastSuccessfulLogin() != null) {
                custmgmt.setAuthenticated(BoolStr.Y.name());
            } else {
                custmgmt.setAuthenticated(BoolStr.N.name());
            }
            if (null != customer.getAccountLocked()) {
                custmgmt.setLocked(customer.getAccountLocked());
            } else {
                custmgmt.setLocked(false);
            }
            customerManagement.add(custmgmt);
        }
        return customerManagement;
    }

    /**
     * 
     * @param id
     *            - id
     * @param iisn
     *            - iisn
     * @return boolean
     */
    @Override
    @Transactional(readOnly = false)
    public boolean unlock(Integer id, String iisn) {
        boolean unlockStatus = false;
        Customer customer = customerDao.get(id);
        if (customer.getAccountLocked()) {
            unlockStatus = true;
            customer.setAccountLocked(Boolean.FALSE);
            customer.setAccountLockedReasonCode(LockCode.UNLOCKED.getCode());
            if (logger.isInfoEnabled()) {
                StringBuilder logBuilder = new StringBuilder("The account for user ");
                logBuilder.append(customer.getUserName());
                logBuilder.append(" for IISN = ");
                logBuilder.append(iisn);
                logBuilder.append(" was unlocked by ");
                logBuilder.append(SecurityContextHolder.getContext().getAuthentication().getName());
                logger.info(logBuilder.toString());
            }
        }
        return unlockStatus;
    }
}
