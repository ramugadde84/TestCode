/**
 * 
 */
package org.tch.ste.auth.service.internal.authattempts;

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
 * @author anus
 * 
 */
@Service
public class AuthAttemptsServiceImpl implements AuthAttemptsService {

    private static Logger logger = LoggerFactory.getLogger(AuthAttemptsServiceImpl.class);

    @Autowired
    private BatchOutputFileNameCreationService batchOutputFileNameCreationService;

    @Autowired
    private ReportHistoryService reportHistoryService;

    @Autowired
    @Qualifier("synchronousJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("authAttemptsUsers")
    private Job job;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.auth.service.internal.authattempts.AuthAttemptService#
     * generateAuthAttemptsReport(java.lang.String)
     */
    @Override
    public void generateAuthAttemptsReport(String iisn) {
        logger.debug("Generate Authentication Attempts user report for iisn  {}", iisn);
        try {
            String timeStamp = DateUtil.getCurrentTimestamp();
            String fileType = BatchOutputFileName.AUTHREPORTDAILY.name();
            String reportType = ReportHistoryConstants.AUTH_ATTEMPTS.name();
            Date reportStartTime = reportHistoryService.getReportStartTime(reportType);
            Date reportEndTime = reportHistoryService.getReportEndTime(reportType);

            if (logger.isInfoEnabled()) {
                logger.info("Authentication Attempts Report Start Time: "
                        + DateUtil.formatDate(reportStartTime, "yyyy-MM-dd HH:mm:ss") + " Report End Time: "
                        + DateUtil.formatDate(reportEndTime, "yyyy-MM-dd HH:mm:ss"));
            }

            JobExecution exec = jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addDate(InfraConstants.REPORT_START_TIME, reportStartTime)
                            .addDate(InfraConstants.REPORT_END_TIME, reportEndTime)
                            .addString(InfraConstants.REPORT_TYPE, reportType)
                            .addString(
                                    AuthConstants.JOB_PARAM_TEMP_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createTempOutputFileName(fileType, iisn,
                                            timeStamp))

                            .addString(AuthConstants.JOB_PARAM_TIMESTAMP, timeStamp)
                            .addString(AuthConstants.JOB_PARAM_IISN, iisn)
                            .addString(
                                    AuthConstants.JOB_PARAM_ACTUAL_OUTPUT_FILE_NAME,
                                    batchOutputFileNameCreationService.createActualOutputFileName(fileType, iisn,
                                            timeStamp)).toJobParameters());

            if (exec.getExitStatus() == ExitStatus.FAILED) {
                logger.warn("Authentication Attempts report for IISN: {} failed", iisn);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.warn("An error occurred while generating Authentication Attempts Report : " + iisn, e);
        } catch (Throwable t) {
            logger.warn("Error in batch", t);
        }

        logger.info("Completed Authentication Attempts report generation for iisn {}", iisn);

    }

}
