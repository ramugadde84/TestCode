/**
 * 
 */
package org.tch.ste.auth.dto;

/**
 * @author sharduls
 * 
 */
public class PaymentInstrumentResponse extends AbstractResponse {

    private String redirectUrl;

    /**
     * Returns the field redirectUrl.
     * 
     * @return redirectUrl String - Get the field.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the field redirectUrl.
     * 
     * @param redirectUrl
     *            String - Set the field redirectUrl.
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
