/**
 * 
 */
package org.tch.ste.test.auth.lockedoutuser;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.tch.ste.auth.service.core.batch.LockedOutUsersReportGenerationBatchWatchInitiator;
import org.tch.ste.auth.test.util.LockedOutUsersReportGenerationTestUtil;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * It is used to test locked out users.
 * 
 * @author ramug
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class LockedOutUsersReportGenerationTest extends WebAppIntegrationTest {

	@Autowired
	private LockedOutUsersReportGenerationBatchWatchInitiator batchWatchInitiator;

	private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound" };

	protected String LOGIN_URL = "/login";

	protected String LOGIN_ID = "userId";

	@Autowired
	@Qualifier("hdfcJdbcTemplate")
	private JdbcTemplate hdfcJdbcTemplate;

	protected String PASSWORD = "password";

	protected String IISN = "iisn";

	protected String TR_ID = "trId";

	protected String iisn = "123456";

	protected String trId = "123456";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
	 */
	@Override
	public void doOtherSetup() {
		super.doOtherSetup();
		LockedOutUsersReportGenerationTestUtil
				.createDirectories(outboundDropZones);
		LockedOutUsersReportGenerationTestUtil
				.deleteFilesInDirectories(outboundDropZones);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
	 */
	@Override
	public void doOtherTeardown() {
		super.doOtherSetup();
		LockedOutUsersReportGenerationTestUtil
				.deleteFilesInDirectories(outboundDropZones);
		hdfcJdbcTemplate
				.update("DELETE FROM REPORT_HISTORY WHERE REPORT_TYPE='LOCKOUT'");
		hdfcJdbcTemplate.update("TRUNCATE LOGIN_HISTORY");

	}

	/**
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testTimeOnGenerationBatch() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();
		Thread.sleep(62000);
		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		String userName1 = validateOutboundFileTestRecordUserName(outboundDropZones[0]);
		Assert.assertEquals(userName1, "1234qwe1");
		LockedOutUsersReportGenerationTestUtil
				.deleteFilesInDirectories(outboundDropZones);
		
		userName = "1234qwe2";
		password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();
		Thread.sleep(62000);
		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		userName1 = validateOutboundFileTestRecordUserName(outboundDropZones[0]);
		Assert.assertEquals(userName1, "1234qwe2");
	}

	/**
	 * Test manual lock change.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testManualLockChange() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		String sql = "UPDATE CUSTOMERS SET ACCOUNT_LOCKED=0 WHERE ACCOUNT_LOCKED=1 AND USERNAME=?";
		hdfcJdbcTemplate.update(sql, new Object[] { userName });
		Thread.sleep(62000);
		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		userName = validateOutboundFileTestRecordUserName(outboundDropZones[0]);
		Assert.assertEquals(userName,"1234qwe1");

	}

	/**
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testCheckOfLockedValueChangeByCustomersLockUnlockBatch()
			throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();
		userName = "1234qwe2";

		String sql = "UPDATE CUSTOMERS SET ACCOUNT_LOCKED=1,ACCOUNT_LOCKED_AT=NOW() WHERE USERNAME=?";

		hdfcJdbcTemplate.update(sql, new Object[] { userName });
		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String validateOutboundFileTestRecordUserName(String fileName)
			throws IOException {
		String mainData = null;
		File outboundDir = new File(fileName);
		Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null,
				false);

		for (File f : filesInDir) {
			InputStreamReader inputStreamReader = new InputStreamReader(
					new FileInputStream(f), Charset.defaultCharset());
			try (BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader)) {
				List<String> recordsStores = new ArrayList<String>();
				String record = null;
				while ((record = bufferedReader.readLine()) != null) {
					recordsStores.add(record);
				}
				String str[] = new String[recordsStores.size()];
				recordsStores.toArray(str);
				String[] str1 = null;
				for (int i = 1; i < str.length - 1; i++) {
					str1 = str[i].split(",");
					for (i = 2; i <= 2; i++) {
						mainData = str1[i];
					}

				}
			}
		}

		return mainData;

	}

	/**
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testLockedOutReportGenerationBatch() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		validateOutboundFile(outboundDropZones[0]);
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
	private static void validateOutboundFile(String fileName)
			throws IOException {
		File outboundDir = new File(fileName);
		Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null,
				false);
		assertTrue(filesInDir.size() == 1);

	}

	/**
	 * Testing Header Record.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testHeaderRecord() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		validateOutboundTestFile(outboundDropZones[0]);
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
	private static void validateOutboundTestFile(String fileName)
			throws IOException {
		File outboundDir = new File(fileName);
		Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null,
				false);
		for (File f : filesInDir) {
			InputStreamReader inputStreamReader = new InputStreamReader(
					new FileInputStream(f), Charset.defaultCharset());
			try (BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader)) {
				String headerString;
				if ((headerString = bufferedReader.readLine()) != null) {
					String[] splittedValues = headerString.split(",");
					List<String> actualValues = Arrays.asList(splittedValues);
					List<String> expectedValues = new ArrayList<String>();
					expectedValues.add("Record Number");
					expectedValues.add("Issuer Customer ID");
					expectedValues.add("Vault Username");
					expectedValues.add("Current State");
					expectedValues.add("Lockout Reason");
					expectedValues.add("Lockout timestamp");
					Assert.assertEquals(actualValues.get(0),
							expectedValues.get(0));
					Assert.assertEquals(actualValues.get(1).trim(),
							expectedValues.get(1).trim());
					Assert.assertEquals(actualValues.get(2).trim(),
							expectedValues.get(2).trim());
					Assert.assertEquals(actualValues.get(3).trim(),
							expectedValues.get(3).trim());
					Assert.assertEquals(actualValues.get(4).trim(),
							expectedValues.get(4).trim());
					Assert.assertEquals(actualValues.get(5).trim(),
							expectedValues.get(5).trim());
				}
			}
		}
	}

	/**
	 * Testing Header Record.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testTrailerRecord() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		validateOutboundTrailerTestFile(outboundDropZones[0]);

	}

	/**
	 * @param fileName
	 *            String - The file name.
	 * @param expectedRecords
	 *            String [] - The expected records.
	 * @return
	 * @throws IOException
	 *             Thrown. Validates the outbound file
	 * 
	 */
	private static String validateOutboundTrailerTestFile(String fileName)
			throws IOException {
		File outboundDir = new File(fileName);
		String trailerLine = null;
		Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null,
				false);
		for (File f : filesInDir) {
			try (ReversedLinesFileReader reversedLinesFileReader = new ReversedLinesFileReader(
					f)) {
				trailerLine = reversedLinesFileReader.readLine();
				String[] trailerLineSplit = trailerLine.split(",");
				List<String> trailerLineSplits = Arrays
						.asList(trailerLineSplit);
				Assert.assertEquals("Number of Records",
						trailerLineSplits.get(0));
			}
		}
		return trailerLine;

	}

	/**
	 * It tests Number of Records in a file.
	 * 
	 * @throws Exception
	 *             Thrown.
	 */
	@Test
	public void testNumberOfRecords() throws Exception {
		String userName = "1234qwe1";
		String password = "password1235";

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		this.mockMvc
				.perform(
						post(LOGIN_URL).accept(MediaType.APPLICATION_JSON)
								.param(LOGIN_ID, userName)
								.param(PASSWORD, password).param(IISN, iisn)
								.param(TR_ID, trId))
				.andExpect(okStatus)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.success",
								Boolean.TRUE).exists()).andReturn();

		batchWatchInitiator.lockedOutUsersReportGenerationWatchInitiator();
		validateTestNumberOfRecords(outboundDropZones[0]);

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
	private static void validateTestNumberOfRecords(String fileName)
			throws IOException {
		String trailerRecords = validateOutboundTrailerTestFile(fileName);
		File outboundDir = new File(fileName);
		Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null,
				false);
		for (File f : filesInDir) {
			InputStreamReader inputStreamReader = new InputStreamReader(
					new FileInputStream(f), Charset.defaultCharset());
			try (BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader)) {
				int temp = 0;
				String inputReader = null;
				while ((inputReader = bufferedReader.readLine()) != null) {
					Assert.assertNotNull(inputReader);
					temp++;
				}
				int count = temp - 2;
				String[] trailerLineSplit = trailerRecords.split(",");
				List<String> trailerLineSplits = Arrays
						.asList(trailerLineSplit);
				Assert.assertEquals("Number of Records",
						trailerLineSplits.get(0));
				Assert.assertEquals(String.valueOf(count),
						trailerLineSplits.get(1));
			}

		}

	}

}
