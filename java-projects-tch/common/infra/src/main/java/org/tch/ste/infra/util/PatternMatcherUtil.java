/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * Methods to do pattern matching on Strings.
 * 
 * @author Karthik.
 * 
 */
public class PatternMatcherUtil {
    /**
     * Constructor to make this a utility class.
     */
    private PatternMatcherUtil() {
        // Empty.
    }

    /**
     * Checks if the provided string has a lower case character.
     * 
     * @param string
     *            String - The string.
     * @return boolean - True if it does.
     */
    public static boolean containsLowerCaseCharacter(String string) {
        return string.matches("(.*)[abcdefghijklmnopqrstuvwxyz](.*)");
    }

    /**
     * Checks if the provided string has an upper case character.
     * 
     * @param string
     *            String - The string.
     * @return boolean - True if it does.
     */
    public static boolean containsUpperCaseCharacter(String string) {
        return string.matches("(.*)[ABCDEFGHIJKLMNOPQRSTUVWXYZ](.*)");
    }

    /**
     * Checks if the provided string has a digit.
     * 
     * @param string
     *            String - The string.
     * @return boolean - True if it does.
     */
    public static boolean containsDigit(String string) {
        return string.matches("(.*)[0123456789](.*)");
    }

    /**
     * Checks if the provided string has a special character.
     * 
     * @param string
     *            String - The string.
     * @return boolean - True if it does.
     */
    public static boolean containsSpecialCharacter(String string) {
        return string.matches("(.*)[*&%$#@!~)(<\\{}\\_?/,;:'\"^-](.*)");
    }

    /**
     * Checks if a string is completely numeric.
     * 
     * @param string
     *            String - The string.
     * @return boolean - True if numeric. False otherwise.
     */
    public static boolean isNumeric(String string) {
        return string.matches("^\\d+$");
    }
  
}
