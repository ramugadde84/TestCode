/**
 * 
 */
package org.tch.ste.vault.test.batch.inbound;

import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.core.batch.BatchFileWatcherInitiator;
import org.tch.ste.vault.test.util.BatchTestHelperUtil;

/**
 * Tests the inbound logic.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class InboundFileHandlerTest extends BaseTest {

    private static String[] inboundDropZones = new String[] { "C:\\DropZone\\HDFC\\inbound",
            "C:\\DropZone\\ICICI\\inbound" };

    private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound",
            "C:\\DropZone\\ICICI\\outbound" };

    private static String[] errorDropZones = new String[] { "C:\\DropZone\\HDFC\\error", "C:\\DropZone\\ICICI\\error" };

    private static String badIisnFileName = "PYMNTAUTHCREATE_981234_20140614000000";

    private static String badFileTypeFileName = "PYMTAUTHCREATE_123456_20140614000000";

    private static String hdfcFileName = "PYMNTAUTHCREATE_123456_20140614000000";

    private static String iciciFileName = "PYMNTAUTHCREATE_123556_20140614000000";

    private static int HDFC_POS = 0;

    private static int ICICI_POS = 1;

    private static String hdfcBadTimestampFormatFileName = "PYMNTAUTHCREATE_123456_2014";

    private static String iciciBadTimestampFormatFileName = "PYMNTAUTHCREATE_123556_2014";

    @Autowired
    private BatchFileWatcherInitiator batchFileWatcherInitiator;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        BatchTestHelperUtil.createDirectories(inboundDropZones);
        BatchTestHelperUtil.createDirectories(outboundDropZones);
        BatchTestHelperUtil.createDirectories(errorDropZones);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        BatchTestHelperUtil.deleteFilesInDirectories(inboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(errorDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
    }

    /**
     * Tests for an invalid IISN as part of the file name.
     * 
     * @throws IOException
     *             - Thrown when unable to create a file.
     * @throws InterruptedException
     *             - When sleep time is interrupted.
     */
    @Test
    public void testPreloadBadIisn() throws IOException, InterruptedException {
        createFilesForBadIisn();
        batchFileWatcherInitiator.startFileProcessing();
        validateErrorFilesForBadIisn();
    }

    /**
     * Tests for mismatched IISN.
     * 
     * @throws IOException
     *             When unable to create file.
     */
    @Test
    public void testPreloadMismatchedIisn() throws IOException {
        createFilesForMismatchedIisn();
        batchFileWatcherInitiator.startFileProcessing();
        validateErrorFilesForMismatchedIisn();
    }

    /**
     * Tests bad timestamp format.
     * 
     * @throws IOException
     *             When file is not created.
     */
    @Test
    public void testBadTimestampFormat() throws IOException {
        createFilesForBadTimestampFormat();
        batchFileWatcherInitiator.startFileProcessing();
        validateErrorFilesForBadTimestampFormat();
    }

    /**
     * Tests bad file type.
     * 
     * @throws IOException
     *             When file cannot be created.
     */
    @Test
    public void testBadFileType() throws IOException {
        createFilesForBadFileType();
        batchFileWatcherInitiator.startFileProcessing();
        validateErrorFilesForBadFileType();
    }

    /**
     * 
     */
    private static void validateErrorFilesForBadFileType() {
        for (String directory : errorDropZones) {
            File file = new File(directory, badFileTypeFileName);
            assertTrue(file.exists());
        }

        for (String directory : outboundDropZones) {
            File file = new File(directory, badFileTypeFileName + "_ERROR");
            assertTrue(file.exists());
        }

    }

    /**
     * Creates file with bad file type.
     * 
     * @throws IOException
     *             - When file cannot be created.
     */
    private static void createFilesForBadFileType() throws IOException {
        for (String directory : inboundDropZones) {
            File file = new File(directory, badFileTypeFileName);
            if (!file.createNewFile()) {
                throw new RuntimeException("Error in creating file: " + file.getAbsolutePath());
            }
        }

    }

    /**
     * Validates.
     */
    private static void validateErrorFilesForBadTimestampFormat() {
        File hdfcErrorFile = new File(errorDropZones[HDFC_POS], hdfcBadTimestampFormatFileName);
        assertTrue(hdfcErrorFile.exists());
        File iciciErrorFile = new File(errorDropZones[ICICI_POS], iciciBadTimestampFormatFileName);
        assertTrue(iciciErrorFile.exists());

        File hdfcOutboundFile = new File(outboundDropZones[HDFC_POS], hdfcBadTimestampFormatFileName + "_ERROR");
        assertTrue(hdfcOutboundFile.exists());
        File iciciOutboundFile = new File(outboundDropZones[ICICI_POS], iciciBadTimestampFormatFileName + "_ERROR");
        assertTrue(iciciOutboundFile.exists());
    }

    /**
     * Creates the files for bad timestamp format.
     * 
     * @throws IOException
     *             Thrown when file is not created.
     */
    private static void createFilesForBadTimestampFormat() throws IOException {
        File file = new File(inboundDropZones[HDFC_POS], hdfcBadTimestampFormatFileName);
        if (!file.createNewFile()) {
            throw new RuntimeException("Cannot create file: " + file.getAbsolutePath());
        }

        file = new File(inboundDropZones[ICICI_POS], iciciBadTimestampFormatFileName);
        if (!file.createNewFile()) {
            throw new RuntimeException("Cannot create file: " + file.getAbsolutePath());
        }
    }

    /**
     * Validates the error files.
     */
    private static void validateErrorFilesForMismatchedIisn() {
        File hdfcErrorFile = new File(errorDropZones[HDFC_POS], iciciFileName);
        assertTrue(hdfcErrorFile.exists());
        File iciciErrorFile = new File(errorDropZones[ICICI_POS], hdfcFileName);
        assertTrue(iciciErrorFile.exists());

        File hdfcOutboundFile = new File(outboundDropZones[HDFC_POS], iciciFileName + "_ERROR");
        assertTrue(hdfcOutboundFile.exists());
        File iciciOutboundFile = new File(outboundDropZones[ICICI_POS], hdfcFileName + "_ERROR");
        assertTrue(iciciOutboundFile.exists());
    }

    /**
     * Creates files for mismatched IISN.
     * 
     * @throws IOException
     *             When unable to create.
     */
    private static void createFilesForMismatchedIisn() throws IOException {
        File file = new File(inboundDropZones[HDFC_POS], iciciFileName);
        if (!file.createNewFile()) {
            throw new RuntimeException("Unable to create file" + file.getAbsolutePath());
        }
        file = new File(inboundDropZones[ICICI_POS], hdfcFileName);
        if (!file.createNewFile()) {
            throw new RuntimeException("Unable to create file" + file.getAbsolutePath());
        }
    }

    /**
     * Validates whether we have the error files in the right directory.
     */
    private static void validateErrorFilesForBadIisn() {
        for (String dropZone : errorDropZones) {
            File file = new File(dropZone, badIisnFileName);
            assertTrue(file.exists());
        }

        for (String dropZone : outboundDropZones) {
            File file = new File(dropZone, badIisnFileName + "_ERROR");
            assertTrue(file.exists());
        }

    }

    /**
     * Creates the files for bad IISN.
     * 
     * @throws IOException
     *             Thrown when not able to create a file.
     * 
     */
    private static void createFilesForBadIisn() throws IOException {
        for (String dropZone : inboundDropZones) {
            File file = new File(dropZone, badIisnFileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Unable to create new file: " + file.getAbsolutePath());
                }
            }
        }
    }
}
