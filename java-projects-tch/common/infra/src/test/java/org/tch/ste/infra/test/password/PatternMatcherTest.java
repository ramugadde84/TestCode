/**
 * 
 */
package org.tch.ste.infra.test.password;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tch.ste.infra.util.PatternMatcherUtil;

/**
 * Tests the pattern matcher util.
 * 
 * @author Karthik.
 * 
 */
public class PatternMatcherTest {

    /**
     * Tests for special characters.
     */
    @Test
    public void test() {
        String numAndSpecialChar = "1$";
        assertTrue(PatternMatcherUtil.containsSpecialCharacter(numAndSpecialChar));
        assertTrue(PatternMatcherUtil.containsDigit(numAndSpecialChar));
        assertFalse(PatternMatcherUtil.containsLowerCaseCharacter(numAndSpecialChar));
        assertFalse(PatternMatcherUtil.containsUpperCaseCharacter(numAndSpecialChar));

        String specialChars = "%$";

        assertTrue(PatternMatcherUtil.containsSpecialCharacter(specialChars));
        assertFalse(PatternMatcherUtil.containsDigit(specialChars));
        assertFalse(PatternMatcherUtil.containsLowerCaseCharacter(specialChars));
        assertFalse(PatternMatcherUtil.containsUpperCaseCharacter(specialChars));

        String noSpecialChars = "aAb123";
        assertFalse(PatternMatcherUtil.containsSpecialCharacter(noSpecialChars));
        assertTrue(PatternMatcherUtil.containsDigit(noSpecialChars));
        assertTrue(PatternMatcherUtil.containsLowerCaseCharacter(noSpecialChars));
        assertTrue(PatternMatcherUtil.containsUpperCaseCharacter(noSpecialChars));
    }

}
