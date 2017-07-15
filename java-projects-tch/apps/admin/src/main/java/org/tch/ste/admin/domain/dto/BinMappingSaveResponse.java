/**
 * 
 */
package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

/**
 * Response to a save request.
 * 
 * @author Karthik.
 * 
 */
public class BinMappingSaveResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1311647819952724828L;

    private boolean success;

    private String realBinErrMsg;

    private String tokenBinErrMsg;

    private BinMapping binMapping;

    /**
     * Returns the field success.
     * 
     * @return success boolean - Get the field.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the field success.
     * 
     * @param success
     *            boolean - Set the field success.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the field realBinErrMsg.
     * 
     * @return realBinErrMsg String - Get the field.
     */
    public String getRealBinErrMsg() {
        return realBinErrMsg;
    }

    /**
     * Sets the field realBinErrMsg.
     * 
     * @param realBinErrMsg
     *            String - Set the field realBinErrMsg.
     */
    public void setRealBinErrMsg(String realBinErrMsg) {
        this.realBinErrMsg = realBinErrMsg;
    }

    /**
     * Returns the field tokenBinErrMsg.
     * 
     * @return tokenBinErrMsg String - Get the field.
     */
    public String getTokenBinErrMsg() {
        return tokenBinErrMsg;
    }

    /**
     * Sets the field tokenBinErrMsg.
     * 
     * @param tokenBinErrMsg
     *            String - Set the field tokenBinErrMsg.
     */
    public void setTokenBinErrMsg(String tokenBinErrMsg) {
        this.tokenBinErrMsg = tokenBinErrMsg;
    }

    /**
     * Returns the field binMapping.
     * 
     * @return binMapping BinMapping - Get the field.
     */
    public BinMapping getBinMapping() {
        return binMapping;
    }

    /**
     * Sets the field binMapping.
     * 
     * @param binMapping
     *            BinMapping - Set the field binMapping.
     */
    public void setBinMapping(BinMapping binMapping) {
        this.binMapping = binMapping;
    }
}
