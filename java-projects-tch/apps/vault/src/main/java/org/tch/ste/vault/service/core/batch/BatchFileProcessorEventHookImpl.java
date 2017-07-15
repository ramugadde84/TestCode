/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessingResult;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessorEventHook;
import org.tch.ste.vault.service.internal.batchhistory.BatchHistoryFacade;
import org.tch.ste.vault.util.BatchFileUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchFileProcessorEventHookImpl implements BatchFileProcessorEventHook {

    private static Logger logger = LoggerFactory.getLogger(BatchFileProcessorEventHookImpl.class);

    @Autowired
    private BatchHistoryFacade batchHistoryFacade;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchFileProcessorEventHook#
     * batchStart(java.io.File)
     */
    @Override
    public void batchStart(File file) {
        String[] splitFileName = file.getName().split(VaultConstants.FILE_PART_SEPERATOR);
        batchHistoryFacade.processingStart(splitFileName[VaultConstants.IISN_PART],
                splitFileName[VaultConstants.TIMESTAMP_PART], splitFileName[VaultConstants.FILE_TYPE_PART]);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchFileProcessorEventHook#
     * batchComplete(java.io.File,
     * org.tch.ste.vault.service.internal.batch.BatchFileProcessingResult)
     */
    @Override
    public void batchComplete(File file, BatchFileProcessingResult batchFileProcessingResult) {
        try {
            boolean success = true;
            int recordCount = 0;
            if (batchFileProcessingResult == null || !batchFileProcessingResult.isSuccess()) {
                logger.info("Moving file: {} to error directory since batch failed", file.getAbsolutePath());
                success = false;
                BatchFileUtil.moveToErrorDir(file);
            } else {
                logger.info("Moving file: {} to processed directory since batch succeeded", file.getAbsolutePath());
                recordCount = batchFileProcessingResult.getRecordCount();
                BatchFileUtil.moveToProcessedDir(file);
            }
            String[] splitFileName = file.getName().split(VaultConstants.FILE_PART_SEPERATOR);
            batchHistoryFacade.processingEnd(splitFileName[VaultConstants.IISN_PART],
                    splitFileName[VaultConstants.TIMESTAMP_PART], splitFileName[VaultConstants.FILE_TYPE_PART],
                    recordCount, success);
        } catch (Throwable t) {
            logger.warn("Error while attempting to move file: {}", file.getAbsolutePath(), t);
        }
    }

}
