/**
 * 
 */
package org.tch.ste.admin.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.tch.ste.admin.constant.RoleType;

/**
 * A user class.
 * 
 * @author Karthik.
 * 
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "IISN")
    private String iisn;

    @Column(name = "LAST_LOGIN_TIME", nullable = true)
    private Date lastLoginTime;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "IS_ACTIVE")
    private Integer active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    /**
     * @param roleType
     *            String - The roletype.
     * @return boolean - True if has the role.
     */
    public boolean hasRole(RoleType roleType) {

        for (UserRole userRole : userRoles) {
            if (userRole.getId().equals(roleType.ordinal())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return id Integer - Get the field.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            Integer - Set the field id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userId - get the user id.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            - set the password
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return iisn - get the IISN.
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @param iisn
     *            - set the iisn
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @return lastLoginTime
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     *            - set Last Login Time
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return userRoles List<UserRole> - Get the field.
     */
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     * @param userRoles
     *            List<UserRole> - Set the field userRoles.
     */
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * Returns the field firstName.
     * 
     * @return firstName String - Get the field.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the field firstName.
     * 
     * @param firstName
     *            String - Set the field firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the field lastName.
     * 
     * @return lastName String - Get the field.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the field lastName.
     * 
     * @param lastName
     *            String - Set the field lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the field active.
     * 
     * @return active Integer - Get the field.
     */
    public Integer getActive() {
        return active;
    }

    /**
     * Sets the field active.
     * 
     * @param active
     *            Integer - Set the field active.
     */
    public void setActive(Integer active) {
        this.active = active;
    }
}
