/**
 * 
 */
package org.tch.ste.admin.domain.entity;

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
 * User-Role mapping class.
 * 
 * @author Karthik.
 * 
 */
@Entity
@Table(name = "USERS_ROLES")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;

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
     * @return user User - Get the field.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     *            User - Set the field user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return role Role - Get the field.
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role
     *            Role - Set the field role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

}
