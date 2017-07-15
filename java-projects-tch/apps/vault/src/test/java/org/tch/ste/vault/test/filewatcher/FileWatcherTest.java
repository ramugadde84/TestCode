/**
 * 
 */
package org.tch.ste.vault.test.filewatcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.core.batch.BatchFileWatcherInitiator;
import org.tch.ste.vault.test.common.FileWatcherEventHookImpl;

/**
 * Tests the file watcher utility. Maps to a part of use-case 5.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class FileWatcherTest extends BaseTest {

    private static final String[] directories = new String[] { "C:\\DropZone\\ICICI\\inbound",
            "C:\\DropZone\\SBI\\inbound", "C:\\DropZone\\HDFC\\inbound" };

    private static final String[] outboundDirectories = new String[] { "C:\\DropZone\\ICICI\\outbound",
            "C:\\DropZone\\SBI\\outbound", "C:\\DropZone\\HDFC\\outbound" };

    private static final String[] filesToCreate = new String[] { "abc.txt", "123.txt" };

    private static List<String> filesCreated = new ArrayList<String>();

    private static String[] errorFiles = new String[] { "C:\\DropZone\\ICICI\\error\\abc.txt",
            "C:\\DropZone\\SBI\\error\\abc.txt", "C:\\DropZone\\HDFC\\error\\abc.txt",
            "C:\\DropZone\\ICICI\\error\\123.txt", "C:\\DropZone\\SBI\\error\\123.txt",
            "C:\\DropZone\\HDFC\\error\\123.txt" };

    private static String[] outboundErrorFiles = new String[] { "C:\\DropZone\\ICICI\\outbound\\abc.txt_ERROR",
            "C:\\DropZone\\SBI\\outbound\\abc.txt_ERROR", "C:\\DropZone\\HDFC\\outbound\\abc.txt_ERROR",
            "C:\\DropZone\\ICICI\\outbound\\123.txt_ERROR", "C:\\DropZone\\SBI\\outbound\\123.txt_ERROR",
            "C:\\DropZone\\HDFC\\outbound\\123.txt_ERROR" };

    @Autowired
    private FileWatcherEventHookImpl fileWatcherEventHook;

    @Autowired
    private BatchFileWatcherInitiator batchFileWatcherInitiator;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.WebAppIntegrationTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        fileWatcherEventHook.reset();
        // Create the directories required for testing
        for (String directory : directories) {
            createDirectoryIfNotExists(directory);
        }

        for (String directory : outboundDirectories) {
            createDirectoryIfNotExists(directory);
            // Delete files in the outbound directory, if present.
            for (Object file : FileUtils.listFiles(new File(directory), null, false)) {
                if (file instanceof File) {
                    if (!((File) file).delete()) {
                        throw new RuntimeException("Error while deleting files from directory: " + directory);
                    }
                }
            }
        }
    }

    /**
     * Creates a directory if it does not exist.
     * 
     * @param directory
     *            String.
     */
    private static void createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Cannot create directory: " + directory);
            }
        }
    }

    /**
     * Tests the file watcher.
     * 
     * @throws InterruptedException
     *             - When interrupted.
     * @throws IOException
     *             - When unable to create a file.
     */
    @Test
    public void testFileWatcher() throws InterruptedException, IOException {
        // Create the first file in each of the directories.
        for (String fileToCreate : filesToCreate) {
            for (String directory : directories) {
                File myFile = new File(directory, fileToCreate);
                if (!myFile.createNewFile()) {
                    throw new RuntimeException("Unable to create file: " + fileToCreate);
                }
                filesCreated.add(myFile.getAbsolutePath());
            }
        }
        batchFileWatcherInitiator.startFileProcessing();
        validateFileWatcher();
        validateBatchProcess();
    }

    /**
     * Validates whether the processing succeeded.
     */
    private static void validateBatchProcess() {
        for (String fileCreated : filesCreated) {
            File file = new File(fileCreated);
            assertFalse(file.exists());
        }

        for (String errorFile : errorFiles) {
            File file = new File(errorFile);
            assertTrue(file.exists());
        }
        for (String outboundErrorFile : outboundErrorFiles) {
            File file = new File(outboundErrorFile);
            assertTrue(file.exists());
        }
    }

    /**
     * Validates whether all files created are observed by the file watcher.
     */
    private void validateFileWatcher() {
        System.out.println("Number of files from file watcher: " + fileWatcherEventHook.getNumFilesAdded());
        assertTrue(filesCreated.size() == fileWatcherEventHook.getNumFilesAdded());
        List<String> filesWatched = fileWatcherEventHook.getFilesAdded();
        for (String fileCreated : filesCreated) {
            assertTrue(filesWatched.contains(fileCreated));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        for (String errorFile : errorFiles) {
            File myFile = new File(errorFile);
            if (!myFile.delete()) {
                System.out.println("Unable to delete file:" + errorFile);
            }
        }
        for (String outboundErrorFile : outboundErrorFiles) {
            File file = new File(outboundErrorFile);
            if (!file.delete()) {
                System.out.println("Unable to delete file:" + outboundErrorFile);
            }
        }
        filesCreated.clear();
    }
}
