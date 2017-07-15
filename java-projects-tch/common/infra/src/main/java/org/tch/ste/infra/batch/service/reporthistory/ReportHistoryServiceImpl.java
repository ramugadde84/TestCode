/**
 * 
 */
package org.tch.ste.infra.batch.service.reporthistory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.domain.entity.ReportHistory;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.constant.InfraQueryConstants;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;

/**
 * @author anus
 * 
 */
public class ReportHistoryServiceImpl implements ReportHistoryService {

    @Autowired
    @Qualifier("reportHistoryDao")
    private JpaDao<ReportHistory, Integer> reportHistoryDao;

    @Autowired
    private VaultProperties vaultProperties;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.batch.reporthistory.ReportHistoryService
     * #getReportStartTime(java.lang.String)
     */
    @Override
    public Date getReportStartTime(String reportType) {
        ReportHistory reportHistory = getReportHistory(reportType);
        return reportHistory != null ? reportHistory.getLastReportTime() : DateUtil.getEpochStart();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.batch.reporthistory.ReportHistoryService
     * #getReportEndTime(java.lang.String)
     */
    @Override
    public Date getReportEndTime(String reportType) {
        return DateUtil.subtractTimeInMinutes(DateUtil.getUtcTime(),
                Integer.parseInt(vaultProperties.getKey(MivConstants.REPORT_TIME)));
    }

    /**
     * Returns the report history.
     * 
     * @param reportType
     *            String - The report type.
     * @return ReportHistory - The report history.
     */
    private ReportHistory getReportHistory(String reportType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(InfraQueryConstants.REPORT_TYPE, reportType);
        return ListUtil.getFirstOrNull(reportHistoryDao.findByName(InfraQueryConstants.GET_REPORT_HISTORY, params));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.batch.reporthistory.ReportHistoryService
     * #save(java.lang.String, java.util.Date)
     */
    @Override
    @Transactional(readOnly = false)
    public void save(String reportType, Date lastReportTime) {
        ReportHistory reportHistory = getReportHistory(reportType);
        if (reportHistory == null) {
            reportHistory = new ReportHistory();
            reportHistory.setReportType(reportType);
            reportHistory.setLastReportTime(lastReportTime);
        } else {
            reportHistory.setLastReportTime(lastReportTime);
        }
        reportHistoryDao.save(reportHistory);
    }
}
