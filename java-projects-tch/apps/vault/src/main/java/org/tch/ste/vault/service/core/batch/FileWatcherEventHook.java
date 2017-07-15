/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;

/**
 * A hook for someone to watch for events, if need be,
 * 
 * @author Karthik.
 * 
 */
public interface FileWatcherEventHook {
    /**
     * A file has been added.
     * 
     * @param file
     *            File - The file which has been added.
     */
    void fileAdded(File file);

    /**
     * A file has been modified.
     * 
     * @param file
     *            File - The file which has been modified.
     */
    void fileModified(File file);

    /**
     * A file has been deleted.
     * 
     * @param directoryPath
     *            String - The directory path.
     * @param fileName
     *            String - The file name.
     */
    void fileDeleted(String directoryPath, String fileName);
}
