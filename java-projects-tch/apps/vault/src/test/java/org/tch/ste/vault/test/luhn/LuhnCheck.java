/**
 * 
 */
package org.tch.ste.vault.test.luhn;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tch.ste.vault.util.PanUtil;

/**
 * @author kjanani
 * 
 */
public class LuhnCheck {

    /**
     * Test for luhn check.
     */
    @Test
    public void test() {

        assertEquals(PanUtil.generateLuhnCheckDigit("9992739871"), 1);
        assertEquals(PanUtil.generateLuhnCheckDigit("8992739871"), 2);
        assertEquals(PanUtil.generateLuhnCheckDigit("7992739871"), 3);
        assertEquals(PanUtil.generateLuhnCheckDigit("6992739871"), 4);
        assertEquals(PanUtil.generateLuhnCheckDigit("5992739871"), 5);
        assertEquals(PanUtil.generateLuhnCheckDigit("4992739871"), 6);
        assertEquals(PanUtil.generateLuhnCheckDigit("3992739871"), 7);
        assertEquals(PanUtil.generateLuhnCheckDigit("2992739871"), 8);
        assertEquals(PanUtil.generateLuhnCheckDigit("1992739871"), 9);
        assertEquals(PanUtil.generateLuhnCheckDigit("0992739871"), 0);
        assertEquals(PanUtil.generateLuhnCheckDigit("316512613183197"), 4);
    }

}
