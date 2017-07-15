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
@Table(name = "LOCKOUT_ATTEMPT_REPORT_HISTORIES")
public class LockedOutAttemptReportHistory implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "LOCKOUT_LAST_ATTEMPT_TIME")
    private Date lockOutLastAttemptTime;

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
     * Gets the lock out last attemtp time.
     * @return Date - The date.
     */
    public Date getLockOutLastAttemptTime() {
        return lockOutLastAttemptTime;
    }

    /**
     * Sets the lock out last attempt time.
     * @param lockOutLastAttemptTime Date - The lock out attempt time.
     */
    public void setLockOutLastAttemptTime(Date lockOutLastAttemptTime) {
        this.lockOutLastAttemptTime = lockOutLastAttemptTime;
    }

    
}
