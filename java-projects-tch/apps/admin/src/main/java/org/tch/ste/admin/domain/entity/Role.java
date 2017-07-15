/**
 * 
 */
package org.tch.ste.admin.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Role class.
 * 
 * @author Karthik.
 * 
 */
@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

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
     * @return name String - Get the field.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            String - Set the field name.
     */
    public void setName(String name) {
        this.name = name;
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
}
