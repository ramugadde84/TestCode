/**
 * 
 */
package org.tch.ste.infra.test.batch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tch.ste.infra.util.DateUtil;

/**
 * Tests the Date Util.
 * 
 * @author Karthik.
 * 
 */
public class TestDateUtil {

    /**
     * Test for the date util valid Month Year.
     */
    @Test
    public void testValidMonthYear() {
        assertTrue(DateUtil.isValidMonthYear("0425"));
        assertFalse(DateUtil.isValidMonthYear("1425"));
    }

}
