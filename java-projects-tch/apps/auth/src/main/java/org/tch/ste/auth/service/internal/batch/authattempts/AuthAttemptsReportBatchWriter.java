/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.authattempts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.util.BatchFileWriterUtil;
import org.tch.ste.domain.entity.LoginHistory;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.StringUtil;

/**
 * @author anus
 * 
 */
public class AuthAttemptsReportBatchWriter implements ItemWriter<LoginHistory> {
    private static Logger logger = LoggerFactory.getLogger(AuthAttemptsReportBatchWriter.class);

    private int numRecords = 0;

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
    public AuthAttemptsReportBatchWriter(String outputFileName, String timeStamp, String iisn) throws IOException {
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
        logger.debug("Entered into the Authentication Attempts User Report Batch Writer's write method");
        List<String> lines = new ArrayList<String>();

        for (LoginHistory loginHistory : items) {
            lines.add(processAuthAttemptsRequest(loginHistory));
            ++numRecords;
        }
        BatchFileWriterUtil.writeLines(file, lines);

    }

    /**
     * @param loginHistory
     * @return
     */
    private String processAuthAttemptsRequest(LoginHistory loginHistory) {
        return getContentToWrite(loginHistory);
    }

    /**
     * Converts to a fixed length record.
     * 
     * @return String - The record to be written.
     * 
     * @param loginHistory
     */
    private String getContentToWrite(LoginHistory loginHistory) {
        StringBuilder retVal = new StringBuilder();
        retVal.append(numRecords + 1);
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(loginHistory.getCustomer().getIssuerCustomerId()));
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(loginHistory.getUserName()));
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append(DateUtil.formatDate(loginHistory.getLoginAttemptTime(),InfraConstants.STD_TIMESTAMP_FMT));
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(loginHistory.getClientIpAddress()));
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(loginHistory.getClientUserAgent()));
        retVal.append(AuthConstants.CSV_SEPERATOR);
        retVal.append((loginHistory.isLoginSuccessful())?1:0);
        return retVal.toString();

    }
}
