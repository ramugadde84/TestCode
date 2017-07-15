/**
 * 
 */
package org.tch.ste.vault.test.lockunlockcustomeraccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.batch.support.FixedLengthReader;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.service.core.batch.BatchFileWatcherInitiator;
import org.tch.ste.vault.service.internal.batch.customer.LockUnlockCustomerAccountResponse;
import org.tch.ste.vault.test.util.BatchTestHelperUtil;

/**
 * Lock and Unlock customer account.
 * 
 * @author ramug
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LockUnlockCustomeAccountTest extends BaseTest {

    private static Logger logger = LoggerFactory.getLogger(LockUnlockCustomeAccountTest.class);
    private static String[] inboundDropZones = new String[] { "C:\\DropZone\\HDFC\\inbound",
            "C:\\DropZone\\ICICI\\inbound" };

    private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound",
            "C:\\DropZone\\ICICI\\outbound" };

    private static String[] processedDropZones = new String[] { "C:\\DropZone\\HDFC\\processed",
            "C:\\DropZone\\ICICI\\processed" };

    private static String[] errorDropZones = new String[] { "C:\\DropZone\\HDFC\\error", "C:\\DropZone\\ICICI\\error" };

    private static int HDFC_POS = 0;

    // private static int ICICI_POS = 1;

    private static String HDFC_SUCCESS_FILE = "SETACCOUNTLOCK_123456_20130430120000";

    private static String HDFC_OUTPUT_FILE_STARTING_NAME = "SETACCOUNTLOCKCONF_123456";

    private static String HDFC_BAD_RECORD_TYPE_FILE = "SETACCOUNTLOCK_123456_20140930120000";

    private static String HDFC_BAD_TRAILER_FILE = "SETACCOUNTLOCK_123456_20141030120000";

    private static String HDFC_MISSING_MANDATORY_FIELD = "SETACCOUNTLOCK_123456_20141130120000";

    private static String HDFC_CUSTOMER_ID_MISSING = "SETACCOUNTLOCK_123456_20141230120000";

    private static List<LockUnlockCustomerAccountResponse> hdfcResponses = new ArrayList<LockUnlockCustomerAccountResponse>();

    private static List<LockUnlockCustomerAccountResponse> hdfcHeaderTrailerFailureResponses = new ArrayList<LockUnlockCustomerAccountResponse>();

    private static List<LockUnlockCustomerAccountResponse> hdfcMissingMandatoryFields = new ArrayList<LockUnlockCustomerAccountResponse>();

    private static List<LockUnlockCustomerAccountResponse> hdfcInvalidIssuerCustomerId = new ArrayList<LockUnlockCustomerAccountResponse>();

    private static String SUCCESS_STRING = BatchResponseCode.SUCCCESS.getResponseStr();

    private static String MISSING_MANDATORY_FIELD_STRING = BatchResponseCode.MISSING_MANDATORY_FIELD.getResponseStr();

    private static String CUSTOMER_ID_NOT_AVAILABLE = BatchResponseCode.USER_ACCOUNT_NOT_FOUND.getResponseStr();

    private static String HEADER_TRAILER_ERROR_STR = BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR
            .getResponseStr();

    @Autowired
    private BatchFileWatcherInitiator batchFileWatcherInitiator;

    @Autowired
    private EntityManagerInjector emInjector;

    @Autowired
    @Qualifier("hdfcJdbcTemplate")
    private JdbcTemplate hdfcJdbcTemplate;

    @Autowired
    @Qualifier("trailerReader")
    private FixedLengthReader<BatchFileTrailer> trailerReader;

    /**
     * Removes the existing em.
     */
    public void removeExistingEntityManager() {
        try {
            emInjector.remove("123456");
        } catch (Throwable t) {
            logger.warn("Error in entity manager", t);
        }
    }

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
        BatchTestHelperUtil.createDirectories(processedDropZones);
        BatchTestHelperUtil.createDirectories(errorDropZones);
        removeExistingEntityManager();
        createHdfcResponse();
    }

    /**
     * The Batch File which is coming as valid user-name and account locked.
     * 
     * @throws IOException
     *             - In case unable to move a file.
     */
    @Test
    public void testLockCustomerFromBatchFileTest() throws IOException {
        // From Batch File the input is Locked (L-1) for that the Reason Code
        // is(5).
        FileUtils.copyFileToDirectory(new File(HDFC_SUCCESS_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        String accountLocked = hdfcJdbcTemplate.queryForObject("SELECT ACCOUNT_LOCKED from CUSTOMERS where USERNAME=?",
                String.class, "12345678");
        Assert.assertEquals("1", accountLocked);
        String reasonCode = hdfcJdbcTemplate.queryForObject(
                "select ACCOUNT_LOCKED_REASON_CODE from CUSTOMERS where USERNAME=?", String.class, "12345678");
        Assert.assertEquals("5", reasonCode);
        String accountUnLocked = hdfcJdbcTemplate.queryForObject(
                "SELECT ACCOUNT_LOCKED from CUSTOMERS where USERNAME=?", String.class, "12345679");
        Assert.assertEquals("0", accountUnLocked);
        reasonCode = hdfcJdbcTemplate.queryForObject(
                "select ACCOUNT_LOCKED_REASON_CODE from CUSTOMERS where USERNAME=?", String.class, "12345679");
        validateOutput();
    }

    /**
     * Tests for an header record type.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testLockUnlockHeaderFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_RECORD_TYPE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_BAD_RECORD_TYPE_FILE);
    }

    /**
     * Tests for an header record type.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testLockUnlockTrailerFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_TRAILER_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_BAD_TRAILER_FILE);
    }

    /**
     * Test for Missing mandatory fields.
     * 
     * @throws IOException
     *             - throws IO-Excepiton.
     */
    @Test
    public void testMissingMandatoryFields() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_MISSING_MANDATORY_FIELD), new File(inboundDropZones[HDFC_POS]),
                true);
        batchFileWatcherInitiator.startFileProcessing();
        validateMissingMandatoryFields(HDFC_MISSING_MANDATORY_FIELD);
    }

    /**
     * Test for Invalid customer id.
     * 
     * @throws IOException
     *             - throws Exception.
     */
    @Test
    public void testInvalidCustomerId() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_CUSTOMER_ID_MISSING), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();

        validateInvalidCustomeId(HDFC_CUSTOMER_ID_MISSING);
    }

    /**
     * @param file1
     * @throws IOException
     */
    private void validateInvalidCustomeId(String file1) throws IOException {
        validateOutputFile(HDFC_POS, file1, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcInvalidIssuerCustomerId,
                hdfcJdbcTemplate);
    }

    /**
     * @param file1
     * @throws IOException
     */
    private void validateMissingMandatoryFields(String file1) throws IOException {
        validateOutputFile(HDFC_POS, file1, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcMissingMandatoryFields,
                hdfcJdbcTemplate);
    }

    /**
     * To Clear the drop zone folder after running the batch.
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        BatchTestHelperUtil.deleteFilesInDirectories(inboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(processedDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(errorDropZones);
        hdfcResponses.clear();
        hdfcHeaderTrailerFailureResponses.clear();
        hdfcMissingMandatoryFields.clear();
        hdfcInvalidIssuerCustomerId.clear();
    }

    private void validateOutput() throws IOException {
        validateOutputFile(HDFC_POS, HDFC_SUCCESS_FILE, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcResponses, hdfcJdbcTemplate);
    }

    /**
     * Validate output file.
     * 
     * @param pos
     * @param inputFile
     * @param startingName
     * @param expectedOutput
     * @param jdbcTemplate
     * @throws IOException
     */
    private static void validateOutputFile(int pos, String inputFile, String startingName,
            List<LockUnlockCustomerAccountResponse> expectedOutput, JdbcTemplate jdbcTemplate) throws IOException {
        File outDir = new File(outboundDropZones[pos]);
        assertTrue(outDir.exists());
        Collection<File> files = FileUtils.listFiles(outDir, null, false);
        assertTrue(files.size() == 1);
        File file = (File) files.toArray()[0];
        assertTrue(file.getName().startsWith(startingName));
        List<String> lines = FileUtils.readLines(file);
        int expectedOutputSize = expectedOutput.size();
        assertEquals(lines.size(), expectedOutputSize + 2);
    }

    private static void createHdfcResponse() {
        // Success responses.
        hdfcResponses.add(createLockUnlockCustomerAccountResponse("1234", "12345678", "L", SUCCESS_STRING));
        hdfcResponses.add(createLockUnlockCustomerAccountResponse("1235", "12345679", "U", SUCCESS_STRING));

        // Header or Trailer error responses.

        hdfcHeaderTrailerFailureResponses.add(createLockUnlockCustomerAccountResponse("1234", "12345678", "U",
                HEADER_TRAILER_ERROR_STR));

        hdfcMissingMandatoryFields.add(createLockUnlockCustomerAccountResponse(null, "12345678", "U",
                MISSING_MANDATORY_FIELD_STRING));

        hdfcInvalidIssuerCustomerId.add(createLockUnlockCustomerAccountResponse("1234", "12345688", "U",
                CUSTOMER_ID_NOT_AVAILABLE));
    }

    /**
     * @param issuerCustomerId
     *            - issuer customer id.
     * @param userName
     *            -user name.
     * @param lockState
     *            -lock state.
     * @param responseCode
     *            -response code.
     */
    private static LockUnlockCustomerAccountResponse createLockUnlockCustomerAccountResponse(String issuerCustomerId,
            String userName, String lockState, String responseCode) {
        LockUnlockCustomerAccountResponse response = new LockUnlockCustomerAccountResponse();
        response.setIssuerCustomerId(issuerCustomerId);
        response.setLockState(lockState);
        response.setResponseCode(responseCode);
        response.setUserName(userName);
        return response;
    }

    /**
     * Validates header failure.
     * 
     * @param file2
     *            String - The file2.
     * @param file1
     *            String - The file1.
     * 
     * @throws IOException
     *             Thrown.
     */
    private void validateHeaderTrailerFailure(String file1) throws IOException {
        validateOutputFile(HDFC_POS, file1, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcMissingMandatoryFields,
                hdfcJdbcTemplate);

        validateFileExists(HDFC_POS, file1, errorDropZones);

    }

    /**
     * Validates the processed files.
     * 
     * @param pos
     *            int - The position.
     * @param inputFile
     *            String - The input file.
     * @param dropZones
     *            String - The drop zones.
     */
    private static void validateFileExists(int pos, String inputFile, String[] dropZones) {
        File processedFile = new File(dropZones[pos], inputFile);
        assertTrue(processedFile.exists());
    }
}
