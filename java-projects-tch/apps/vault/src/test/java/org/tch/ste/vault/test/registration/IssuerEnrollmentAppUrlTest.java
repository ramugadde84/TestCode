/**
 * 
 */
package org.tch.ste.vault.test.registration;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.tch.ste.vault.constant.VaultControllerTest.C_ISSUER_ENROLLMENT_APP_URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.util.JsonObjectConverter;
import org.tch.ste.test.base.NonSecureIntegrationTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlResponseBody;
import org.tch.ste.vault.domain.dto.IssuerEnrollmentUrlResponseObj;

/**
 * Test for Issuer Enrollment App.
 * 
 * @author anus
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class IssuerEnrollmentAppUrlTest extends NonSecureIntegrationTest {
    private String requestJson = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":1234,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":123456}}}";

    private String requestJson1 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":12345678}}}";

    private String requestJson2 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":1234}}}";

    private String requestJson3 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":5678}}}";

    private String requestJson4 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":9012}}}";

    private String requestJson5 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":1145}}}";

    private String requestJson6 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":6678}}}";

    private String requestJson7 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":4566,\"CIID\":\"\",\"IISN\":6678}}}";

    private String requestJson8 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":47890,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":6678}}}";

    private String requestJson9 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":null,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":null}}}";

    private String requestJson10 = "{\"GetIssuerEnrollmentAppURLRequest\":\"\":{\"Version\":\"1.0\",\"SourceID\":\"WP000001\",\"MessageID\":\"3F2504E04F8911D39A0C0305E82C33012345\",\"CloudID\":\"TSC001\"},\"\":{\"TRID\":null,\"CIID\":\"abcdefghijklmnopqrstuvwxyzabcdefghij\",\"IISN\":null}}}";

    private String requestJson11 = "{\"GetIssuerEnrollmentAppURLRequest\":{\"MessageHeader\":{\"Version\":\"%%\",\"SourceID\":\"$%\",\"MessageID\":\"**$**\",\"CloudID\":\"#$%^**\"},\"GetIssuerEnrollmentAppURLRequestBody\":{\"TRID\":1234,\"CIID\":\"*&^%\",\"IISN\":1234}}}";

    @Autowired
    @Qualifier("jsonConverter")
    private JsonObjectConverter<IssuerEnrollmentUrlResponseObj> responseConverter;

    @Autowired
    private VaultProperties vaultProperties;

    /**
     * Tests the enrollment application url.
     * 
     * @throws Exception
     *             - Thrown. This method is used to validte the issuer url
     * 
     */
    @Test
    public void testIssuerEnrollmentAppurl() throws Exception {

        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        GetIssuerEnrollmentAppUrlResponseBody responseBody = response.getIssuerEnrollmentUrlResponse().getResponseBody();
        assertEquals(responseBody.getIssuerUrl(),
                "http://localhost:8090/auth/app/login?iisn=123456&trId=1234&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(responseBody.getCiid(), "abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(responseBody.getTrid(), new Integer("1234"));
        assertEquals(response.getIssuerEnrollmentUrlResponse().getMessageHeader().getCloudId(),
                vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_CLOUD_SWITCH_ID));
        assertEquals(response.getIssuerEnrollmentUrlResponse().getMessageHeader().getSourceId(),
                vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_SOURCE_ID));

    }

    /**
     * This method is used to test if the given iisn is valid
     * 
     * @throws Exception
     *             - Thrown.
     */
    @Test
    public void testIisnValid() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson1).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
       assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(), "Input parameter value is invalid");
    }

    /**
     * Tests all five variations for the Get Issuer Enrollment Url. - Auth
     * Option 1- Fetch from AUTH_APP_URL in ISSUER_CONFIGURATIONS. - Auth Option
     * 2 - Fetch from AUTH_APP_URL in ISSUER_CONFIGURATIONS. - Auth Option 3 -
     * Fetch from properties file. - Auth Option 4 - Fetch from properties file.
     * - Auth Option 5 - Fetch from properties file.
     * 
     * @throws Exception
     *             = Thrown.
     */
    @Test
    public void testIssuerUrl() throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        MvcResult res2 = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson2).contentType(mediaType)).andReturn();
        MvcResult res3 = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson3).contentType(mediaType)).andReturn();
        MvcResult res4 = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson4).contentType(mediaType)).andReturn();
        MvcResult res5 = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson5).contentType(mediaType)).andReturn();
        MvcResult res6 = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson6).contentType(mediaType)).andReturn();

        IssuerEnrollmentUrlResponseObj response2 = responseConverter.objectify(res2.getResponse()
                .getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        IssuerEnrollmentUrlResponseObj response3 = responseConverter.objectify(res3.getResponse()
                .getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        IssuerEnrollmentUrlResponseObj response4 = responseConverter.objectify(res4.getResponse()
                .getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        IssuerEnrollmentUrlResponseObj response5 = responseConverter.objectify(res5.getResponse()
                .getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        IssuerEnrollmentUrlResponseObj response6 = responseConverter.objectify(res6.getResponse()
                .getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        
        assertEquals(response2.getIssuerEnrollmentUrlResponse().getResponseBody().getIssuerUrl(), "http://www.issuerurl.com/login?iisn=1234&trId=4566&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(response3.getIssuerEnrollmentUrlResponse().getResponseBody().getIssuerUrl(), "http://www.issuerurl1.com/login?iisn=5678&trId=4566&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(response4.getIssuerEnrollmentUrlResponse().getResponseBody().getIssuerUrl(),
                "http://localhost:8090/auth/app/login?iisn=9012&trId=4566&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(response5.getIssuerEnrollmentUrlResponse().getResponseBody().getIssuerUrl(),
                "http://localhost:8090/auth/app/login?iisn=1145&trId=4566&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
        assertEquals(response6.getIssuerEnrollmentUrlResponse().getResponseBody().getIssuerUrl(),
                "http://localhost:8090/auth/app/login?iisn=6678&trId=4566&ciid=abcdefghijklmnopqrstuvwxyzabcdefghij");
    }

    /**
     * This method is used to check the mandatory Fields
     * 
     * @throws Exception
     *             - Thrown.
     * 
     */
    @Test
    public void testMandatoryFields() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson7).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(), "Input parameter value is invalid");
    }

    /**
     * This method is used to check if the token requestor is valid
     * 
     * @throws Exception
     *             - Thrown.
     * 
     */
    @Test
    public void testTokenRequestorValid() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson8).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(), "Input parameter value is invalid");
    }

    /**
     * This method is used to check any exception has occurred while processing
     * 
     * @throws Exception
     *             - Thrown.
     * 
     */
    @Test
    public void testErrors() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson9).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(),
                "Input parameter value is invalid");
    }

    /**
     * This method is used to check if the JSON structure is valid
     * 
     * @throws Exception
     *             - Thrown.
     * 
     */
    @Test
    public void testJsonStructure() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson10).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(),
                "Error occurred at Vault when processing the request");
    }

    /**
     * This method is used to check for the special characters
     * 
     * @throws Exception
     *             - Thrown.
     * 
     */
    @Test
    public void testSpecialCharacters() throws Exception {
        MvcResult res = this.mockMvc.perform(
                post(C_ISSUER_ENROLLMENT_APP_URL).content(requestJson11).contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        IssuerEnrollmentUrlResponseObj response = responseConverter.objectify(
                res.getResponse().getContentAsString(), IssuerEnrollmentUrlResponseObj.class);
        assertEquals(response.getIssuerEnrollmentUrlResponse().getResponseBody().getErrorlist().getErrorMessage(), "Input parameter value is invalid");
    }
}
