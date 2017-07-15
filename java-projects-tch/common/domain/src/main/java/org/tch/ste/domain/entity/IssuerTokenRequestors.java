package org.tch.ste.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Issuer To Token Requestor Mapping is done here,all the isseur id's and token
 * Requestor id's are stored.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "ISSUER_TOKEN_REQUESTORS")
public class IssuerTokenRequestors implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = -436457457886215766L;

    /**
     * Token Requestor Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * Many to one configuration to Issuer Configuration.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUER_CONFIGURATION_ID", referencedColumnName = "ID")
    private IssuerConfiguration issuerConfiguration;

    /**
     * Many to one configuration to Token Requestor Configuration.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOKEN_REQUESTOR_CONFIGURATION_ID", referencedColumnName = "ID")
    private TokenRequestorConfiguration tokenRequestorConfiguration;

    /**
     * @return the primary key.
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @return IssuerConfiguration details.
     */
    public IssuerConfiguration getIssuerConfiguration() {
        return issuerConfiguration;
    }

    /**
     * 
     * @return TokenRequestorConfiguration details.
     */
    public TokenRequestorConfiguration getTokenRequestorConfiguration() {
        return tokenRequestorConfiguration;
    }

    /**
     * 
     * @param id
     *            Primary key value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @param issuerConfiguration
     *            IssuerConfiguration object.
     */
    public void setIssuerConfiguration(IssuerConfiguration issuerConfiguration) {
        this.issuerConfiguration = issuerConfiguration;
    }

    /**
     * 
     * @param tokenRequestorConfiguration
     *            TokenRequestor Configuration.
     */
    public void setTokenRequestorConfiguration(TokenRequestorConfiguration tokenRequestorConfiguration) {
        this.tokenRequestorConfiguration = tokenRequestorConfiguration;
    }

}
