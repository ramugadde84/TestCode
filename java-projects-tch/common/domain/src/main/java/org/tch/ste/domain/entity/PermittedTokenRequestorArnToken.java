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
 * Token Requester ARN to tokens mapping.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "PERMITTED_TOKEN_REQUESTOR_ARNS_TOKENS")
public class PermittedTokenRequestorArnToken implements Serializable {

    /**
     * serial version id.
     */
    private static final long serialVersionUID = 4405515864686269031L;

    /**
     * primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    /**
     * Token Requestor Arn.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERMITTED_TOKEN_REQUESTOR_ARN_ID", referencedColumnName = "ID")
    private PermittedTokenRequestorArn permittedTokenRequestorArn;

    /**
     * Token.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOKEN_ID", referencedColumnName = "ID")
    private Token token;

    /**
     * @return primary key of permitted token requester arn token.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return permitted token requester arn.
     */
    public PermittedTokenRequestorArn getPermittedTokenRequestorArn() {
        return permittedTokenRequestorArn;
    }

    /**
     * @return Token object.
     */
    public Token getToken() {
        return token;
    }

    /**
     * @param id
     *            primary key.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param permittedTokenRequestorArn
     *            PermittedTokenReequestor object.
     */
    public void setPermittedTokenRequestorArn(PermittedTokenRequestorArn permittedTokenRequestorArn) {
        this.permittedTokenRequestorArn = permittedTokenRequestorArn;
    }

    /**
     * @param token
     *            Token object.
     */
    public void setToken(Token token) {
        this.token = token;
    }

}
