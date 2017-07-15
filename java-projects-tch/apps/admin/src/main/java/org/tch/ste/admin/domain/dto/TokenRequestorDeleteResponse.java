/**
 * 
 */
package org.tch.ste.admin.domain.dto;

/**
 * @author anus
 * 
 */
public class TokenRequestorDeleteResponse extends TokenRequestorResponse {
    /**
     * 
     */
    private static final long serialVersionUID = 2991061216389301247L;
    private String deleteMessage;

    /**
     * Gets the field tokenRequestor.
     * 
     * @return the tokenRequestor
     */
    public TokenRequestor getTokenRequestor() {
        return tokenRequestor;
    }

    /**
     * Sets the parameter tokenRequestor to the field tokenRequestor.
     * 
     * @param tokenRequestor
     *            the tokenRequestor to set
     */
    public void setTokenRequestor(TokenRequestor tokenRequestor) {
        this.tokenRequestor = tokenRequestor;
    }

    private TokenRequestor tokenRequestor;

    /**
     * Gets the field deleteMessage.
     * 
     * @return the deleteMessage
     */
    public String getDeleteMessage() {
        return deleteMessage;
    }

    /**
     * Sets the parameter deleteMessage to the field deleteMessage.
     * 
     * @param deleteMessage
     *            the deleteMessage to set
     */
    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

}
