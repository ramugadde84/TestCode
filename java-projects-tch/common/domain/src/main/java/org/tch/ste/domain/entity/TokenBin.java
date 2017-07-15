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
 * Token Bin information.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "TOKEN_BINS")
public class TokenBin implements Serializable {

    /**
     * Serial version id.
     */
    private static final long serialVersionUID = 566849917335602910L;

    /**
     * Primary key value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    /**
     * Token bin.
     */
    @Column(name = "BIN", nullable = false)
    private String tokenBin;

    /**
     * List of Token Bin Mapping.
     */
    @OneToMany(mappedBy = "tokenBin", cascade = CascadeType.ALL)
    private List<TokenBinMapping> tokenBinMappings;

    /**
     * @return the primary key value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return token bin.
     */
    public String getTokenBin() {
        return tokenBin;
    }

    /**
     * @return list of token bin mapping.
     */
    public List<TokenBinMapping> getTokenBinMappings() {
        return tokenBinMappings;
    }

    /**
     * @param id
     *            primary key.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param tokenBin
     *            token bin.
     */
    public void setTokenBin(String tokenBin) {
        this.tokenBin = tokenBin;
    }

    /**
     * @param tokenBinMappings
     *            list of token bin mapping.
     */
    public void setTokenBinMappings(List<TokenBinMapping> tokenBinMappings) {
        this.tokenBinMappings = tokenBinMappings;
    }

}
