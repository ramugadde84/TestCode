/**
 * 
 */
package org.tch.ste.auth.service.internal.lockoutuser;

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
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.constant.BatchOutputFileName;
import org.tch.ste.auth.service.internal.batch.BatchOutputFileNameCreationService;
import org.tch.ste.infra.batch.service.reporthistory.ReportHistoryService;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.constant.ReportHistoryConstants;
import org.tch.ste.infra.util.DateUtil;

/**
 * This is the implementation class for Lock out customers information.
 * 
 * @author ramug
 * 
 */
@Service
public class LockedOutUsersReportGenerationServiceImpl implements LockedOutUsersReportGenerationService {

    private static Logger logger = LoggerFactory.getLogger(LockedOutUsersReportGenerationServiceImpl.class);

    @Autowired
    private BatchOutputFileNameCreationService batchOutputFileNameCreationService;

    @Autowired
    @Qualifier("synchronousJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("lockoutuser")
    private Job job;

    @Autowired
    private ReportHistoryService reportHistoryService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.lockoutuser.LockoutUserService#
     * generateLockoutUsersReport(java.lang.String)
     */
    @Override
    public void generateLockoutUsersReport(String iisn) {
        logger.debug("Generate Lock out user report for iisn  {}", iisn);
        try {
            String timeStamp = DateUtil.getCurrentTimestamp();
            String fileType = BatchOutputFileName.LOCKOUTREPORTDAILY.name();
            String reportType = ReportHistoryConstants.LOCKOUT.name();
            Date reportStartTime = reportHistoryService.getReportStartTime(reportType);
            Date reportEndTime = reportHistoryService.getReportEndTime(reportType);
            if (logger.isInfoEnabled()) {
                logger.info("Locked out users Report Start Time: "
                        + DateUtil.formatDate(reportStartTime, "yyyy-MM-dd HH:mm:ss") + " Report End Time: "
                        + DateUtil.formatDate(reportEndTime, "yyyy-MM-dd HH:mm:ss"));
            }

            JobExecution exec = jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addString(
                                    AuthConstants.JOB_PARAM_TEMP_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createTempOutputFileName(fileType, iisn,
                                            timeStamp))
                            .addDate(InfraConstants.REPORT_START_TIME, reportStartTime)
                            .addDate(InfraConstants.REPORT_END_TIME, reportEndTime)
                            .addString(InfraConstants.REPORT_TYPE, reportType)
                            .addString(AuthConstants.JOB_PARAM_TIMESTAMP, timeStamp)
                            .addString(AuthConstants.JOB_PARAM_IISN, iisn)
                            .addString(
                                    AuthConstants.JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createActualOutputFileName(fileType, iisn,
                                            timeStamp)).toJobParameters());

            if (exec.getExitStatus() == ExitStatus.FAILED) {
                logger.warn("Lock out users report for IISN: {} failed", iisn);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.error("An error occurred while generating the daily locked out users report for issuer {}."
                    ,iisn);
            logger.error("Actual error is: ",e);
        } catch (Throwable t) {
            logger.error("An error occurred while generating the daily locked out users report for issuer {}."
                    ,iisn);
            logger.error("Actual error is: ",t);
        }

        logger.info("Completed Lock out users report generation for iisn {}", iisn);

    }

}
