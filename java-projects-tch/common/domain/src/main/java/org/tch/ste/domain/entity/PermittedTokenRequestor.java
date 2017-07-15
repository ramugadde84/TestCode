package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Provides Token Requester Information,which is a wallet provider.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "PERMITTED_TOKEN_REQUESTORS")
public class PermittedTokenRequestor implements Serializable {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = -7110203332163389534L;

    /**
     * Primary key value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * Network Token Requester id.
     */
    @Column(name = "NETWORK_TR_ID", nullable = false)
    private Integer networkTokenRequestorId;

    /**
     * List of Permitted Token Requester Arns.
     */
    @OneToMany(mappedBy = "permittedTokenRequestor")
    private List<PermittedTokenRequestorArn> tokenRequestorArns;

    /**
     * 
     * @return primary key value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @return network token requestor id.
     */
    public Integer getNetworkTokenRequestorId() {
        return networkTokenRequestorId;
    }

    /**
     * @return list of permitted token requester arns.
     */
    public List<PermittedTokenRequestorArn> getTokenRequestorArns() {
        return tokenRequestorArns;
    }

    /**
     * @param id
     *            primay key.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param networkTokenRequestorId
     *            network token requester id.
     */
    public void setNetworkTokenRequestorId(Integer networkTokenRequestorId) {
        this.networkTokenRequestorId = networkTokenRequestorId;
    }

    /**
     * @param tokenRequestorArns
     *            list of permitted token requester arns.
     */
    public void setTokenRequestorArns(List<PermittedTokenRequestorArn> tokenRequestorArns) {
        this.tokenRequestorArns = tokenRequestorArns;
    }

}
