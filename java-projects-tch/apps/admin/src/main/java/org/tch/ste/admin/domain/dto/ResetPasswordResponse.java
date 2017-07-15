/**
 * 
 */
package org.tch.ste.admin.domain.dto;

/**
 * @author kjanani
 * 
 */
public class ResetPasswordResponse {

    private boolean success;
    private UserPassword userPassword;

    /**
     * @return the userPassword
     */
    public UserPassword getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword
     *            the userPassword to set
     */
    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

}
