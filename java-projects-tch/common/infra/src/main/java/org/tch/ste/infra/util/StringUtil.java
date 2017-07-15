/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * A string utility class.
 * 
 * @author Karthik.
 * 
 */
public class StringUtil {

    /*
     * Private Constructor.
     */
    private StringUtil() {
        // Empty
    }

    /**
     * Checks if a string is null or blank.
     * 
     * @param str
     *            String - The string.
     * @return boolean - True if the string is null or blank.
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Strips the leading zeros from a string.
     * 
     * @param str
     *            String - The string.
     * @return String - The stripped value.
     */
    public static String stripLeadingZeros(String str) {
        return (str!=null)?str.replaceFirst("^0+(?!$)", ""):null;
    }

    /**
     * If the given string is null or whitespace, returns an empty string.
     * 
     * @param string
     *            String - The issuer customer id.
     * @return String - The output.
     */
    public static String convertNullToBlank(String string) {
        String retVal = string;
        if (string == null || string.trim().length() == 0) {
            retVal = "";
        }
        return retVal;
    }
}
