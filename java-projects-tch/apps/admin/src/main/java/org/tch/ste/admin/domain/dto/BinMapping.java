package org.tch.ste.admin.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Dto to hold bin mapping information.
 * 
 * @author pamartheepan
 * 
 */
public class BinMapping implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5505096942660480083L;

    private Integer id;

    /*
     * realBin id
     */
    @NotNull
    @NotEmpty
    @NotBlank
    private String realBin;

    /*
     * tokenBin id
     */
    @NotNull
    @NotBlank
    private String tokenBin;
    /*
     * iisn id
     */
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(max = 6)
    private String iisn;

    /**
     * @return tokenBin
     */

    public String getTokenBin() {
        return tokenBin;
    }

    /**
     * @param tokenBin
     *            String.
     */
    public void setTokenBin(String tokenBin) {
        this.tokenBin = tokenBin;
    }

    /**
     * @return realBin
     */
    public String getRealBin() {
        return realBin;
    }

    /**
     * @param realBin
     *            String.
     */
    public void setRealBin(String realBin) {
        this.realBin = realBin;
    }

    /**
     * @return iisn
     */
    public String getIisn() {
        return iisn;
    }

    /**
     * @param iisn
     *            String - The iisn.
     */
    public void setIisn(String iisn) {
        this.iisn = iisn;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
