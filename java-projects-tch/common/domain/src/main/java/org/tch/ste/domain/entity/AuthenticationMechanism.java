package org.tch.ste.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * AuthenticationMechanism refers to AUTHENTICATION_MECHANISM table.
 * AuthenticationMechanism holds the type of mechanisms to authenticate an
 * issuer.
 * 
 * @author kjanani
 * 
 */
@Entity
@Table(name = "AUTHENTICATION_MECHANISM")
public class AuthenticationMechanism implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 3256510479324433435L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "AUTH_MECHANISM")
    private String name;

    /**
     * Get the primary key
     * 
     * @return - Returns the unique ID
     */
    public int getId() {
        return id;
    }

    /**
     * set the ID
     * 
     * @param id
     *            - Sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * Get the name
     * 
     * @return - return the name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Get the name
     * 
     * @param name
     *            - sets the name
     */

    public void setName(String name) {
        this.name = name;
    }

}
