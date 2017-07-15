package org.tch.ste.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity maps to NETWORK table. NETWORK table defines the expected
 * networks in the ecosystem
 * 
 * @author Janani.
 * 
 */
@Entity
@Table(name = "NETWORKS")
public class Network implements Serializable {

    private static final long serialVersionUID = 3014255875979444148L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "BIN_FIRST_DIGIT")
    private String binFirstDigit;

    @Column(name = "NAME")
    private String name;

    /**
     * @return Integer id -get the field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            - sets the field id
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String binFirstDigit -get the First digit of the BIN that
     *         identifies the network owner
     */
    public String getBinFirstDigit() {
        return binFirstDigit;
    }

    /**
     * @param binFirstDigit
     *            -sets the First digit of the BIN that identifies the network
     *            owner
     */
    public void setBinFirstDigit(String binFirstDigit) {
        this.binFirstDigit = binFirstDigit;
    }

    /**
     * @return String name -gets the name of the network
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            -sets the name of the network
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

}
