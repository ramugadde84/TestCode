/**
 * 
 */
package org.tch.ste.infra.batch;

import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.tch.ste.infra.batch.service.reporthistory.ReportHistoryService;

/**
 * Listens to the report history.
 * 
 * @author Karthik.
 * 
 */
public class ReportHistoryManagementListener implements JobExecutionListener {

    @Autowired
    private ReportHistoryService reportHistoryService;

    private String reportType;

    private Date lastReportTime;

    /**
     * Overloaded constructor.
     * 
     * @param reportType
     *            String - The report type.
     * @param lastReportTime
     *            Date - The last report time.
     */
    public ReportHistoryManagementListener(String reportType, Date lastReportTime) {
        this.reportType = reportType;
        this.lastReportTime = new Date(lastReportTime.getTime());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.
     * springframework.batch.core.JobExecution)
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.
     * springframework.batch.core.JobExecution)
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (ExitStatus.COMPLETED.equals(jobExecution.getExitStatus())) {
            reportHistoryService.save(reportType, lastReportTime);
        }
    }

}
