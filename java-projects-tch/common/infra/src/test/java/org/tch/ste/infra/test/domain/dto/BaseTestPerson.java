/**
 * 
 */
package org.tch.ste.infra.test.domain.dto;

import org.tch.ste.infra.annotation.FixedField;

/**
 * Base class.
 * 
 * @author Karthik.
 * 
 */
public class BaseTestPerson {
    @FixedField(length = 10, offset = 0)
    private String name;

    @FixedField(length = 2, offset = 10)
    private int age;

    /**
     * Returns the field name.
     * 
     * @return name String - Get the field.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the field name.
     * 
     * @param name
     *            String - Set the field name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the field age.
     * 
     * @return age int - Get the field.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the field age.
     * 
     * @param age
     *            int - Set the field age.
     */
    public void setAge(int age) {
        this.age = age;
    }
}
