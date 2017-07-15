/**
 * 
 */
package org.tch.ste.domain.constant;

/**
 * The authentication type chosen for an issuer.
 * 
 * @author Karthik.
 * 
 */
public enum AuthenticationType {
    /**
     * Issuer-Hosted w/Real-Time Payment Instrument Provisioning.
     */
    ISSUER_HOSTED_WITH_REALTIME(1),
    /**
     * Issuer-Hosted w/Pre-Loaded Payment Instruments.
     */
    ISSUER_HOSTED_WITH_PRELOAD(2),
    /**
     * TCH-Hosted w/Issuer-Provided Credentials.
     */
    TCH_HOSTED_WITH_ISSUER_CREDS(3),
    /**
     * TCH-Hosted w/Generated Credentials.
     */
    TCH_HOSTED_WITH_GENERATED_CREDS(4),
    /**
     * TCH-Hosted w/Issuer Auth Web Service.
     */
    TCH_HOSTED_WITH_ISSUER_WEBSERVICE(5);

    private int type;

    /**
     * Private constructor.
     * 
     * @param type
     *            int - The type.
     */
    private AuthenticationType(int type) {
        this.type = type;
    }

    /**
     * Returns the type.
     * 
     * @return int - The type.
     */
    public int getType() {
        return this.type;
    }
}
