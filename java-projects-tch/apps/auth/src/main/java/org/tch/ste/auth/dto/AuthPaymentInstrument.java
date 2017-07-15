/**
 * 
 */
package org.tch.ste.auth.dto;

/**
 * DTO to carry payment instrument information.
 * 
 * @author Karthik.
 * 
 */
public class AuthPaymentInstrument {

    private Integer id;

    private String nickName;
    
    private String panLastFour;

    private String expMonthYear;

    private boolean active;

    /**
     * Returns the field expMonthYear.
     * 
     * @return expMonthYear String - Get the field.
     */
    public String getExpMonthYear() {
        return expMonthYear;
    }

    /**
     * Sets the field expMonthYear.
     * 
     * @param expMonthYear
     *            String - Set the field expMonthYear.
     */
    public void setExpMonthYear(String expMonthYear) {
        this.expMonthYear = expMonthYear;
    }

    /**
     * Returns the field active.
     * 
     * @return active boolean - Get the field.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the field active.
     * 
     * @param active
     *            boolean - Set the field active.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the ID of the Payment Instrument.
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID .
     * 
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the field nickName.
     * 
     * @return nickName String - Get the field.
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets the field nickName.
     * 
     * @param nickName
     *            String - Set the field nickName.
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Returns the field panLastFour.
     *
     * @return  panLastFour String - Get the field.
     */
    public String getPanLastFour() {
        return panLastFour;
    }

    /**
     * Sets the field panLastFour.
     * 
     * @param panLastFour  String - Set the field panLastFour.
     */
    public void setPanLastFour(String panLastFour) {
        this.panLastFour = panLastFour;
    }
}
