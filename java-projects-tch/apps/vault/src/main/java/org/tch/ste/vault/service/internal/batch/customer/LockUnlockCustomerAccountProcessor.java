/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.vault.constant.BatchOutputFileName;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.batch.AbstractBatchFileProcessor;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessingResult;
import org.tch.ste.vault.service.internal.batch.BatchOutputFileNameCreationService;

/**
 * This is a processor for Lock or Unlock customer accounts.
 * 
 * @author ramug
 * 
 */
@Service("SETACCOUNTLOCK")
public class LockUnlockCustomerAccountProcessor extends AbstractBatchFileProcessor {

    private static Logger logger = LoggerFactory.getLogger(LockUnlockCustomerAccountProcessor.class);

    @Autowired
    @Qualifier("synchronousJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("lockUnlockCustomerAccounts")
    private Job job;

    @Autowired
    private BatchOutputFileNameCreationService batchOutputFileNameCreationService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.AbstractBatchFileProcessor#doProcess
     * (java.io.File)
     */
    @Override
    protected BatchFileProcessingResult doProcess(File file) {
        BatchFileProcessingResult retVal = new BatchFileProcessingResult();
        retVal.setSuccess(true);
        try {
            String[] splitFileNames = file.getName().split(VaultConstants.FILE_PART_SEPERATOR);
            String fileType = BatchOutputFileName.SETACCOUNTLOCKCONF.name();
            String iisn = splitFileNames[VaultConstants.IISN_PART];
            String timeStamp = splitFileNames[VaultConstants.TIMESTAMP_PART];
            JobExecution exec = jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addString(VaultConstants.BATCH_INPUT_FILE_NAME, file.getAbsolutePath())
                            .addString(VaultConstants.JOB_PARAM_IISN, iisn)
                            .addString(VaultConstants.JOB_PARAM_TIMESTAMP,
                                    DateUtil.getCurrentTimestamp())
                            .addString(VaultConstants.JOB_PARAM_FILE_TYPE,
                                    splitFileNames[VaultConstants.FILE_TYPE_PART])
                            .addString(
                                    VaultConstants.JOB_PARAM_TEMP_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createTempOutputFileName(fileType, iisn,
                                            timeStamp))
                            .addString(
                                    VaultConstants.JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createActualOutputFileName(fileType, iisn,
                                            timeStamp)).toJobParameters());
            ExecutionContext executionContext = exec.getExecutionContext();
            if (exec.getExitStatus() == ExitStatus.FAILED) {
                retVal.setSuccess(false);
            } else if (!BatchResponseCode.SUCCCESS.getResponseStr().equals(
                    executionContext.getString(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, null))) {
                retVal.setSuccess(false);
            } else {
                retVal.setRecordCount(executionContext.getInt(VaultConstants.NUM_PROCESSED_RECORDS, 0));
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.warn("Error while executing Lock or Unlock customer accounts: {} ", file.getAbsolutePath(), e);
            retVal.setSuccess(false);
        }
        return retVal;
    }
}
