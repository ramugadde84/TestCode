/**
 * 
 */
package org.tch.ste.infra.batch.service.reporthistory;

import java.util.Date;

/**
 * @author anus
 *
 */
public interface ReportHistoryService {

    /**
     * Fetches the report start time.
     * 
     * @param reportType String - The report type.
     * @return Date - The start time.
     */
    Date getReportStartTime(String reportType);
    
    /**
     * Fetches the report end time.
     * 
     * @param reportType String - The report end time.
     * @return Date - The end time.
     */
    Date getReportEndTime(String reportType);
    
    /**
     * Save the last date for the given report type.
     * 
     * @param reportType String - The report type.
     * @param lastReportTime Date - The time based on which the last report has been generated.
     */
    public void save(String reportType,Date lastReportTime);
}
