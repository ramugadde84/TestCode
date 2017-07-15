/**
 * 
 */
package org.tch.ste.infra.test.domain.dto;

import org.tch.ste.infra.annotation.FixedField;

/**
 * A test class for fixed length test.
 * 
 * @author Karthik.
 * 
 */
public class TestPerson extends BaseTestPerson {
    @FixedField(length = 5, offset = 12)
    private boolean married;

    @FixedField(length = 10, offset = 17)
    private String spouseName;

    @FixedField(length = 12, offset = 27)
    private Double salary;

    @FixedField(length = 19, offset = 39, overrideType = Long.class)
    private String creditCardNumber;

    /**
     * Returns the field creditCardNumber.
     * 
     * @return creditCardNumber String - Get the field.
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    /**
     * Sets the field creditCardNumber.
     * 
     * @param creditCardNumber
     *            String - Set the field creditCardNumber.
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    /**
     * Returns the field married.
     * 
     * @return married boolean - Get the field.
     */
    public boolean isMarried() {
        return married;
    }

    /**
     * Sets the field married.
     * 
     * @param married
     *            boolean - Set the field married.
     */
    public void setMarried(boolean married) {
        this.married = married;
    }

    /**
     * Returns the field spouseName.
     * 
     * @return spouseName String - Get the field.
     */
    public String getSpouseName() {
        return spouseName;
    }

    /**
     * Sets the field spouseName.
     * 
     * @param spouseName
     *            String - Set the field spouseName.
     */
    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    /**
     * Returns the field salary.
     * 
     * @return salary Double - Get the field.
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Sets the field salary.
     * 
     * @param salary
     *            Double - Set the field salary.
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
