/**
 * 
 */
package org.tch.ste.test.base;

import org.junit.After;
import org.junit.Before;

/**
 * Creates the JNDI Resources which are used by other tests.
 * 
 * @author Karthik.
 * 
 */
public class BaseTest {

    /**
     * Default Constructor.
     * 
     */
    public BaseTest() {

    }

    /**
     * 
     */
    @Before
    public void setupEntityManager() {
        doOtherSetup();
    }

    /**
     * 
     */
    protected void doOtherSetup() {
        // TODO Auto-generated method stub
    }

    /**
     * 
     */
    @After
    public void removeEntityManager() {
        doOtherTeardown();
    }

    /**
     * 
     */
    protected void doOtherTeardown() {
        // TODO Auto-generated method stub
    }
}
