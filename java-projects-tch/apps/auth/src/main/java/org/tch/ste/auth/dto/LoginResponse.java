/**
 * 
 */
package org.tch.ste.auth.dto;

import java.util.List;

/**
 * Response for Login.
 * 
 * @author Karthik.
 * 
 */
public class LoginResponse extends AbstractResponse {
    private Login login;
    
    private boolean locked;

    private List<AuthPaymentInstrument> paymentInstruments;
    
    private String redirectUrl;

    /**
     * Returns the field login.
     * 
     * @return login Login - Get the field.
     */
    public Login getLogin() {
        return login;
    }

    /**
     * Sets the field login.
     * 
     * @param login
     *            Login - Set the field login.
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * Returns the field paymentInstruments.
     * 
     * @return paymentInstruments List<AuthPaymentInstrument> - Get the field.
     */
    public List<AuthPaymentInstrument> getPaymentInstruments() {
        return paymentInstruments;
    }

    /**
     * Sets the field paymentInstruments.
     * 
     * @param paymentInstruments
     *            List<AuthPaymentInstrument> - Set the field
     *            paymentInstruments.
     */
    public void setPaymentInstruments(List<AuthPaymentInstrument> paymentInstruments) {
        this.paymentInstruments = paymentInstruments;
    }

    /**
     * Returns the field locked.
     *
     * @return  locked boolean - Get the field.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the field locked.
     * 
     * @param locked  boolean - Set the field locked.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Returns the field redirectUrl.
     *
     * @return  redirectUrl String - Get the field.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the field redirectUrl.
     * 
     * @param redirectUrl  String - Set the field redirectUrl.
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
