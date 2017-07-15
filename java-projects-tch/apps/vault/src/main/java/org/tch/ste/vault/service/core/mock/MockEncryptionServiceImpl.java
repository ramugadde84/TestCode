package org.tch.ste.vault.service.core.mock;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.exception.SteException;

/**
 * @author sujathas
 * 
 */
@Service
public class MockEncryptionServiceImpl implements MockEncryptionService {

    private static byte[] encryptionKey = { 0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65,
            0x74, 0x4b, 0x65, 0x79 };

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.core.mock.MockEncryptionService#encrypt()
     */
    @Override
    public String encrypt(String value) throws SteException {
        Cipher cipher;

        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(cipher.doFinal(value.getBytes(InfraConstants.ENCODING_VALUE)))
                    .replace("\r", "").replace("\n", "");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | UnsupportedEncodingException e) {
            throw new SteException("unable to encrypt value: ", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.mock.MockEncryptionService#decrypt(java
     * .lang.String)
     */
    @Override
    public String decrypt(String encryptedValue) throws SteException {

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.decodeBase64(encryptedValue.getBytes(Charset.forName("UTF-8")))),
                    "UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw new SteException("unable to decrypt the value", e);
        }

    }
}
