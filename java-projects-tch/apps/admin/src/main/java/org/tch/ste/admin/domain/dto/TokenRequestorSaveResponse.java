package org.tch.ste.admin.domain.dto;

/**
 * DTO to return the response.
 * 
 * @author anus
 * 
 */
public class TokenRequestorSaveResponse extends TokenRequestorResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -5053500689368495607L;

    private String nameErrorMsg;

    private String networkIdErrorMsg;

    private String saveSuccessMsg;

    private TokenRequestor tokenRequestor;

    /**
     * Gets the filed nameErrorMsg.
     * 
     * @return the nameErrorMsg
     */
    public String getNameErrorMsg() {
        return nameErrorMsg;
    }

    /**
     * Sets the parameter nameErrorMsg to the field nameErrorMsg.
     * 
     * @param nameErrorMsg
     *            the nameErrorMsg to set
     */
    public void setNameErrorMsg(String nameErrorMsg) {
        this.nameErrorMsg = nameErrorMsg;
    }

    /**
     * Gets the filed networkIdErrorMsg.
     * 
     * @return the networkIdErrorMsg
     */
    public String getNetworkIdErrorMsg() {
        return networkIdErrorMsg;
    }

    /**
     * Sets the parameter networkIdErrorMsg to the field networkIdErrorMsg.
     * 
     * @param networkIdErrorMsg
     *            the networkIdErrorMsg to set
     */
    public void setNetworkIdErrorMsg(String networkIdErrorMsg) {
        this.networkIdErrorMsg = networkIdErrorMsg;
    }

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

    /**
     * Get the success message to display in user interface
     * 
     * @return the saveSuccessMsg
     */
    public String getSaveSuccessMsg() {
        return saveSuccessMsg;
    }

    /**
     * Set the save success message to display in user interface
     * 
     * @param saveSuccessMsg
     *            the successSaveMsg to set
     */
    public void setSaveSuccessMsg(String saveSuccessMsg) {
        this.saveSuccessMsg = saveSuccessMsg;
    }
}
