package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * TokenRequestor DTO - The object that carries Token Requestor information from
 * screen to controller.
 * 
 * @author janarthanans
 * 
 */
public class TokenRequestor implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5859572937028313376L;

    private Integer id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(max = 11)
    private String networkId;

    /**
     * 
     * @return name - return the name of the token requestor
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *            - Set the issuer token requestor name to the instance.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return networkId - return networkId of the token requestor
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * 
     * @param networkId
     *            - Set the issuer token requestor networkId to the instance.
     */
    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * @return - return the id of the token requestor
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            - Set the issuer token requestor id to the instance.
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
