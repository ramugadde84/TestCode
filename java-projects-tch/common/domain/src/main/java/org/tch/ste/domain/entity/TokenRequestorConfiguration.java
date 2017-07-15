package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * This entity maps to TOKEN_REQUESTOR_CONFIGURATIONS table.
 * TOKEN_REQUESTOR_CONFIGURATIONS table contains the Configuration attributes
 * for token requestors
 * 
 * @author janani
 * 
 */
@Entity
@Table(name = "TOKEN_REQUESTOR_CONFIGURATIONS")
public class TokenRequestorConfiguration implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3904213242691477369L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NETWORK_TR_ID", nullable = false)
    private String networkTokenRequestId;

    @Column(name = "IS_DELETED")
    private String deleteFlag;

    @Column(name="STATUS_URI_KEY")
    private String statusUriKey;

    @Column(name="SUCCESS_URI_VALUE")
    private String successUriValue;

    @Column(name="FAILURE_URI_VALUE")
    private String failureUriValue;

    /**
     * Gets the field deleteFlag.
     * 
     * @return the deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the parameter deleteFlag to the field deleteFlag.
     * 
     * @param deleteFlag
     *            the deleteFlag to set
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * List of Token Requestor Mapping.
     */
    @OneToMany(mappedBy = "tokenRequestorConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssuerTokenRequestors> issuerTokenRequestoMaps;

    /**
     * 
     * @return list of issuer token requestor maps.
     */
    public List<IssuerTokenRequestors> getIssuerTokenRequestoMaps() {
        return issuerTokenRequestoMaps;
    }

    /**
     * 
     * @param issuerTokenRequestoMaps
     *            list of issuer token request maps.
     */
    public void setIssuerTokenRequestoMaps(List<IssuerTokenRequestors> issuerTokenRequestoMaps) {
        this.issuerTokenRequestoMaps = issuerTokenRequestoMaps;
    }


    /**
     * @return id - gets the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return name - gets the description of the token requestor
     */
    public String getName() {
        return name;
    }

    /**
     * @return networkTokenRequestId- Network provided transaction requestor ID
     */
    public String getNetworkTokenRequestId() {
        return networkTokenRequestId;
    }


    /**
     * @param id
     *            - sets the field id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param name
     *            - sets the description of the token requestor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param networkTokenRequestId
     *            - Network provided transaction requestor ID
     */
    public void setNetworkTokenRequestId(String networkTokenRequestId) {
        this.networkTokenRequestId = networkTokenRequestId;
    }

    /**
     * Returns the field statusUriKey.
     * 
     * @return statusUriKey String - Get the field.
     */
    public String getStatusUriKey() {
        return statusUriKey;
    }

    /**
     * Sets the field statusUriKey.
     * 
     * @param statusUriKey
     *            String - Set the field statusUriKey.
     */
    public void setStatusUriKey(String statusUriKey) {
        this.statusUriKey = statusUriKey;
    }

    /**
     * Returns the field successUriValue.
     * 
     * @return successUriValue String - Get the field.
     */
    public String getSuccessUriValue() {
        return successUriValue;
    }

    /**
     * Sets the field successUriValue.
     * 
     * @param successUriValue
     *            String - Set the field successUriValue.
     */
    public void setSuccessUriValue(String successUriValue) {
        this.successUriValue = successUriValue;
    }

    /**
     * Returns the field failureUriValue.
     * 
     * @return failureUriValue String - Get the field.
     */
    public String getFailureUriValue() {
        return failureUriValue;
    }

    /**
     * Sets the field failureUriValue.
     * 
     * @param failureUriValue
     *            String - Set the field failureUriValue.
     */
    public void setFailureUriValue(String failureUriValue) {
        this.failureUriValue = failureUriValue;
    }
}
