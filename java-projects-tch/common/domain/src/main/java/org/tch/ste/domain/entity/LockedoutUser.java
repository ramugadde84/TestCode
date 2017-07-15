/**
 * 
 */
package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This Entity contains last login_history id value.
 * 
 * @author ramug
 * 
 */
@Entity
@Table(name = "LOCKED_OUT_USER")
public class LockedoutUser implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "LOGIN_HISTORY_ID")
    private Date loginHistoryId;

    /**
     * Gets the Id value - primary key.
     * 
     * @return the id value.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id value.
     * 
     * @param id
     *            - id value.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Login history id value.
     * 
     * @return login history id value.
     */
    public Date getLoginHistoryId() {
        return loginHistoryId;
    }

    /**
     * Sets the login history id value.
     * 
     * @param loginHistoryId
     *            - login history id value.
     */
    public void setLoginHistoryId(Date loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }
}
