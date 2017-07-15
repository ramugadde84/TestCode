/**
 * 
 */
package org.tch.ste.auth.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * A login DTO which contains neccessary details.
 * 
 * @author Karthik.
 * 
 */
public class Login {
	@NotBlank
	private String userId;

	@NotBlank
	private String password;

	@NotBlank
	private String iisn;

	@NotBlank
	private String trId;

	private String ciid;

	/**
	 * Returns the field userId.
	 * 
	 * @return userId String - Get the field.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the field userId.
	 * 
	 * @param userId
	 *            String - Set the field userId.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

	/**
	 * Returns the field iisn.
	 * 
	 * @return iisn String - Get the field.
	 */
	public String getIisn() {
		return iisn;
	}

	/**
	 * Sets the field iisn.
	 * 
	 * @param iisn
	 *            String - Set the field iisn.
	 */
	public void setIisn(String iisn) {
		this.iisn = iisn;
	}

	/**
	 * Returns the field trId.
	 * 
	 * @return trId String - Get the field.
	 */
	public String getTrId() {
		return trId;
	}

	/**
	 * Sets the field trId.
	 * 
	 * @param trId
	 *            String - Set the field trId.
	 */
	public void setTrId(String trId) {
		this.trId = trId;
	}

	/**
	 * Get the CIID.
	 * 
	 * @return the ciid - CIID.
	 */
	public String getCiid() {
		return ciid;
	}

	/**
	 * Set the CIID.
	 * 
	 * @param ciid
	 *            the ciid to set
	 */
	public void setCiid(String ciid) {
		this.ciid = ciid;
	}
}
