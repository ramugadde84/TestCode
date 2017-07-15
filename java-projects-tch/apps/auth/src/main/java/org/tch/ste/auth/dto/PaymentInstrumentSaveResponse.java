/**
 * 
 */
package org.tch.ste.auth.dto;

/**
 * Payment Instrument Save Response.
 * 
 * @author sharduls
 * 
 */
public class PaymentInstrumentSaveResponse extends AbstractResponse {

	private String reponseCode;

	/**
	 * Get the Response Code for Saving Payment Instruments.
	 * 
	 * @return the reponseCode
	 */
	public String getReponseCode() {
		return reponseCode;
	}

	/**
	 * Set the Response Code.
	 * 
	 * @param reponseCode
	 *            the reponseCode to set
	 */
	public void setReponseCode(String reponseCode) {
		this.reponseCode = reponseCode;
	}

}
