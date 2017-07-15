package org.tch.ste.infra.dto;

/**
 * @author sujathas
 * 
 */
public class EncryptionObject {

    private String action;

    private String value;

    /**
     * @return -Returns the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action
     *            -Sets the action value
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return -Returns the action value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            - Sets the action Value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
