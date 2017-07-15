/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.internal.configuration.ConfigurationPropertiesService;
import org.tch.ste.vault.service.internal.issuer.IssuerRetrievalService;

/**
 * Initiates the file watcher process.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileWatcherInitiator {

    private static Logger logger = LoggerFactory.getLogger(BatchFileWatcherInitiator.class);

    @Autowired
    private IssuerRetrievalService issuerRetrievalService;

    @Autowired
    private ConfigurationPropertiesService configurationPropertiesService;

    @Autowired(required = false)
    private FileWatcherEventHook fileWatcherEventHook;

    @Autowired
    private BatchInboundFileHandler batchInboundFileHandler;

    /**
     * Starts the file watcher.
     * 
     * Note: The following caveats apply. - This would not monitor freshly added
     * directories unless the server is restarted. - This is meant to be run
     * within a single server and will not work in a clustered environment. - If
     * this needs to be run within a clustered environment, Quartz needs to be
     * introduced.
     */
    public void startFileProcessing() {
        List<IssuerConfiguration> issuers = issuerRetrievalService.getAll();
        List<String> directories = getInboundDirectoriesToWatch(issuers);
        // Process all the existing files in the directories.
        processExistingFiles(directories);
    }

    /**
     * Process a set of files.
     * 
     * @param files
     *            List<Files> - The files.
     */
    private void processFiles(List<File> files) {
        for (File file : files) {
        	//Used for unit testing.
            if (fileWatcherEventHook != null) {
                fileWatcherEventHook.fileAdded(file);
            }
            logger.debug("File with name: {} has been  added ", file.getName());
            batchInboundFileHandler.handle(file);
        }
    }

    /**
     * Process all the existing files first.
     * 
     * @param directories
     *            List<String> - The directories to process the files from.
     */
    private void processExistingFiles(List<String> directories) {
        List<File> existingFiles = new ArrayList<File>();
        for (String directory : directories) {
            try {
                existingFiles.addAll(FileUtils.listFiles(new File(directory), null, false));
            } catch (Throwable t) {
                logger.warn("Unable to list the files in the directory: " + directory, t);
            }
        }

        processFiles(existingFiles);
    }

    /**
     * Returns the directories of the issuers.
     * 
     * @param issuers
     *            List<IssuerConfiguration> - The issuers.
     * @return List<String> - T3whe directories.
     */
    private List<String> getInboundDirectoriesToWatch(List<IssuerConfiguration> issuers) {
        List<String> retVal = new ArrayList<String>();
        String batchRootPath = configurationPropertiesService.getBatchRoot();
        Map<String, Boolean> directories = new HashMap<String, Boolean>();
        for (IssuerConfiguration issuer : issuers) {
            String issuerDropzonePath = issuer.getDropzonePath();
            if (!directories.containsKey(issuerDropzonePath)) {
                directories.put(issuerDropzonePath, Boolean.TRUE);
                File inboundDirectory = new File(FilenameUtils.concat(batchRootPath, issuerDropzonePath),
                        VaultConstants.INBOUND_DIR);
                retVal.add(inboundDirectory.getAbsolutePath());
            }
        }
        return retVal;
    }
}
