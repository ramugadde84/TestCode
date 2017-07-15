/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * A helper class to generate alphabets, numbers etc.
 * 
 * @author Karthik.
 * 
 */
public class AlphaNumericGenerator {

    private static final int NUM_ALPHABETS = 26;
    private static final int NUM_DIGITS = 10;

    private static final char[] specialChars = new char[] { '@', '$', '#', '!', '*', '%', '&' };

    /**
     * Private constructor to make this a utility class.
     */
    private AlphaNumericGenerator() {
        // Empty.
    }

    /**
     * Fetches lower case alphabets.
     * 
     * @return char[] - Alphabets in lower case.
     */
    public static char[] getLowerCaseAlphabets() {
        char[] retVal = new char[NUM_ALPHABETS];
        int i = 0;
        for (char c = 'a'; c <= 'z'; ++c, ++i) {
            retVal[i] = c;
        }
        return retVal;
    }

    /**
     * Fetches upper case alphabets.
     * 
     * @return char[] - Alphabets in upper case.
     */
    public static char[] getUpperCaseAlphabets() {
        char[] retVal = new char[NUM_ALPHABETS];
        int i = 0;
        for (char c = 'A'; c <= 'Z'; ++c, ++i) {
            retVal[i] = c;
        }
        return retVal;
    }

    /**
     * Fetches digits from 0-9.
     * 
     * @return char[] - Digits from 0-9.
     */
    public static char[] getDigits() {
        char[] retVal = new char[NUM_DIGITS];
        int i = 0;
        for (char c = '0'; c <= '9'; ++c, ++i) {
            retVal[i] = c;
        }
        return retVal;
    }

    /**
     * Returns allowed special characters.
     * 
     * @return char[] - Allowed special characters.
     */
    public static char[] getSpecialChars() {
        char[] retVal = new char[specialChars.length];
        for (int i = 0; i < specialChars.length; ++i) {
            retVal[i] = specialChars[i];
        }
        return retVal;
    }

}
