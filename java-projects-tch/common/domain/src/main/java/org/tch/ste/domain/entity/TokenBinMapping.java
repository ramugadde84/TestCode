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
 * Token Bin mapping,which provides token to bin and bin to mapping.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "TOKEN_BIN_MAPPINGS")
public class TokenBinMapping implements Serializable {
    /**
     * Serial version id.
     */
    private static final long serialVersionUID = -7487083391539025455L;

    /**
     * Primary key value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    /**
     * Real PAN BIN.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAN_BIN_ID", nullable = false, referencedColumnName = "ID")
    private IisnBin iisnBin;
    /**
     * Token PAN BIN.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOKEN_BIN_ID", nullable = false, referencedColumnName = "ID")
    private TokenBin tokenBin;

    /**
     * @return primary key value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Token Bin.
     */
    public TokenBin getTokenBin() {
        return tokenBin;
    }

    /**
     * @param id
     *            primary key value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param tokenBin
     *            Token bin information.
     */
    public void setTokenBin(TokenBin tokenBin) {
        this.tokenBin = tokenBin;
    }

    /**
     * @return iisnBin
     */
    public IisnBin getIisnBin() {
        return iisnBin;
    }

    /**
     * @param iisnBin
     *            IisnBin - The IISN BIN.
     */
    public void setIisnBin(IisnBin iisnBin) {
        this.iisnBin = iisnBin;
    }

}
