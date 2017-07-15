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
 * This entity refers to IISN_BINS table. IISN_BINS table Maps IISNs to BINs,
 * including both real and token BINs.
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "IISN_BINS")
public class IisnBin implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6870940165399328876L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IISN")
    private String iisn;

    @Column(name = "BIN")
    private String bin;

    @Column(name = "BIN_TYPE")
    private String binType;

    @OneToMany(mappedBy = "iisnBin", cascade = CascadeType.ALL)
    private List<TokenBinMapping> binMappings;

    /**
     * @return Integer id -get the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            -set the field id
     * 
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String Iisn- gets IISN of the issuer
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @param iisn
     *            - the IISN of the issuer
     * 
     * 
     * 
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @return String bin- gets BIN (either token or real) that belongs to the
     *         IISN
     */
    public String getBin() {
        return bin;
    }

    /**
     * @param bin
     *            - sets the BIN (either token or real) that belongs to the IISN
     * 
     */
    public void setBin(String bin) {
        this.bin = bin;
    }

    /**
     * @return binMappings
     */
    public List<TokenBinMapping> getBinMappings() {
        return binMappings;
    }

    /**
     * @param binMappings
     *            List<TokenBinMapping> - The bin mappings.
     */
    public void setBinMappings(List<TokenBinMapping> binMappings) {
        this.binMappings = binMappings;
    }

    /**
     * Returns the field binType.
     * 
     * @return binType String - Get the field.
     */
    public String getBinType() {
        return binType;
    }

    /**
     * Sets the field binType.
     * 
     * @param binType
     *            String - Set the field binType.
     */
    public void setBinType(String binType) {
        this.binType = binType;
    }
}
