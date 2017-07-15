/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * @author anus
 * 
 */
public enum VaultErrorCode {
    /**
     * 
     */
    INPUT_PARAMETER_VALUE_IS_INVALID("ERR002"),
    /**
     * 
     */
    ERROR_OCCURED_AT_VAULT_WHEN_PROCESSING_THE_REQUEST("ERR010"),
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
    private VaultErrorCode(String responseStr) {
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
