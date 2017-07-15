/**
 * 
 */
package org.tch.ste.vault.test.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.tch.ste.vault.service.core.batch.FileWatcherEventHook;

/**
 * An implementation of the interface which is used for testing.
 * 
 * @author Karthik.
 * 
 */
@Service
public class FileWatcherEventHookImpl implements FileWatcherEventHook {

    private int numFilesAdded = 0;

    private List<String> filesAdded = new ArrayList<String>();

    /**
     * Reset the number of files.
     */
    public synchronized void reset() {
        numFilesAdded = 0;
        filesAdded.clear();
    }

    /**
     * @return numFilesAdded int - Get the field.
     */
    public synchronized int getNumFilesAdded() {
        return numFilesAdded;
    }

    /**
     * @return filesAdded List<String> - Get the field.
     */
    public synchronized List<String> getFilesAdded() {
        return filesAdded;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.batch.FileWatcherEventHook#fileAdded(java
     * .io.File)
     */
    @Override
    public synchronized void fileAdded(File file) {
        numFilesAdded++;
        filesAdded.add(file.getAbsolutePath());
        this.notifyAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.batch.FileWatcherEventHook#fileModified
     * (java.io.File)
     */
    @Override
    public synchronized void fileModified(File file) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.batch.FileWatcherEventHook#fileDeleted
     * (java.lang.String, java.lang.String)
     */
    @Override
    public synchronized void fileDeleted(String directoryPath, String fileName) {
        // TODO Auto-generated method stub

    }

}
