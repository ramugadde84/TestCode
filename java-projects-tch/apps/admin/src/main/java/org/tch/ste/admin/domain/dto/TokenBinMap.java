package org.tch.ste.admin.domain.dto;

/**
 * @author kjanani
 * 
 */
public class TokenBinMap {

    private String id;
    private String panBinId;
    private String tokenBinId;

    /**
     * 
     * 
     * @return -
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            -
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return -
     */
    public String getPanBinId() {
        return panBinId;
    }

    /**
     * @param panBinId
     *            -
     */
    public void setPanBinId(String panBinId) {
        this.panBinId = panBinId;
    }

    /**
     * @return -
     */
    public String getTokenBinId() {
        return tokenBinId;
    }

    /**
     * @param tokenBinId
     *            -
     */
    public void setTokenBinId(String tokenBinId) {
        this.tokenBinId = tokenBinId;
    }

}
