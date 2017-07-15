/**
 * 
 */
package org.tch.ste.vault.service.internal.batchhistory;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.constant.BoolInt;
import org.tch.ste.domain.entity.BatchFileHistory;
import org.tch.ste.domain.entity.BatchFileType;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.util.BatchFileUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchHistoryServiceImpl implements BatchHistoryService {

    @Autowired
    @Qualifier("batchFileHistoryDao")
    private JpaDao<BatchFileHistory, Integer> batchFileHistoryDao;

    @Autowired
    @Qualifier("batchFileTypeDao")
    private JpaDao<BatchFileType, Integer> batchFileTypeDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryService#
     * hasProcessed(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean hasProcessed(String iisn, String timeStamp, String batchFileType) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_BATCH_FILE_NAME,
                BatchFileUtil.createFileName(batchFileType, iisn, timeStamp));
        return ListUtil.getFirstOrNull(batchFileHistoryDao.findByName(
                VaultQueryConstants.GET_EXISTING_BATCH_FILE_HISTORY, params)) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryService#
     * processingStart(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = false)
    public void processingStart(String iisn, String timeStamp, String batchFileType) {
        BatchFileHistory batchFileHistory = new BatchFileHistory();
        batchFileHistory.setBatchFileName(BatchFileUtil.createFileName(batchFileType, iisn, timeStamp));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.BATCH_FILE_TYPE, batchFileType);
        BatchFileType fileType = ListUtil.getFirstOrNull(batchFileTypeDao.findByName(
                VaultQueryConstants.FIND_BY_BATCH_FILE_TYPE, params));
        if (fileType != null) {
            batchFileHistory.setBatchFileType(fileType);
            batchFileHistory.setProcessingStartTime(DateUtil.getUtcTime());
            batchFileHistoryDao.save(batchFileHistory);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryService#
     * processingEnd(java.lang.String, java.lang.String, java.lang.String, int,
     * boolean)
     */
    @Override
    @Transactional(readOnly = false)
    public void processingEnd(String iisn, String timeStamp, String batchFileType, int recordCount, boolean success) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_BATCH_FILE_NAME,
                BatchFileUtil.createFileName(batchFileType, iisn, timeStamp));
        BatchFileHistory batchFileHistory = ListUtil.getFirstOrNull(batchFileHistoryDao.findByName(
                VaultQueryConstants.GET_EXISTING_BATCH_FILE_HISTORY, params));
        // HACK???
        batchFileHistory = batchFileHistoryDao.get(batchFileHistory.getId());
        batchFileHistory.setRecordCount(recordCount);
        batchFileHistory.setSucessfullyProcessed((success) ? BoolInt.TRUE.ordinal() : BoolInt.FALSE.ordinal());
        batchFileHistory.setProcessingEndTime(DateUtil.getUtcTime());
    }

}
