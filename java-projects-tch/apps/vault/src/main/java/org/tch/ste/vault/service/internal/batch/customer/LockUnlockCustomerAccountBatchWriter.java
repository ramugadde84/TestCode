/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * Lock Unlock Customer Account Batch Writer,writes to flat file or any
 * persistent location.
 * 
 * @author ramug
 * 
 */
public class LockUnlockCustomerAccountBatchWriter implements ItemWriter<LockUnlockCustomerAccountContent> {

    private File outputFile;

    private String iisn;

    private String headerTrailerValidationResult;

    private LockUnlockCustomerAccountService accountService;

    @Autowired
    @Qualifier("lockUnlockAccountWriter")
    private FixedLengthWriter<LockUnlockCustomerAccountResponse> lociFixedLengthWriter;

    /**
     * Constructor.
     * 
     * @param outputFileName
     *            - the output file name.
     * @param iisn
     *            - the iisn value.
     * @param accountService
     *            - the account service object.
     * @throws IOException
     *             Thrown.
     */
    public LockUnlockCustomerAccountBatchWriter(String outputFileName, String iisn,
            LockUnlockCustomerAccountService accountService) throws IOException {
        this.outputFile = new File(outputFileName);
        this.iisn = iisn;
        if (!outputFile.exists()) {
            if (!outputFile.createNewFile()) {
                throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
            }
        }
        this.accountService = accountService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends LockUnlockCustomerAccountContent> items) throws Exception {
        List<String> lines = new ArrayList<String>();
        if (BatchResponseCode.SUCCCESS.getResponseStr().equals(headerTrailerValidationResult)) {
            for (LockUnlockCustomerAccountContent accountContent : items) {
                lines.add(lociFixedLengthWriter.write(accountService.lockUnlockCustomers(iisn, accountContent)));
            }
        } else {
            for (LockUnlockCustomerAccountContent accountContent : items) {
                lines.add(convertToOutput(accountContent, headerTrailerValidationResult));
            }
        }
        BatchFileWriterUtil.writeLines(outputFile, lines);
    }

    /**
     * Converts the input record to an output record with the given error code.
     * 
     * @param accountContent
     *            lock or unlock account - The content.
     * @param validationErrorCode
     *            String - The error code.
     * @return String - The output string.
     */
    private String convertToOutput(LockUnlockCustomerAccountContent accountContent, String validationErrorCode) {
        LockUnlockCustomerAccountResponse output = new LockUnlockCustomerAccountResponse();
        LockUnlockCustomerAccountUtil.copyValues(output, accountContent);
        output.setResponseCode(BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR.getResponseStr());
        return lociFixedLengthWriter.write(output);
    }

    /**
     * Retrieves the header trailer result.
     * 
     * @param stepExecution
     *            StepExecution - The step execution.
     */
    @BeforeStep
    public void retrieveHeaderTrailerResult(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        this.headerTrailerValidationResult = jobContext
                .getString(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, null);
    }

}