/**
 * 
 */
package org.tch.ste.vault.service.internal.batchhistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchHistoryFacadeImpl implements BatchHistoryFacade {

    @Autowired
    private BatchHistoryService batchHistoryService;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryFacade#
     * hasProcessed(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean hasProcessed(String iisn, String timeStamp, String batchFileType) {
        return batchHistoryService.hasProcessed(iisn, timeStamp, batchFileType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryFacade#
     * processingStart(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void processingStart(String iisn, String timeStamp, String batchFileType) {
        batchHistoryService.processingStart(iisn, timeStamp, batchFileType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batchhistory.BatchHistoryFacade#
     * processingEnd(java.lang.String, java.lang.String, java.lang.String, int,
     * boolean)
     */
    @Override
    public void processingEnd(String iisn, String timeStamp, String batchFileType, int recordCount, boolean success) {
        batchHistoryService.processingEnd(iisn, timeStamp, batchFileType, recordCount, success);
    }

}
