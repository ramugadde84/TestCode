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
 * This entity maps to TOKEN_POS_ENTRY_MODES table. TOKEN_POS_ENTRY_MODES table
 * maps Canonical model of entry modes for the purpose of applying token domain
 * restriction control rules.
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "TOKEN_POS_ENTRY_MODES")
public class TokenPosEntryMode implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENTRY_MODE")
    private String entryMode;

    @OneToMany(mappedBy = "tokenPosEntryModes")
    private List<TokenNetworkPosEntryModeMapping> tokenNetworkPosEntryModeMappings;

    /**
     * @return String description - gets the description of the entry mode
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return String entryMode -get the Canonical token POS entry modes
     */
    public String getEntryMode() {
        return entryMode;
    }

    /**
     * @return Integer id -get the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return tokenNetworkPosEntryModeMappings - gets list
     */
    public List<TokenNetworkPosEntryModeMapping> getTokenNetworkPosEntryModeMappings() {
        return tokenNetworkPosEntryModeMappings;
    }

    /**
     * @param description
     *            - sets the description of the entry mode
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param entryMode
     *            -set the Canonical token POS entry modes
     * 
     */
    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
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
     * @param tokenNetworkPosEntryModeMappings
     *            - gets the list
     */
    public void setTokenNetworkPosEntryModeMappings(
            List<TokenNetworkPosEntryModeMapping> tokenNetworkPosEntryModeMappings) {
        this.tokenNetworkPosEntryModeMappings = tokenNetworkPosEntryModeMappings;
    }

}
