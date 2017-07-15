package org.tch.ste.vault.web.controller;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_ENCRYPT_VALUE;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.dto.EncryptionObject;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.util.EncryptionXmlTransformer;
import org.tch.ste.vault.service.core.mock.MockEncryptionService;

/**
 * Controller to Handle Encryption and Decryption
 * 
 * @author sujathas
 * 
 */
@Controller
@RequestMapping(C_ENCRYPT_VALUE)
public class MockEncryptionController {

    String encryptedVal = null;
    @Autowired
    private MockEncryptionService mockEncryptionService;

    /**
     * @param request
     *            -Request Value
     * @param response
     *            -Response
     * @return HttpEntity<byte[]> - The xml.
     * @throws SteException
     *             -Ste Exception
     * 
     */
    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<byte[]> fetchData(HttpServletRequest request, HttpServletResponse response) throws SteException {
        byte[] retVal = null;
        String decryptedVal;
        StringBuilder xmlValue = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                xmlValue.append(line);
            }

            EncryptionObject encObject = EncryptionXmlTransformer.getXmlData(xmlValue.toString());
            String attrVal = encObject.getValue();
            if (InfraConstants.ENCRYPT_VALUE.equals(attrVal)) {
                encryptedVal = mockEncryptionService
                        .encrypt(EncryptionXmlTransformer.parseInputXml(xmlValue.toString()));
                retVal = EncryptionXmlTransformer.generateResponse(encryptedVal, attrVal).getBytes(
                        Charset.forName("UTF-8"));
            } else if (InfraConstants.DECRYPT_VALUE.equals(encObject.getValue())) {
                decryptedVal = mockEncryptionService
                        .decrypt(EncryptionXmlTransformer.parseInputXml(xmlValue.toString()));
                retVal = EncryptionXmlTransformer.generateResponse(decryptedVal, attrVal).getBytes(
                        Charset.forName("UTF-8"));
            }
        } catch (IOException | SteException e) {
            throw new SteException("unable to genearte the response", e);
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_XML);
        if (retVal != null) {
            header.setContentLength(retVal.length);
        }
        return new HttpEntity<byte[]>(retVal, header);
    }
}
