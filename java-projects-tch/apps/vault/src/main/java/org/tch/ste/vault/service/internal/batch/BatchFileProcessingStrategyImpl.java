/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.tch.ste.vault.constant.VaultConstants;

/**
 * An implementation of the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchFileProcessingStrategyImpl implements BatchFileProcessingStrategy, ApplicationContextAware {

    /**
     * To display all log levels.
     */
    public static final Logger logger = LoggerFactory.getLogger(BatchFileProcessingStrategyImpl.class);

    private ApplicationContext ctx;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.core.batch.BatchFileProcessingStrategy#
     * getBatchFileProcessor(java.io.File)
     */
    @Override
    public BatchFileProcessor getBatchFileProcessor(File file) {
        BatchFileProcessor retVal = null;
        try {
            String fileType = file.getName().split(VaultConstants.FILE_PART_SEPERATOR)[VaultConstants.FILE_TYPE_PART];
            retVal = ctx.getBean(fileType, BatchFileProcessor.class);
        } catch (Throwable t) {
            logger.warn("Message is :" + t);
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
