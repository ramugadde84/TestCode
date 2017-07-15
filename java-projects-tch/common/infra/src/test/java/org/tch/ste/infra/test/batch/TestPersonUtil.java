/**
 * 
 */
package org.tch.ste.infra.test.batch;

import org.tch.ste.infra.test.domain.dto.TestPerson;

/**
 * Utility to create a test person.
 * 
 * @author Karthik.
 * 
 */
public class TestPersonUtil {
    /**
     * Private Constructor.
     */
    private TestPersonUtil() {
        // empty
    }

    /**
     * Creates a person.
     * 
     * @param name
     *            String - The name.
     * @param age
     *            int - The age.
     * @param married
     *            boolean - Is (s)he married.
     * @param spouseName
     *            String - Spouse name.
     * @param salary
     *            double - The salary.
     * @param creditCardNumber
     *            String - The credit card number.
     * @return TestPerson - The created one.
     */
    public static TestPerson createPerson(String name, int age, boolean married, String spouseName, double salary,
            String creditCardNumber) {
        TestPerson testPerson = new TestPerson();
        testPerson.setName(name);
        testPerson.setAge(age);
        testPerson.setMarried(married);
        testPerson.setSpouseName(spouseName);
        testPerson.setSalary(salary);
        testPerson.setCreditCardNumber(creditCardNumber);
        return testPerson;
    }
}
