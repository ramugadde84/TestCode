/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.passwordexpiry;

import java.util.Date;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.vault.constant.BatchOutputFileName;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.batch.BatchOutputFileNameCreationService;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class PasswordExpiryServiceImpl implements PasswordExpiryService {

    private static Logger logger = LoggerFactory.getLogger(PasswordExpiryServiceImpl.class);

    @Autowired
    @Qualifier("synchronousJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("passwordExpiry")
    private Job job;

    @Autowired
    private BatchOutputFileNameCreationService batchOutputFileNameCreationService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.passwordexpiry.PasswordExpiryService
     * #expirePasswords(java.lang.String)
     */
    @Override
    public void expirePasswords(String iisn) {
        logger.info("Starting password expiry for iisn {}", iisn);
        try {
            Date expiryDate = DateUtil.add(DateUtil.getUtcTime(), VaultConstants.PASSWORD_EXPIRY_TIME);
            String timeStamp = DateUtil.getCurrentTimestamp();
            String fileType = BatchOutputFileName.CREDUPDATEDAILY.name();
            JobExecution exec = jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addDate(VaultConstants.EXPIRY_DATE, expiryDate)
                            .addString(
                                    VaultConstants.JOB_PARAM_TEMP_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createTempOutputFileName(fileType, iisn,
                                            timeStamp))
                            .addString(VaultConstants.JOB_PARAM_TIMESTAMP, timeStamp)
                            .addString(VaultConstants.JOB_PARAM_IISN, iisn)
                            .addString(
                                    VaultConstants.JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createActualOutputFileName(fileType, iisn,
                                            timeStamp)).toJobParameters());
            if (exec.getExitStatus() == ExitStatus.FAILED) {
                logger.warn("Password expiry job for IISN: {} failed", iisn);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.warn("Error while executing password expiry job for iisn: " + iisn, e);
        } catch (Throwable t) {
            logger.warn("Error in batch", t);
        }

        logger.info("Completed password expiry for iisn {}", iisn);
    }
}
