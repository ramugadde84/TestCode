/**
 * 
 */
package org.tch.ste.vault.test.batch.password;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.service.core.batch.PasswordExpiryInitiator;

/**
 * Tests the password expiry logic.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PasswordExpirationTest extends BaseTest {

    private static final int HEADER_LINE = 0;

    private static final int TRAILER_LINE = 3;

    private static final int EXPECTED_NUM_RECORDS = 2;

    private static final String testHeader = "Record Count, Issuer Customer Id, User Name, Password, Error Code";

    private static final String[] expectedRecords = new String[] { "1,1234,12345678,", "2,1235,12345679," };

    @Autowired
    private PasswordExpiryInitiator passwordExpiryService;

    @Autowired
    @Qualifier("hdfcJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    /**
     * Tests the password expiration logic.
     * 
     * @throws IOException
     *             Exception Thrown.
     */
    @Test
    public void testPasswordExpiry() throws IOException {

        Date lastPasswordChangeDate = DateUtil.add(DateUtil.getUtcTime(), VaultConstants.PASSWORD_EXPIRY_TIME - 1);
        jdbcTemplate.update(
                "UPDATE CUSTOMERS SET LAST_PASSWORD_CHANGE_DATETIME=?,HASHED_PASSWORD=NULL,PASSWORD_SALT=NULL",
                lastPasswordChangeDate);
        // Check if the date has been updated successfully.
        Integer rowCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CUSTOMERS where LAST_PASSWORD_CHANGE_DATETIME=? ", Integer.class,
                lastPasswordChangeDate);

        assertTrue(rowCount == 2);
        Integer passwordCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) from CUSTOMERS where HASHED_PASSWORD IS NOT NULL AND PASSWORD_SALT IS NOT NULL",
                Integer.class);
        assertTrue(passwordCount == 0);
        passwordExpiryService.expirePasswords();
        Integer newRowCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CUSTOMERS where LAST_PASSWORD_CHANGE_DATETIME=? ", Integer.class,
                lastPasswordChangeDate);
        assertTrue(newRowCount == 0);

        Integer updatedPasswordCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) from CUSTOMERS where HASHED_PASSWORD IS NOT NULL AND PASSWORD_SALT IS NOT NULL",
                Integer.class);
        assertTrue(updatedPasswordCount == 2);

        validateOutboundFile();
    }

    /**
     * Validates the outbound directory for the given file.
     * 
     * @throws IOException
     *             Exception - Thrown.
     */
    private static void validateOutboundFile() throws IOException {
        File outboundDir = new File("C:\\DropZone\\HDFC\\outbound");

        Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null, false);
        assertTrue(filesInDir.size() == 1);
        for (File file : filesInDir) {
            List<String> lines = FileUtils.readLines(file);

            String headerLine = lines.get(HEADER_LINE);
            validateHeader(headerLine);

            for (int i = 1; i < lines.size() - 1; i++) {
                validateContent(lines.get(i), expectedRecords[i - 1]);
            }

            String trailerLine = lines.get(TRAILER_LINE);
            validateTrailer(trailerLine);
        }
    }

    /**
     * @param actualContent
     *            String - Actual Content.
     * @param expectedContent
     *            String - Expected Content.
     */
    private static void validateContent(String actualContent, String expectedContent) {
        assertTrue(actualContent.startsWith(expectedContent));
        String values[] = actualContent.split(",");
        String password = values[3];
        assertTrue(password != null && password.length() == 12);
    }

    /**
     * Validates the trailer record.
     * 
     * @param trailerLine
     *            String - The trailer line.
     */
    private static void validateTrailer(String trailerLine) {
        assertEquals(trailerLine, String.valueOf(EXPECTED_NUM_RECORDS));
    }

    /**
     * Validates the header.
     * 
     * @param headerLine
     *            String - The header.
     */
    private static void validateHeader(String headerLine) {
        assertEquals(testHeader, headerLine);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        File directory = new File("C:\\DropZone\\HDFC\\outbound");
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Unable to create the outbound directory");
        }

        for (Object file : FileUtils.listFiles(directory, null, false)) {
            if (file instanceof File) {
                File myFile = (File) file;
                if (!myFile.delete()) {
                    throw new RuntimeException("Unable to delete file: " + myFile.getAbsolutePath());
                }
            }
        }
    }

}
