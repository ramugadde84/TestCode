/**
 * 
 */
package org.tch.ste.auth.dto;

/**
 * The model which is used for the login page.
 * 
 * @author Karthik.
 * 
 */
public class LoginModel {
	private String issuerLogoName;

	private String issuerLogo;

	private String iisn;

	private String trId;
    
    private String redirectUrl;

	private String ciid;

	/**
	 * Returns the field issuerLogoName.
	 * 
	 * @return issuerLogoName String - Get the field.
	 */
	public String getIssuerLogoName() {
		return issuerLogoName;
	}

	/**
	 * Sets the field issuerLogoName.
	 * 
	 * @param issuerLogoName
	 *            String - Set the field issuerLogoName.
	 */
	public void setIssuerLogoName(String issuerLogoName) {
		this.issuerLogoName = issuerLogoName;
	}

	/**
	 * Returns the field issuerLogo.
	 * 
	 * @return issuerLogo String - Get the field.
	 */
	public String getIssuerLogo() {
		return issuerLogo;
	}

	/**
	 * Sets the field issuerLogo.
	 * 
	 * @param issuerLogo
	 *            String - Set the field issuerLogo.
	 */
	public void setIssuerLogo(String issuerLogo) {
		this.issuerLogo = issuerLogo;
	}

	/**
	 * @return the iisn
	 */
	public String getIisn() {
		return iisn;
	}

	/**
	 * @param iisn
	 *            the iisn to set
	 */
	public void setIisn(String iisn) {
		this.iisn = iisn;
	}

	/**
	 * @return the trId
	 */
	public String getTrId() {
		return trId;
	}

	/**
	 * @param trId
	 *            the trId to set
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
     * Returns the field redirectUrl.
     *
     * @return  redirectUrl String - Get the field.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the field redirectUrl.
     * 
     * @param redirectUrl  String - Set the field redirectUrl.
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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
