package org.tch.ste.admin.domain.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Login DTO - The object that carries login information from screen to
 * controller.
 * 
 * @author: Ganeshji Marwaha
 * @since: 4/26/14
 */
public class Login {

    @NotEmpty
    @NotNull
    @NotBlank
    private String loginId;

    @NotEmpty
    @NotNull
    @NotBlank
    private String password;

    /**
     * Returns the field loginId.
     * 
     * @return loginId String - Get the field.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the field loginId.
     * 
     * @param loginId
     *            String - Set the field loginId.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * Returns the field password.
     * 
     * @return password String - Get the field.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the field password.
     * 
     * @param password
     *            String - Set the field password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
