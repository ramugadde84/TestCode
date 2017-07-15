/**
 * 
 */
package org.tch.ste.domain.dto;

/**
 * Contains the necessary details related to password
 * 
 * @author kjanani
 * 
 */
public class Password {

    private String hashedPassword;
    private String passwordSalt;
    private String plainTextPassword;

    /**
     * Gets the hashed password
     * 
     * @return the hashedPassword
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets the hashed password
     * 
     * @param hashedPassword
     *            the hashedPassword to set
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Gets the hashed password
     * 
     * @return the passwordSalt
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * Sets the password salt
     * 
     * @param passwordSalt
     *            the passwordSalt to set
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    /**
     * Gets the plain text password
     * 
     * @return the plainTextPassword
     */
    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    /**
     * Sets the plain text password
     * 
     * @param plainTextPassword
     *            the plainTextPassword to set
     */
    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

}
