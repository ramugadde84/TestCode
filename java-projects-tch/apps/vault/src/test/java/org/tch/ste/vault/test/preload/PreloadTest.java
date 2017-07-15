/**
 * 
 */
package org.tch.ste.vault.test.preload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.batch.support.FixedLengthReader;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.service.core.batch.BatchFileWatcherInitiator;
import org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument.PaymentInstrumentPreloadRecordResponse;
import org.tch.ste.vault.test.util.BatchTestHelperUtil;

/**
 * Test payment instrument preload.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PreloadTest extends BaseTest {

    /**
     * To display all log entry
     */
    public static final Logger logger = LoggerFactory.getLogger(PreloadTest.class);

    private static String[] inboundDropZones = new String[] { "C:\\DropZone\\HDFC\\inbound",
            "C:\\DropZone\\ICICI\\inbound" };

    private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound",
            "C:\\DropZone\\ICICI\\outbound" };

    private static String[] processedDropZones = new String[] { "C:\\DropZone\\HDFC\\processed",
            "C:\\DropZone\\ICICI\\processed" };

    private static String[] errorDropZones = new String[] { "C:\\DropZone\\HDFC\\error", "C:\\DropZone\\ICICI\\error" };

    private static int HDFC_POS = 0;

    private static int ICICI_POS = 1;

    private static String HDFC_SUCCESS_FILE = "PYMNTAUTHCREATE_123456_20140630120000";

    private static String ICICI_SUCCESS_FILE = "PYMNTAUTHCREATE_123556_20140630120000";

    private static String HDFC_OUTPUT_FILE_STARTING_NAME = "PYMNTAUTHCREATECONF_123456";

    private static String ICICI_OUTPUT_FILE_STARTING_NAME = "PYMNTAUTHCREATECONF_123556";

    private static String HDFC_BAD_FILE_TYPE_FILE = "PYMNTAUTHCREATE_123456_20140730120000";

    private static String ICICI_BAD_FILE_TYPE_FILE = "PYMNTAUTHCREATE_123556_20140730120000";

    private static String HDFC_BAD_IID_FILE = "PYMNTAUTHCREATE_123456_20140830120000";

    private static String ICICI_BAD_IID_FILE = "PYMNTAUTHCREATE_123556_20140830120000";

    private static String HDFC_BAD_RECORD_TYPE_FILE = "PYMNTAUTHCREATE_123456_20140930120000";

    private static String ICICI_BAD_RECORD_TYPE_FILE = "PYMNTAUTHCREATE_123556_20140930120000";

    private static String HDFC_NO_TRAILER_FILE = "PYMNTAUTHCREATE_123456_20140530120000";

    private static String ICICI_NO_TRAILER_FILE = "PYMNTAUTHCREATE_123556_20140530120000";

    private static String HDFC_MANDATORY_VALIDATION_FAILURE_FILE = "PYMNTAUTHCREATE_123456_20140430120000";

    private static String ICICI_MANDATORY_VALIDATION_FAILURE_FILE = "PYMNTAUTHCREATE_123556_20140430120000";

    private static String HDFC_DUPLICATE_FILE = "PYMNTAUTHCREATE_123456_20140330120000";

    private static String ICICI_DUPLICATE_FILE = "PYMNTAUTHCREATE_123556_20140330120000";

    private static String HDFC_TRAILER_MISMATCH_FILE = "PYMNTAUTHCREATE_123456_20130330120000";

    private static String ICICI_TRAILER_MISMATCH_FILE = "PYMNTAUTHCREATE_123556_20130330120000";

    private static String HDFC_UPDATE_ADD_FILE = "PYMNTAUTHCREATE_123456_20150630120000";

    private static String ICICI_UPDATE_ADD_FILE = "PYMNTAUTHCREATE_123556_20150630120000";

    private static String HDFC_UPDATE_UPDATE_FILE = "PYMNTAUTHCREATE_123456_20150730120000";

    private static String ICICI_UPDATE_UPDATE_FILE = "PYMNTAUTHCREATE_123556_20150730120000";

    private static String HDFC_SAME_CUSTOMER_ADD_FILE = "PYMNTAUTHCREATE_123456_20100630120000";

    private static String HDFC_SAME_CUSTOMER_UPDATE_FILE = "PYMNTAUTHCREATE_123456_20100730120000";

    private static String ICICI_SAME_CUSTOMER_ADD_FILE = "PYMNTAUTHCREATE_123556_20100630120000";

    private static String ICICI_SAME_CUSTOMER_UPDATE_FILE = "PYMNTAUTHCREATE_123556_20100730120000";

    private static String HDFC_ALPHABET_HEADER_FILE = "PYMNTAUTHCREATE_123456_20150830120000";

    private static String HDFC_ALPHABET_TRAILER_FILE = "PYMNTAUTHCREATE_123456_20150930120000";

    private static String HDFC_BAD_CONTENT_TYPE_FILE = "PYMNTAUTHCREATE_123456_20151030120000";

    private static String SUCCESS_STRING = BatchResponseCode.SUCCCESS.getResponseStr();

    private static String MISSING_MANDATORY_FIELD_STRING = BatchResponseCode.MISSING_MANDATORY_FIELD.getResponseStr();

    private static String HEADER_TRAILER_ERROR_STR = BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR
            .getResponseStr();

    private static String INPUT_VALUE_TYPE_MISMATCH_STR = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH.getResponseStr();

    private static String DUPLICATE_RECORD_IN_FILE_STR = BatchResponseCode.DUPLICATE_RECORD_IN_FILE.getResponseStr();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcHeaderTrailerFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciHeaderTrailerFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcValidationFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciValidationFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcMismatchedTrailerFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciMismatchedTrailerFailureResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcUpdateResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciUpdateResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcSameCustomerUpdateResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> iciciSameCustomerUpdateResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcAlphaHeaderResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcAlphaTrailerResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    private static List<PaymentInstrumentPreloadRecordResponse> hdfcBadContentTypeResponses = new ArrayList<PaymentInstrumentPreloadRecordResponse>();

    @Autowired
    @Qualifier("paymentInstrumentContentReader")
    private FixedLengthReader<PaymentInstrumentPreloadRecordResponse> responseReader;

    @Autowired
    private BatchFileWatcherInitiator batchFileWatcherInitiator;

    @Autowired
    private EntityManagerInjector emInjector;

    @Autowired
    @Qualifier("hdfcJdbcTemplate")
    private JdbcTemplate hdfcJdbcTemplate;

    @Autowired
    @Qualifier("iciciJdbcTemplate")
    private JdbcTemplate iciciJdbcTemplate;

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
            logger.warn("Message is :" + t);
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
        createHdfcResponses();
        createIciciResponses();
        removeExistingEntityManager();
    }

    /**
     * Creates responses.
     */
    private static void createIciciResponses() {
        iciciResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1755", "0723", "1512345",
                "15111234", "Icici1", SUCCESS_STRING));
        iciciResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725", "1512346",
                "15111235", "Icici2", SUCCESS_STRING));
        iciciHeaderTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1755", "0723",
                "1512345", "15111234", "Icici1", HEADER_TRAILER_ERROR_STR));
        iciciHeaderTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725",
                "1512346", "15111235", "Icici2", HEADER_TRAILER_ERROR_STR));

        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXXA755", "0723",
                "1512345", "15111234", "Icici1", INPUT_VALUE_TYPE_MISMATCH_STR));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", null,
                "1512349", "15111235", "Icici2", MISSING_MANDATORY_FIELD_STRING));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725",
                null, "15111235", "Icici3", MISSING_MANDATORY_FIELD_STRING));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725",
                "1512347", null, "Icici4", MISSING_MANDATORY_FIELD_STRING));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725",
                "1512347", "15111236", "Icici5", SUCCESS_STRING));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752", "0725",
                "1512347", "15111236", "Icici6", DUPLICATE_RECORD_IN_FILE_STR));
        iciciValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1753", "0725",
                "1512347", "15111236", "Icici7", SUCCESS_STRING));

        iciciMismatchedTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1755",
                "0723", "1512345", "15111234", "Icici1", HEADER_TRAILER_ERROR_STR));
        iciciMismatchedTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1752",
                "0725", "1512346", "15111235", "Icici2", HEADER_TRAILER_ERROR_STR));

        iciciUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("153476XXXXXX1755", "0723", "81512345",
                "15111234", "XIcici3", SUCCESS_STRING));
        iciciUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("153476XXXXXX1752", "0725", "181512346",
                "15111235", "XIcici4", SUCCESS_STRING));

        iciciSameCustomerUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1775", "0723",
                "21512345", "15111234", "Icici1", SUCCESS_STRING));
        iciciSameCustomerUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("123476XXXXXX1772", "0725",
                "21512346", "15111235", "Icici2", SUCCESS_STRING));

    }

    /**
     * Creates responses.
     */
    private static void createHdfcResponses() {
        hdfcResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0523", "12345", "5111234",
                "Hdfc1", SUCCESS_STRING));
        hdfcResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525", "12346", "5111235",
                "Hdfc2", SUCCESS_STRING));
        hdfcHeaderTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0523",
                "12345", "5111234", "Hdfc1", HEADER_TRAILER_ERROR_STR));
        hdfcHeaderTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                "12346", "5111235", "Hdfc2", HEADER_TRAILER_ERROR_STR));

        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXXA751", "0523",
                "12345", "5111234", "Hdfc1", INPUT_VALUE_TYPE_MISMATCH_STR));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", null,
                "12349", "5111235", "Hdfc2", MISSING_MANDATORY_FIELD_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                null, "5111235", "Hdfc3", MISSING_MANDATORY_FIELD_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                "12347", null, "Hdfc4", MISSING_MANDATORY_FIELD_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                "12347", "5111236", "Hdfc5", SUCCESS_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                "12347", "5111236", "Hdfc6", DUPLICATE_RECORD_IN_FILE_STR));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0525",
                "12347", "5111236", "Hdfc7", SUCCESS_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse(null, "0525", "12347",
                "5111296", "Hdfc8", MISSING_MANDATORY_FIELD_STRING));
        hdfcValidationFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "1425",
                "12347", "5111239", "Hdfc9", INPUT_VALUE_TYPE_MISMATCH_STR));

        hdfcMismatchedTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751",
                "0523", "12345", "5111234", "Hdfc1", HEADER_TRAILER_ERROR_STR));
        hdfcMismatchedTrailerFailureResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750",
                "0525", "12346", "5111235", "Hdfc2", HEADER_TRAILER_ERROR_STR));

        hdfcUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("153466XXXXXX1751", "0523", "812345",
                "5111234", "XHdfc3", SUCCESS_STRING));
        hdfcUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("153466XXXXXX1750", "0525", "1812346",
                "5111235", "XHdfc4", SUCCESS_STRING));

        hdfcSameCustomerUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1793", "0523",
                "712345", "5111234", "Hdfc1", SUCCESS_STRING));
        hdfcSameCustomerUpdateResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1792", "0525",
                "712346", "5111235", "Hdfc2", SUCCESS_STRING));

        hdfcAlphaHeaderResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0523", "12345",
                "5111234", "Hdfc1", HEADER_TRAILER_ERROR_STR));
        hdfcAlphaHeaderResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525", "12346",
                "5111235", "Hdfc2", HEADER_TRAILER_ERROR_STR));

        hdfcAlphaTrailerResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0523", "12345",
                "5111234", "Hdfc1", HEADER_TRAILER_ERROR_STR));
        hdfcAlphaTrailerResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525", "12346",
                "5111235", "Hdfc2", HEADER_TRAILER_ERROR_STR));
        hdfcAlphaTrailerResponses.add(createPaymentInstrumentPreloadRecordResponse("2", null, null, null, null,
                HEADER_TRAILER_ERROR_STR));

        hdfcBadContentTypeResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1751", "0523",
                "12345", "5111234", "Hdfc1", INPUT_VALUE_TYPE_MISMATCH_STR));
        hdfcBadContentTypeResponses.add(createPaymentInstrumentPreloadRecordResponse("123466XXXXXX1750", "0525",
                "12346", "5111235", "Hdfc2", INPUT_VALUE_TYPE_MISMATCH_STR));
    }

    /**
     * Creates a record.
     * 
     * @param pan
     *            String - The pan.
     * @param expiryDate
     *            String - The expiry date.
     * @param issuerCustomerId
     *            String - The customer id.
     * @param issuerAccountId
     *            String - The account id.
     * @param nickName
     *            String - The nickname.
     * @param responseCode
     *            String - The response code.
     * @return PaymentInstrumentPreloadRecordResponse - The created record.
     */
    private static PaymentInstrumentPreloadRecordResponse createPaymentInstrumentPreloadRecordResponse(String pan,
            String expiryDate, String issuerCustomerId, String issuerAccountId, String nickName, String responseCode) {
        PaymentInstrumentPreloadRecordResponse retVal = new PaymentInstrumentPreloadRecordResponse();
        retVal.setAccountNickName(nickName);
        retVal.setExpiryDate(expiryDate);
        retVal.setPanMasked(pan);
        retVal.setIssuerAccountId(issuerAccountId);
        retVal.setIssuerCustomerId(issuerCustomerId);
        retVal.setResponseCode(responseCode);
        return retVal;
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
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(processedDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(errorDropZones);
        hdfcResponses.clear();
        iciciResponses.clear();
        hdfcHeaderTrailerFailureResponses.clear();
        iciciHeaderTrailerFailureResponses.clear();
        hdfcValidationFailureResponses.clear();
        iciciValidationFailureResponses.clear();
        hdfcMismatchedTrailerFailureResponses.clear();
        iciciMismatchedTrailerFailureResponses.clear();
        hdfcUpdateResponses.clear();
        iciciUpdateResponses.clear();
        hdfcSameCustomerUpdateResponses.clear();
        iciciSameCustomerUpdateResponses.clear();
        hdfcAlphaHeaderResponses.clear();
        hdfcAlphaTrailerResponses.clear();
        hdfcBadContentTypeResponses.clear();
    }

    /**
     * Tests a success scenario.
     * 
     * @throws IOException
     *             - In case unable to move a file.
     */
    @Test
    public void testPreload() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_SUCCESS_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_SUCCESS_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutput();
    }

    /**
     * Tests the scenario when a header contains a wrong file type.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testPreloadFileTypeHeaderFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_FILE_TYPE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_BAD_FILE_TYPE_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_BAD_FILE_TYPE_FILE, ICICI_BAD_FILE_TYPE_FILE);
    }

    /**
     * Tests for an invalid IID.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testPreloadIidFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_IID_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_BAD_IID_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_BAD_IID_FILE, ICICI_BAD_IID_FILE);
    }

    /**
     * Tests for an header record type.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testPreloadHeaderRecordTypeFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_RECORD_TYPE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils
                .copyFileToDirectory(new File(ICICI_BAD_RECORD_TYPE_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_BAD_RECORD_TYPE_FILE, ICICI_BAD_RECORD_TYPE_FILE);
    }

    /**
     * Tests for missing trailer.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testPreloadMissingTrailerFailure() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_NO_TRAILER_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_NO_TRAILER_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateHeaderTrailerFailure(HDFC_NO_TRAILER_FILE, ICICI_NO_TRAILER_FILE);
    }

    /**
     * Tests the mandatory validations.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testMandatoryValidations() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_MANDATORY_VALIDATION_FAILURE_FILE), new File(
                inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_MANDATORY_VALIDATION_FAILURE_FILE), new File(
                inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateValidationFailure(HDFC_MANDATORY_VALIDATION_FAILURE_FILE, ICICI_MANDATORY_VALIDATION_FAILURE_FILE);
    }

    /**
     * Tests for duplicate files.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testDuplicateFiles() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_DUPLICATE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_DUPLICATE_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();

        // No need to validate here.
        // The positive test case has already passed.
        FileUtils.copyFileToDirectory(new File(HDFC_DUPLICATE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_DUPLICATE_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateDuplicateFiles();
    }

    /**
     * Tests for mismatched trailer.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testMismatchedTrailer() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_TRAILER_MISMATCH_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_TRAILER_MISMATCH_FILE), new File(inboundDropZones[ICICI_POS]),
                true);
        batchFileWatcherInitiator.startFileProcessing();
        validateMismatchedTrailer();
    }

    /**
     * Tests an update scenario.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testUpdate() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_UPDATE_ADD_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_UPDATE_ADD_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(processedDropZones);
        // No need to validate the add since that is done in other tests.
        FileUtils.copyFileToDirectory(new File(HDFC_UPDATE_UPDATE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_UPDATE_UPDATE_FILE), new File(inboundDropZones[ICICI_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutputFile(HDFC_POS, HDFC_UPDATE_UPDATE_FILE, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcUpdateResponses,
                null, false);
        validateOutputFile(ICICI_POS, ICICI_UPDATE_UPDATE_FILE, ICICI_OUTPUT_FILE_STARTING_NAME, iciciUpdateResponses,
                null, false);
    }

    /**
     * Tests the scenario when the same customer is in multiple files.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testSameCustomerMultipleFiles() throws IOException {
        FileUtils
                .copyFileToDirectory(new File(HDFC_SAME_CUSTOMER_ADD_FILE), new File(inboundDropZones[HDFC_POS]), true);
        FileUtils.copyFileToDirectory(new File(ICICI_SAME_CUSTOMER_ADD_FILE), new File(inboundDropZones[ICICI_POS]),
                true);
        batchFileWatcherInitiator.startFileProcessing();
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(processedDropZones);
        FileUtils.copyFileToDirectory(new File(HDFC_SAME_CUSTOMER_UPDATE_FILE), new File(inboundDropZones[HDFC_POS]),
                true);
        FileUtils.copyFileToDirectory(new File(ICICI_SAME_CUSTOMER_UPDATE_FILE), new File(inboundDropZones[ICICI_POS]),
                true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutputFile(HDFC_POS, HDFC_SAME_CUSTOMER_UPDATE_FILE, HDFC_OUTPUT_FILE_STARTING_NAME,
                hdfcSameCustomerUpdateResponses, null, false);
        validateOutputFile(ICICI_POS, ICICI_SAME_CUSTOMER_UPDATE_FILE, ICICI_OUTPUT_FILE_STARTING_NAME,
                iciciSameCustomerUpdateResponses, null, false);
    }

    /**
     * Tests for alphabetical header and trailer type.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testAlphabeticalHeaderTrailerType() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_ALPHABET_HEADER_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutputFile(HDFC_POS, HDFC_ALPHABET_HEADER_FILE, HDFC_OUTPUT_FILE_STARTING_NAME,
                hdfcAlphaHeaderResponses, null, false);
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(processedDropZones);
        FileUtils.copyFileToDirectory(new File(HDFC_ALPHABET_TRAILER_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutputFile(HDFC_POS, HDFC_ALPHABET_TRAILER_FILE, HDFC_OUTPUT_FILE_STARTING_NAME,
                hdfcAlphaTrailerResponses, null, false);
    }

    /**
     * Tests for cases when the content type is not 1.
     * 
     * @throws IOException
     *             Thrown.
     */
    @Test
    public void testBadContentType() throws IOException {
        FileUtils.copyFileToDirectory(new File(HDFC_BAD_CONTENT_TYPE_FILE), new File(inboundDropZones[HDFC_POS]), true);
        batchFileWatcherInitiator.startFileProcessing();
        validateOutputFile(HDFC_POS, HDFC_BAD_CONTENT_TYPE_FILE, HDFC_OUTPUT_FILE_STARTING_NAME,
                hdfcBadContentTypeResponses, null, false);
    }

    /**
     * Validates the mismatched trailer.
     * 
     * @throws IOException
     *             Thrown.
     */
    private void validateMismatchedTrailer() throws IOException {
        validateOutputFile(HDFC_POS, HDFC_TRAILER_MISMATCH_FILE, HDFC_OUTPUT_FILE_STARTING_NAME,
                hdfcMismatchedTrailerFailureResponses, null, false);
        validateOutputFile(ICICI_POS, ICICI_TRAILER_MISMATCH_FILE, ICICI_OUTPUT_FILE_STARTING_NAME,
                iciciMismatchedTrailerFailureResponses, null, false);
    }

    /**
     * Validates the duplicates files.
     */
    private static void validateDuplicateFiles() {
        validateFileExists(HDFC_POS, HDFC_DUPLICATE_FILE + "_ERROR", outboundDropZones);
        validateFileExists(ICICI_POS, ICICI_DUPLICATE_FILE + "_ERROR", outboundDropZones);
        validateFileExists(HDFC_POS, HDFC_DUPLICATE_FILE, errorDropZones);
        validateFileExists(ICICI_POS, ICICI_DUPLICATE_FILE, errorDropZones);
    }

    /**
     * Validates the validation itself.
     * 
     * @param file1
     *            String - The first file.
     * @param file2
     *            String - The second file.
     * @throws IOException
     *             Thrown.
     */
    private void validateValidationFailure(String file1, String file2) throws IOException {
        validateOutputFile(HDFC_POS, file1, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcValidationFailureResponses, null, false);
        validateOutputFile(ICICI_POS, file2, ICICI_OUTPUT_FILE_STARTING_NAME, iciciValidationFailureResponses, null,
                false);
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
    private void validateHeaderTrailerFailure(String file1, String file2) throws IOException {
        validateOutputFile(HDFC_POS, file1, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcHeaderTrailerFailureResponses, null,
                false);
        validateOutputFile(ICICI_POS, file2, ICICI_OUTPUT_FILE_STARTING_NAME, iciciHeaderTrailerFailureResponses, null,
                false);
        validateFileExists(HDFC_POS, file1, errorDropZones);
        validateFileExists(ICICI_POS, file2, errorDropZones);
    }

    /**
     * Validates the output files.
     * 
     * @throws IOException
     *             Thrown.
     */
    private void validateOutput() throws IOException {
        validateOutputFile(HDFC_POS, HDFC_SUCCESS_FILE, HDFC_OUTPUT_FILE_STARTING_NAME, hdfcResponses,
                hdfcJdbcTemplate, true);
        validateOutputFile(ICICI_POS, ICICI_SUCCESS_FILE, ICICI_OUTPUT_FILE_STARTING_NAME, iciciResponses,
                iciciJdbcTemplate, true);
        validateFileExists(HDFC_POS, HDFC_SUCCESS_FILE, processedDropZones);
        validateFileExists(ICICI_POS, ICICI_SUCCESS_FILE, processedDropZones);
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

    /**
     * Validates output file.
     * 
     * @param pos
     *            int - The position.
     * @param inputFile
     *            String - The input file.
     * @param startingName
     *            String - The starting name.
     * @param expectedOutput
     *            PaymentInstrumentPreloadRecordResponse- The expected output.
     * @param passwordGenerated
     * @throws IOException
     *             Thrown.
     */
    private void validateOutputFile(int pos, String inputFile, String startingName,
            List<PaymentInstrumentPreloadRecordResponse> expectedOutput, JdbcTemplate jdbcTemplate,
            boolean passwordGenerated) throws IOException {
        File outDir = new File(outboundDropZones[pos]);
        assertTrue(outDir.exists());
        Collection<File> files = FileUtils.listFiles(outDir, null, false);
        assertTrue(files.size() == 1);
        File file = (File) files.toArray()[0];
        String fileName=file.getName();
        assertTrue(fileName.startsWith(startingName));
        assertEquals(fileName.split(VaultConstants.FILE_PART_SEPERATOR)[VaultConstants.TIMESTAMP_PART],
                inputFile.split(VaultConstants.FILE_PART_SEPERATOR)[VaultConstants.TIMESTAMP_PART]);
        List<String> lines = FileUtils.readLines(file);
        int expectedOutputSize = expectedOutput.size();
        assertEquals(lines.size(), expectedOutputSize + 2);
        for (int i = 1; i <= expectedOutputSize; ++i) {
            validateContent(lines.get(i), expectedOutput.get(i - 1), jdbcTemplate, passwordGenerated);
        }
        BatchFileTrailer trailerRecord = trailerReader.readLine(lines.get(lines.size() - 1), true);
        assertTrue(Integer.parseInt(trailerRecord.getNumRecords()) == expectedOutputSize);
        assertTrue(trailerRecord.getType() == BatchFileRecordType.TRAILER.ordinal());
        assertTrue(trailerRecord.getErrorCode() != null);
    }

    /**
     * Validates the content.
     * 
     * @param line
     *            String - The line.
     * @param expectedOutput
     *            PaymentInstrumentPreloadRecordResponse - The expected output.
     * @param jdbcTemplate
     *            JdbcTemplate - The template used to validate.
     * @param passwordGenerated
     *            boolean - Whether the password is generated in this scenario.
     */
    private void validateContent(String line, PaymentInstrumentPreloadRecordResponse expectedOutput,
            JdbcTemplate jdbcTemplate, boolean passwordGenerated) {
        PaymentInstrumentPreloadRecordResponse response = responseReader.readLine(line, true);
        if (jdbcTemplate != null) {
            String arn = response.getArn();
            assertTrue(arn != null);
            assertTrue(StringUtil.stripLeadingZeros(arn).length() == 23);
            String userName = response.getUserName();
            assertTrue(userName != null);
            if (userName != null) {
                assertTrue(userName.length() == 8);
            }
            if (passwordGenerated) {
                String password = response.getPassword();
                assertTrue(password != null);
                if (password != null) {
                    assertTrue(password.length() == InfraConstants.MAX_PASSWORD_LENGTH);
                }
            }
            // Test against the DB to validate that the specific thing has been
            // added to the tables.
            Integer arnId = jdbcTemplate.queryForObject("SELECT ID from ARNS where ARN=?", Integer.class,
                    response.getArn());
            assertTrue(jdbcTemplate.queryForObject("SELECT COUNT(*) from PAYMENT_INSTRUMENTS where ARN_ID=?",
                    Integer.class, arnId) == 1);
            assertFalse(jdbcTemplate.queryForObject("SELECT IS_ACTIVE from PAYMENT_INSTRUMENTS where ARN_ID=?",
                    Boolean.class, arnId));
            assertTrue(jdbcTemplate.queryForObject("SELECT COUNT(*) from CUSTOMERS where ISSUER_CUSTOMER_ID=?",
                    Integer.class, response.getIssuerCustomerId()) == 1);

        }
        assertEquals(response.getAccountNickName(), expectedOutput.getAccountNickName());
        assertEquals(response.getPanMasked(), expectedOutput.getPanMasked());
        assertEquals(response.getExpiryDate(), expectedOutput.getExpiryDate());
        assertEquals(response.getIssuerAccountId(), expectedOutput.getIssuerAccountId());
        assertEquals(response.getIssuerCustomerId(), expectedOutput.getIssuerCustomerId());
        assertEquals(response.getResponseCode(), expectedOutput.getResponseCode());
    }
}
