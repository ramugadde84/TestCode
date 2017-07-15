/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.lockoutuser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.tch.ste.domain.entity.LoginHistory;

/**
 * @author ramug
 * 
 */
public class LockedOutUsersAttemptsReportBatchProcessor implements ItemProcessor<LoginHistory, LoginHistory> {

    private static final int HASH_SIZE = 10003;

    private Map<String, Boolean> enteredValues = new HashMap<String, Boolean>(HASH_SIZE);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.batch.item.ItemProcessor#process(java.lang.Object)
     */
    @Override
    public LoginHistory process(LoginHistory item) throws Exception {
        LoginHistory loginHistory = null;
        String customerData = fetchCustomerId(item);
        if (!enteredValues.containsKey(customerData)) {
            enteredValues.put(customerData, Boolean.TRUE);
            loginHistory = item;
        }
        return loginHistory;

    }

    /**
     * Fetch customer Id.
     * 
     * @param item
     *            - storing item.
     * @return the value.
     */
    private static String fetchCustomerId(LoginHistory item) {
        return item.getCustomer().getUserName();
    }
}
