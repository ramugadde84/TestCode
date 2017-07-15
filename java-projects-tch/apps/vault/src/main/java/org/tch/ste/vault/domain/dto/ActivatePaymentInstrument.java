/**
 * 
 */
package org.tch.ste.vault.domain.dto;

import org.tch.ste.domain.entity.Token;

/**
 * @author kjanani
 * 
 */
public class ActivatePaymentInstrument {

    Token token;
    boolean success;

    /**
     * @return the token
     */
    public Token getToken() {
        return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
