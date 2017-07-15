/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * @author anus
 * 
 */
public enum VaultResponseCode {

    /**
     * 
     */
    SUCCESS_RESPONSE_CODE("0000"),

    /**
     * 
     */
    FAILURE_RESPONSE_CODE("0001");

    private String responseStr;

    /**
     * Constructor.
     * 
     * @param responseStr
     *            String - The parameter.
     */
    private VaultResponseCode(String responseStr) {
        this.responseStr = responseStr;
    }

    /**
     * Gets the field responseStr.
     * 
     * @return the responseStr
     */
    public String getResponseStr() {
        return responseStr;
    }
}
