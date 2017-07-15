/**
 * 
 */
package org.tch.ste.infra.service.core.security;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.util.EncryptionXmlTransformer;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */

public class EncryptionServiceImpl implements EncryptionService {

    private static Logger logger = LoggerFactory.getLogger(EncryptionServiceImpl.class);

    private VaultProperties vaultProperties;

    /**
     * @return the vaultProperties
     */
    public VaultProperties getVaultProperties() {
        return vaultProperties;
    }

    /**
     * @param vaultProperties
     *            the vaultProperties to set
     */
    public void setVaultProperties(VaultProperties vaultProperties) {
        this.vaultProperties = vaultProperties;
    }

    /*
     * (non-Javadoc)
     * 
     * 
     * @see
     * org.tch.ste.infra.service.core.security.EncryptionService#encrypt(java
     * .lang.String)
     */
    @Override
    public String encrypt(String value) throws SteException {
        String url = vaultProperties.getKey(InfraConstants.ENCRYPTION_VALUE);
        return EncryptionXmlTransformer.readXml((clientConnect(url,
                EncryptionXmlTransformer.generateXML(value, InfraConstants.ENCRYPT_VALUE))));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.service.core.security.EncryptionService#decrypt(java
     * .lang.String)
     */
    @Override
    public String decrypt(String value) throws SteException {
        String url = vaultProperties.getKey(InfraConstants.ENCRYPTION_VALUE);
        return EncryptionXmlTransformer.readXml((clientConnect(url,
                EncryptionXmlTransformer.generateXML(value, InfraConstants.DECRYPT_VALUE))));
    }

    /**
     * @param url
     *            -Url to Connect
     * @param value
     *            - Value
     * @return -Returns the
     * @throws SteException
     *             - The thrown exception.
     */
    public String clientConnect(String url, String value) throws SteException {
        String outputXml = null;
        int statusCode = 0;

        HttpPost post = new HttpPost(url);
        try {

            HttpClient client = new DefaultHttpClient();
            post.setEntity(new StringEntity(value));
            HttpResponse response = client.execute(post);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                outputXml = EntityUtils.toString(response.getEntity());
            }

        } catch (Exception e) {
            logger.warn("Unable to Connect to the Client", e);
            throw new SteException("Error while connecting to URL:" + url, e);
        }
        return outputXml;
    }

}
