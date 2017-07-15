/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;

import org.springframework.stereotype.Service;
import org.tch.ste.vault.service.internal.batch.AbstractBatchFileProcessor;
import org.tch.ste.vault.service.internal.batch.BatchFileProcessingResult;

/**
 * Default Implementation.
 * 
 * @author Karthik.
 * 
 */
@Service("defaultBatchFileProcessor")
public class DefaultBatchFileProcessor extends AbstractBatchFileProcessor {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.AbstractBatchFileProcessor#doProcess
     * (java.io.File)
     */
    @Override
    protected BatchFileProcessingResult doProcess(File file) {
        BatchFileProcessingResult retVal = new BatchFileProcessingResult();
        retVal.setSuccess(false);
        return retVal;
    }

}
