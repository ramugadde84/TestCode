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
 * This entity refers to NETWORK_POS_ENTRY_MODES table. NETWORK_POS_ENTRY_MODES
 * table Identifies the available POS entry modes as defined by a network
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "NETWORK_POS_ENTRY_MODES")
public class NetworkPosEntryMode implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4605318528692326562L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ENTRY_MODE")
    private String entryMode;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "networkPosEntryModes")
    private List<TokenNetworkPosEntryModeMapping> tokenNetworkPosEntryModeMappings;

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
     * @return String entryMode -get the Native network POS entry modes
     */
    public String getEntryMode() {
        return entryMode;
    }

    /**
     * @param entryMode
     *            - sets the native network POS entry modes
     * 
     * 
     */
    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
    }

    /**
     * @return String description -get the Description of the entry mode
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            - sets the description of the entry mode
     * 
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the field tokenNetworkPosEntryModeMappings.
     * 
     * @return tokenNetworkPosEntryModeMappings
     *         List<TokenNetworkPosEntryModeMapping> - The field.
     */
    public List<TokenNetworkPosEntryModeMapping> getTokenNetworkPosEntryModeMappings() {
        return tokenNetworkPosEntryModeMappings;
    }

    /**
     * Sets the field.
     * 
     * @param tokenNetworkPosEntryModeMappings
     *            the tokenNetworkPosEntryModeMappings to set.
     */
    public void setTokenNetworkPosEntryModeMappings(
            List<TokenNetworkPosEntryModeMapping> tokenNetworkPosEntryModeMappings) {
        this.tokenNetworkPosEntryModeMappings = tokenNetworkPosEntryModeMappings;
    }
}
