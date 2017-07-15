/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.constant.LockCode;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;

/**
 * The impl class of Lock or Unlock customer account service.
 * 
 * @author ramug
 * 
 */
@Service
public class LockUnlockCustomerAccountServiceImpl implements LockUnlockCustomerAccountService {

    private static Logger logger = LoggerFactory.getLogger(LockUnlockCustomerAccountServiceImpl.class);

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    @Autowired
    private LockUnlockCustomerAccountValidationService lockUnlockCustomerAccountValidationService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.customer.
     * LockUnlockCustomerAccountService#lockUnlockCustomers(java.lang.String,
     * org.tch.ste.vault.service.internal.batch.customer.
     * LockUnlockCustomerAccountContent)
     */
    @Override
    @Transactional(readOnly = false)
    public LockUnlockCustomerAccountResponse lockUnlockCustomers(String iisn,
            LockUnlockCustomerAccountContent accountContent) {
        LockUnlockCustomerAccountResponse lockUnlockCustomerAccountResponse = new LockUnlockCustomerAccountResponse();
        BatchResponseCode responseCode = lockUnlockCustomerAccountValidationService.validate(iisn, accountContent);
        if (responseCode == BatchResponseCode.SUCCCESS) {
            String inputIssuerCustomerId = accountContent.getIssuerCustomerId();
            String inputUserName = accountContent.getUserName();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(VaultConstants.LOCK_UNLOCK_ISSUER_CUSTOMER_ID, inputIssuerCustomerId);
            params.put(VaultConstants.LOCK_UNLOCK_USER_NAME, inputUserName);
            Customer customer = ListUtil.getFirstOrNull(customerDao.findByName(
                    VaultConstants.LOCK_UNLOCK_CUSTOMER_ACCOUNT_SELECT, params));
            if (customer != null) {
                String accountLockReasonCode = null;
                String inputLockState = accountContent.getLockState();
                Boolean lockState = Boolean.FALSE;
                String unlockedAccount = VaultConstants.ACCOUNT_UNLOCKED;
                String lockedAccount = VaultConstants.ACCOUNT_LOCKED;
                logger.debug("issuerCustomerId={}, userName={}", customer.getIssuerCustomerId(), customer.getUserName());
                if (inputLockState != null
                        && (lockedAccount.equalsIgnoreCase(inputLockState) || unlockedAccount
                                .equalsIgnoreCase(inputLockState))) {
                    if (lockedAccount.equalsIgnoreCase(inputLockState)) {
                        lockState = Boolean.TRUE;
                        accountLockReasonCode = LockCode.LOCKED_BY_BATCH.getCode();
                    } else {
                        accountLockReasonCode = LockCode.UNLOCKED.getCode();
                    }
                    customer.setAccountLocked(lockState);
                    customer.setAccountLockedReasonCode(accountLockReasonCode);
                    customer.setAccountLockedAt(DateUtil.getUtcTime());
                    customer = customerDao.save(customer);
                    lockUnlockCustomerAccountResponse.setResponseCode(responseCode.getResponseStr());
                } else {
                    lockUnlockCustomerAccountResponse.setResponseCode(BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH
                            .getResponseStr());
                }
            } else {
                lockUnlockCustomerAccountResponse.setResponseCode(BatchResponseCode.USER_ACCOUNT_NOT_FOUND
                        .getResponseStr());
            }
        } else {
            lockUnlockCustomerAccountResponse.setResponseCode(responseCode.getResponseStr());
        }
        LockUnlockCustomerAccountUtil.copyValues(lockUnlockCustomerAccountResponse, accountContent);
        return lockUnlockCustomerAccountResponse;

    }

}
