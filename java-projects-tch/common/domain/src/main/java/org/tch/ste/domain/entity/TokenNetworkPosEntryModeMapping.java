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
 * This entity maps to TOKEN_NETWORK_POS_ENTRY_MODE_MAPPING table.
 * TOKEN_NETWORK_POS_ENTRY_MODE_MAPPING table maps network POS entry modes to a
 * canonical model of entry modes to normalize entry modes across networks
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "TOKEN_NETWORK_POS_ENTRY_MODE_MAPPING")
public class TokenNetworkPosEntryModeMapping implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NETWORK_POS_ENTRY_MODE_ID", referencedColumnName = "ID")
    private NetworkPosEntryMode networkPosEntryModes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOKEN_POS_ENTRY_MODE_ID", referencedColumnName = "ID")
    private TokenPosEntryMode tokenPosEntryModes;

    /**
     * @return Integer id -get the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return networkPosEntryModes - gets Network POS entry mode ID
     */
    public NetworkPosEntryMode getNetworkPosEntryModes() {
        return networkPosEntryModes;
    }

    /**
     * @return tokenPosEntryModes gets Token POS entry mode iD
     */
    public TokenPosEntryMode getTokenPosEntryModes() {
        return tokenPosEntryModes;
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
     * @param networkPosEntryModes
     *            - sets Network POS entry mode ID
     */
    public void setNetworkPosEntryModes(NetworkPosEntryMode networkPosEntryModes) {
        this.networkPosEntryModes = networkPosEntryModes;
    }

    /**
     * @param tokenPosEntryModes
     *            - sets Token POS entry mode iD
     */
    public void setTokenPosEntryModes(TokenPosEntryMode tokenPosEntryModes) {
        this.tokenPosEntryModes = tokenPosEntryModes;
    }

}
