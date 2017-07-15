/**
 * 
 */
package org.tch.ste.admin.service.core.customermanagement;

import java.util.List;

import org.tch.ste.admin.domain.dto.CustomerManagement;
import org.tch.ste.admin.domain.dto.UserPassword;

/**
 * @author anus
 * 
 */
public interface CustomerManagementService {

    /**
     * This method is used to reset the password
     * 
     * @param id
     *            Integer - the id.
     * @param iisn
     *            - The IISN Number.
     * @return UserPassword - The new password.
     */
    UserPassword resetPassword(Integer id, String iisn);

    /**
     * Locks the account.
     * 
     * @param id
     *            - id
     * @param iisn
     *            - iisn
     * @return boolean - Success/Failure.
     */
    boolean lock(Integer id, String iisn);

    /**
     * Unlocks the account.
     * 
     * @param id
     *            - id
     * @param iisn
     *            - iisn
     * @return boolean - Success/Failure.
     */
    boolean unlock(Integer id, String iisn);

    /**
     * Returns the list of customers.
     * 
     * @param iisn
     *            String - The iisn
     * @return List<CustomerManagement> - the customers
     * 
     */
    public List<CustomerManagement> getCustomers(String iisn);

}