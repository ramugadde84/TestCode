/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.passwordexpiry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.password.PasswordGenerationService;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * Writer for password expiry. - Writes to a temporary output file. - Updates
 * the database with newly generated usernames & passwords for Auth Option 4. -
 * Locks in case of using Auth Option 3.
 * 
 * @author Karthik.
 * 
 */
public class PasswordExpiryWriter implements ItemWriter<Customer> {

    private int numRecords = 0;

    private File file;

    @Autowired
    private PasswordGenerationService passwordGenerationService;

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file.
     * @throws IOException
     *             IOException - If not able to create a temp file.
     */
    public PasswordExpiryWriter(String outputFileName) throws IOException {
        file = new File(outputFileName);
        if (!file.exists() && !file.createNewFile()) {
            throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends Customer> items) throws Exception {
        List<String> lines = new ArrayList<String>();
        for (Customer customer : items) {
            lines.add(processCustomer(customer));
            ++numRecords;
        }
        BatchFileWriterUtil.writeLines(file, lines);
    }

    /**
     * Processes the customer.
     * 
     * @param customer
     *            Customer - The customer.
     */
    private String processCustomer(Customer customer) {
        Password password = passwordGenerationService.generatePassword();
        customer.setHashedPassword(password.getHashedPassword());
        customer.setPasswordSalt(password.getPasswordSalt());
        customer.setLastPasswordChange(DateUtil.getUtcTime());
        customerDao.save(customer);
        return getContentToWrite(customer, password);
    }

    /**
     * Converts to a fixed length record.
     * 
     * @param customer
     *            Customer - The customer.
     * @param password
     *            Password - The password.
     * @return String - The record to be written.
     */
    private String getContentToWrite(Customer customer, Password password) {
        StringBuilder retVal = new StringBuilder();
        retVal.append(numRecords + 1);
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(customer.getIssuerCustomerId());
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(customer.getUserName());
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(password.getPlainTextPassword());
        retVal.append(VaultConstants.CSV_SEPERATOR);
        return retVal.toString();
    }
}
