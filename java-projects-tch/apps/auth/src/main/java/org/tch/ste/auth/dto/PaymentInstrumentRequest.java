package org.tch.ste.auth.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author sharduls
 * 
 */
public class PaymentInstrumentRequest implements Serializable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 4884134028272729778L;

	private String iisn;

	private String ciid;

	private String trId;

	private List<Integer> activePaymentInstruments;

	private List<Integer> inactivePaymentInstruments;
	

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
	 * Get the CIID.
	 * 
	 * @return the ciid
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
	 * @return the activePaymentInstruments
	 */
	public List<Integer> getActivePaymentInstruments() {
		return activePaymentInstruments;
	}

	/**
	 * @param activePaymentInstruments the activePaymentInstruments to set
	 */
	public void setActivePaymentInstruments(List<Integer> activePaymentInstruments) {
		this.activePaymentInstruments = activePaymentInstruments;
	}

	/**
	 * @return the inactivePaymentInstruments
	 */
	public List<Integer> getInactivePaymentInstruments() {
		return inactivePaymentInstruments;
	}

	/**
	 * @param inactivePaymentInstruments the inactivePaymentInstruments to set
	 */
	public void setInactivePaymentInstruments(
			List<Integer> inactivePaymentInstruments) {
		this.inactivePaymentInstruments = inactivePaymentInstruments;
	}
    
    
}
