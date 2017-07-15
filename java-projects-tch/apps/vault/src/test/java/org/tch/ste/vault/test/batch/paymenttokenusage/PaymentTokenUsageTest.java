/**
 * 
 */
package org.tch.ste.vault.test.batch.paymenttokenusage;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.core.paymenttoken.PaymentTokenUsageInitiator;
import org.tch.ste.vault.test.util.BatchTestHelperUtil;

/**
 * @author anus
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PaymentTokenUsageTest extends BaseTest {

    @Autowired
    private PaymentTokenUsageInitiator paymentTokenUsageInitiator;

    @Autowired
    @Qualifier("hdfcJdbcTemplate")
    private JdbcTemplate hdfcTemplate;

    @Autowired
    @Qualifier("iciciJdbcTemplate")
    private JdbcTemplate iciciTemplate;

    private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound",
            "C:\\DropZone\\ICICI\\outbound" };

    private static final String[] hdfcExpectedRecords = new String[] { "1,1234,,12345678,100,1234,,,true,,123466XXXXXX3456,2015,20",
            "2,1235,,12345679,105,4567,,,true,,123466XXXXXXXXX6333,2014,20" };

    private static final String[] iciciExpectedRecords = new String[] { "1,12314,,21345678,100,1234,,,false,,123466XXXXXX3456,2015,20",
            "2,12315,,21345679,105,4567,,,true,,123466XXXXXXXXX6333,2014,20" };

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        BatchTestHelperUtil.createDirectories(outboundDropZones);
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        createHdfcDetokenizationRequests();
        createIciciDetokenizationRequests();

    }

    /**
     * Creates detokenization requests for ICICI.
     */
    private void createIciciDetokenizationRequests() {
        iciciTemplate.update("INSERT INTO DETOKENIZATION_REQUESTS (TOKEN_ID,CUSTOMER_ID,WAS_SUCCESSFUL,NETWORK_TR_ID)"
                + "VALUES(1,'123456',0,'1234')");
        iciciTemplate.update("INSERT INTO DETOKENIZATION_REQUESTS (TOKEN_ID,CUSTOMER_ID,WAS_SUCCESSFUL,NETWORK_TR_ID)"
                + "VALUES(2,'12345679',1,'4567')");

    }

    /**
     * Creates detokenization requests for Hdfc.
     */
    private void createHdfcDetokenizationRequests() {
        hdfcTemplate.update("INSERT INTO DETOKENIZATION_REQUESTS (TOKEN_ID,CUSTOMER_ID,WAS_SUCCESSFUL,NETWORK_TR_ID)"
                + "VALUES(1,'123456',1,'1234')");

        hdfcTemplate.update("INSERT INTO DETOKENIZATION_REQUESTS (TOKEN_ID,CUSTOMER_ID,WAS_SUCCESSFUL,NETWORK_TR_ID)"
                + "VALUES(2,'12345679',1,'4567')");

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        deleteHdfcDetokenizationRequests();
        deleteIciciDetokenizationRequests();
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
    }

    /**
     * 
     */
    private void deleteIciciDetokenizationRequests() {
        iciciTemplate.update("DELETE FROM DETOKENIZATION_REQUESTS");

    }

    /**
     * 
     */
    private void deleteHdfcDetokenizationRequests() {
        hdfcTemplate.update("DELETE FROM DETOKENIZATION_REQUESTS");
    }

    /**
     * Fetched the Detokenization request.
     * 
     * @throws IOException
     *             Exception Thrown.
     * @throws InterruptedException Thrown.
     */
    @Test
    public void testfetchDetokenizationRequests() throws IOException, InterruptedException {
        paymentTokenUsageInitiator.fetchDetokenizationRequests();
        validateOutboundFile("C:\\DropZone\\HDFC\\outbound", hdfcExpectedRecords);
        validateOutboundFile("C:\\DropZone\\ICICI\\outbound", iciciExpectedRecords);
        BatchTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        createHdfcDetokenizationRequests();
        createIciciDetokenizationRequests();
        Thread.sleep(62000);
        paymentTokenUsageInitiator.fetchDetokenizationRequests();
        validateOutboundFile("C:\\DropZone\\HDFC\\outbound", hdfcExpectedRecords);
        validateOutboundFile("C:\\DropZone\\ICICI\\outbound", iciciExpectedRecords);
    }

    /**
     * @param fileName
     *            String - The file name.
     * @param expectedRecords
     *            String [] - The expected records.
     * @throws IOException
     *             Thrown. Validates the outbound file
     * 
     */
    private static void validateOutboundFile(String fileName, String[] expectedRecords) throws IOException {
        File outboundDir = new File(fileName);
        Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null, false);
        assertTrue(filesInDir.size() == 1);
        File file = (File) filesInDir.toArray()[0];
        List<String> lines = FileUtils.readLines(file);
        for (int i = 1; i < lines.size() - 1; i++) {
            validateContent(lines.get(i), expectedRecords[i - 1]);
        }
    }

    /**
     * @param actualContent
     *            String - Actual Content.
     * @param expectedContent
     *            String - Expected Content. Validates the actual content with
     *            the expected content
     */
    private static void validateContent(String actualContent, String expectedContent) {
        assertTrue(actualContent.startsWith(expectedContent));
    }

}
