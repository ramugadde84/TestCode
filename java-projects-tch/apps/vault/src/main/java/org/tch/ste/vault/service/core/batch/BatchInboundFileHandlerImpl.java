/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessingStrategy;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessor;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessorEventHook;
import org.tch.ste.vault.service.internal.batch.BatchFileValidationResult;
import org.tch.ste.vault.service.internal.batch.BatchFileValidator;
import org.tch.ste.vault.util.BatchFileUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchInboundFileHandlerImpl implements BatchInboundFileHandler {

    private static Logger logger = LoggerFactory.getLogger(BatchInboundFileHandlerImpl.class);

    @Autowired
    private BatchFileProcessingStrategy batchFileProcessingStrategy;

    @Autowired
    private BatchFileValidator batchFileValidator;

    @Autowired
    private BatchFileProcessorEventHook batchFileProcessorEventHook;

    @Autowired
    @Qualifier("defaultBatchFileProcessor")
    private BatchFileProcessor defaultBatchFileProcessor;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.batch.BatchInboundFileHandler#handle(java
     * .io.File)
     */
    @Override
    public void handle(File file) {
        try {
            File inProcessFile = BatchFileUtil.moveToInProcessDir(file);
            String inProcessFilePath = inProcessFile.getAbsolutePath();
            BatchFileValidationResult validationResult = batchFileValidator.validate(inProcessFile);
            if (validationResult.isSuccess()) {
                BatchFileProcessor batchFileProcessor = batchFileProcessingStrategy
                        .getBatchFileProcessor(inProcessFile);
                String[] splitVals = inProcessFile.getName().split(VaultConstants.FILE_PART_SEPERATOR);
                if (batchFileProcessor != null) {
                    batchFileProcessor.process(splitVals[VaultConstants.IISN_PART], inProcessFile,
                            batchFileProcessorEventHook);
                } else {
                    logger.warn("No Batch processor set up for file: {}", inProcessFilePath);
                    defaultBatchFileProcessor.process(splitVals[VaultConstants.IISN_PART], inProcessFile,
                            batchFileProcessorEventHook);
                }
            } else {
                logger.debug("Validation failed due to error: {}. Moving file: {} to error directory", validationResult
                        .getErrorCode().name(), inProcessFilePath);
                BatchFileUtil.copyErrorFileToOutputDir(inProcessFile);
                BatchFileUtil.moveToErrorDir(inProcessFile);
            }

        } catch (IOException e) {
            logger.warn("Error while processing file: " + file.getAbsolutePath(), e);
        }
    }
}
