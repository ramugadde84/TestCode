/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.lockoutuser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.constant.LockedOutReasonConstants;
import org.tch.ste.auth.util.BatchFileWriterUtil;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.LoginHistory;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.StringUtil;

/**
 * The Writer class which handles to write the data to flat files etc.
 * 
 * @author ramug
 * 
 */
public class LockedOutUsersAttemptsReportBatchWriter extends LockedOutUsersReportBatchWriter implements ItemWriter<LoginHistory> {

    private static Logger logger = LoggerFactory.getLogger(LockedOutUsersAttemptsReportBatchWriter.class);

    private File file;

   

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file.
     * @param timeStamp
     *            String - The timestamp.
     * @param iisn
     *            String - The iisn.
     * @throws IOException
     *             IOException - If not able to create a temp file.
     */
    public LockedOutUsersAttemptsReportBatchWriter(String outputFileName, String timeStamp, String iisn)
            throws IOException {
        file = new File(outputFileName);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends LoginHistory> items) throws Exception {
        logger.debug("Entered into the Lock Out Users Report Batch Writer's write method");
        List<String> lines = new ArrayList<String>();
        for (LoginHistory loginHistory : items) {
            lines.add(processLoginHistoryRequest(loginHistory));
            ++super.value;
        }
        BatchFileWriterUtil.writeLines(file, lines);

    }

    /**
     * @param loginHistory
     * @return
     */
    protected String processLoginHistoryRequest(LoginHistory loginHistory) {
        return getContentToWrite(loginHistory);
    }

    /**
     * Converts to a fixed length record.
     * 
     * @return String - The record to be written.
     * 
     * @param loginHistory
     */
    protected String getContentToWrite(LoginHistory loginHistory) {
        StringBuilder retVal = new StringBuilder();
        if (loginHistory != null) {
            Customer customer = loginHistory.getCustomer();
            if (customer != null) {
                retVal.append(this.value + 1);
                retVal.append(AuthConstants.CSV_SEPERATOR);
                retVal.append(StringUtil.convertNullToBlank(customer.getIssuerCustomerId()));
                retVal.append(AuthConstants.CSV_SEPERATOR);
                retVal.append(StringUtil.convertNullToBlank(customer.getUserName()));
                retVal.append(AuthConstants.CSV_SEPERATOR);
                Boolean accountLocked = customer.getAccountLocked();
                String accountLockedData;
                if (accountLocked) {
                    accountLockedData = "L";
                    retVal.append(StringUtil.convertNullToBlank(accountLockedData));
                } else {
                    accountLockedData = "U";
                    retVal.append(StringUtil.convertNullToBlank(accountLockedData));
                }
                retVal.append(AuthConstants.CSV_SEPERATOR);
                String customerLockedOutReasonCode = customer.getAccountLockedReasonCode();
                if (customerLockedOutReasonCode != null) {
                    switch (customerLockedOutReasonCode) {
                    case "1":
                        retVal.append(StringUtil.convertNullToBlank(LockedOutReasonConstants.ONE.getResponseStr()));
                        break;
                    case "2":
                        retVal.append(StringUtil.convertNullToBlank(LockedOutReasonConstants.TWO.getResponseStr()));
                        break;
                    case "3":
                        retVal.append(StringUtil.convertNullToBlank(LockedOutReasonConstants.THREE.getResponseStr()));
                        break;
                    case "4":
                        retVal.append(StringUtil.convertNullToBlank(LockedOutReasonConstants.FOUR.getResponseStr()));
                        break;
                    case "5":
                        retVal.append(StringUtil.convertNullToBlank(LockedOutReasonConstants.FIVE.getResponseStr()));
                        break;
                    default:
                        break;

                    }
                }
                retVal.append(AuthConstants.CSV_SEPERATOR);
                retVal.append(DateUtil.formatDate(customer.getAccountLockedAt(), InfraConstants.STD_TIMESTAMP_FMT));
            }
        }

        return retVal.toString();

    }
    

}
