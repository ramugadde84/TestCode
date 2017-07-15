/**
 * 
 */
package org.tch.ste.infra.test.batch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tch.ste.infra.batch.support.DoubleFormatterImpl;
import org.tch.ste.infra.batch.support.FixedLengthFormatter;
import org.tch.ste.infra.batch.support.IntegerFormatterImpl;

/**
 * Test case for formatter.
 * 
 * @author Karthik.
 * 
 */
public class TestFormatter {

    /**
     * Integer formatter.
     */
    @Test
    public void testIntegerFormatter() {
        FixedLengthFormatter formatter = new IntegerFormatterImpl();
        String value = "1";
        int length = 2;
        assertEquals(formatter.format(value, length), "01");
        value = "666";
        length = 3;
        assertEquals(formatter.format(value, length), "666");
    }

    /**
     * Double formatter.
     */
    @Test
    public void testDoubleFormatter() {
        FixedLengthFormatter formatter = new DoubleFormatterImpl();
        String value = "19.06";
        int length = 10;
        assertEquals(formatter.format(value, length), "0000019.06");

        value = "0";
        assertEquals(formatter.format(value, length), "0000000000");
    }

}
