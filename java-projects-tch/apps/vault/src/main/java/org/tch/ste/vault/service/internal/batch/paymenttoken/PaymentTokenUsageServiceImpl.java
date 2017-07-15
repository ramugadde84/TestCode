/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.paymenttoken;

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
import org.tch.ste.domain.entity.DetokenizationReportHistory;
import org.tch.ste.infra.batch.service.reporthistory.ReportHistoryService;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.constant.ReportHistoryConstants;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.vault.constant.BatchOutputFileName;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.batch.BatchOutputFileNameCreationService;

/**
 * Payment Token Usage Report.
 * 
 * @author anus
 * 
 */
@Service
public class PaymentTokenUsageServiceImpl implements PaymentTokenUsageService {

    private static Logger logger = LoggerFactory.getLogger(PaymentTokenUsageServiceImpl.class);

    @Autowired
    @Qualifier("detokenizationReportHistoryDao")
    private JpaDao<DetokenizationReportHistory, Integer> detokenizationReportHistoryDao;

    @Autowired
    @Qualifier("synchronousJobLauncher")
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("paymentTokenUsage")
    private Job job;

    @Autowired
    private BatchOutputFileNameCreationService batchOutputFileNameCreationService;

    @Autowired
    private ReportHistoryService reportHistoryService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.paymenttoken.
     * PaymentTokenUsageService#generateDetokenizationReport(java.lang.String)
     */
    @Override
    public void generateDetokenizationReport(String iisn) {
        logger.debug("Generate Detokenization report for iisn  {}", iisn);
        try {
            String timeStamp = DateUtil.getCurrentTimestamp();
            String fileType = BatchOutputFileName.TOKENUSEREPORTDAILY.name();
            String reportType = ReportHistoryConstants.TOKEN_USAGE.name();
            Date reportStartTime = reportHistoryService.getReportStartTime(reportType);
            Date reportEndTime = reportHistoryService.getReportEndTime(reportType);
            JobExecution exec = jobLauncher.run(
                    job,
                    new JobParametersBuilder()
                            .addDate(InfraConstants.REPORT_START_TIME, reportStartTime)
                            .addDate(InfraConstants.REPORT_END_TIME, reportEndTime)
                            .addString(InfraConstants.REPORT_TYPE, reportType)
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
                logger.warn("Generate Detokenization report for IISN: {} failed", iisn);
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            logger.warn("An error occurred while generating the daily PAYMENT TOKEN use report for issuer: " + iisn, e);
        } catch (Throwable t) {
            logger.warn("Error in batch", t);
        }

        logger.info("Completed Detokenization report generation for iisn {}", iisn);
    }
}
