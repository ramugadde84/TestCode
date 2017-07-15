/**
 * 
 */
package org.tch.ste.test.auth.authattempts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.tch.ste.auth.service.core.authattempts.AuthenticationAttemptsInitiator;
import org.tch.ste.auth.test.util.AuthTestHelperUtil;
import org.tch.ste.domain.entity.LoginHistory;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.test.base.WebAppIntegrationTest;

/**
 * @author anus
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class AuthenticationAttemptsTest extends WebAppIntegrationTest {

    @Autowired
    private AuthenticationAttemptsInitiator authenticationAttemptsInitiator;
    
    @Autowired
    @Qualifier("loginHistoryDao")
    private JpaDao<LoginHistory,Integer> loginHistoryDao;
    
    @Autowired
    private EntityManagerInjector emInjector;

    private String LOGIN_URL = "/login";

    private String LOGIN_ID = "userId";

    private String PASSWORD = "password";

    protected String IISN = "iisn";

    protected String TR_ID = "trId";

    protected String iisn = "123456";

    protected String trId = "123456";

    private static String[] outboundDropZones = new String[] { "C:\\DropZone\\HDFC\\outbound" };

    private static String authHeader = "Record Number, Issuer Customer ID, Vault Username, Authentication Attempt TimeStamp , Client IP Address, Client User Agent, Authentication Result";

    private static String authTrailer = "Number of Records";

    private static final String[] authExpectedRecordsFirst = new String[] { "1,1234561,1234qwe1,20",
            "2,1234561,1234qwe1,20", "3,1234562,1234qwe2,20" };

    private static final String[] authExpectedRecordsSecond = new String[] { "1,1234561,1234qwe1,20",
            "2,1234561,1234qwe1,20", "3,1234562,1234qwe2,20" };

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        AuthTestHelperUtil.createDirectories(outboundDropZones);
        AuthTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        emInjector.inject(iisn);
        for (LoginHistory loginHistory:loginHistoryDao.getAll()) {
            loginHistoryDao.delete(loginHistory);
        }
        emInjector.remove(iisn);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        AuthTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
    }

    /**
     * 
     * @throws Exception
     *             Thrown.
     */
    @Test
    public void testAuthAttempts() throws Exception {
        String userName = "1234qwe1";
        String correctPassword = "password1234";
        String wrongPassword = "password1235";

        String secondUser = "1234qwe2";

        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                .param(PASSWORD, correctPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();

        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                .param(PASSWORD, wrongPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();

        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, secondUser)
                                .param(PASSWORD, correctPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();
        Thread.sleep(62000);
        authenticationAttemptsInitiator.authAttemptsInitiator();
        validateOutboundFile(outboundDropZones[0], authHeader, authTrailer, authExpectedRecordsFirst);
        AuthTestHelperUtil.deleteFilesInDirectories(outboundDropZones);
        
        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                .param(PASSWORD, correctPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();

        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, userName)
                                .param(PASSWORD, wrongPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();

        this.mockMvc
                .perform(
                        post(LOGIN_URL).accept(MediaType.APPLICATION_JSON).param(LOGIN_ID, secondUser)
                                .param(PASSWORD, correctPassword).param(IISN, iisn).param(TR_ID, trId))
                .andExpect(okStatus).andExpect(MockMvcResultMatchers.jsonPath("$.success", Boolean.TRUE).exists())
                .andReturn();

        Thread.sleep(62000);
        authenticationAttemptsInitiator.authAttemptsInitiator();
        validateOutboundFile(outboundDropZones[0], authHeader, authTrailer, authExpectedRecordsSecond);

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
    private static void validateOutboundFile(String fileName, String header, String trailer, String[] expectedRecords)
            throws IOException {
        File outboundDir = new File(fileName);
        Collection<File> filesInDir = FileUtils.listFiles(outboundDir, null, false);
        assertTrue(filesInDir.size() == 1);
        File file = (File) filesInDir.toArray()[0];
        List<String> lines = FileUtils.readLines(file);
        assertEquals(lines.size(),expectedRecords.length+2);
        for (int i = 1; i < lines.size() - 1; i++) {
            validateContent(lines.get(i), expectedRecords[i - 1]);
        }
        validateHeader(lines.get(0), header);
        validateTrailer(lines.get(lines.size() - 1), trailer);

    }

    /**
     * @param string
     * @param string2
     */
    private static void validateContent(String actualContent, String expectedRecords) {
        assertTrue(actualContent.startsWith(expectedRecords));

    }

    /**
     * @param string
     * @param trailer
     */
    private static void validateTrailer(String actualTrailer, String trailer) {
        String[] trailerLineSplit = actualTrailer.split(",");
        List<String> trailerLineSplits = Arrays.asList(trailerLineSplit);
        assertEquals(trailer, trailerLineSplits.get(0));

    }

    /**
     * @param actualHeader
     *            String - Actual Header.
     * @param header
     *            String - Expected Header. Validates the actual Header with the
     *            expected Header
     */
    private static void validateHeader(String actualHeader, String header) {
        assertTrue(actualHeader.startsWith(header));
    }

}