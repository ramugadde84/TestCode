/**
 * 
 */
package org.tch.ste.vault.util;

/**
 * Utility class which does stuff on PAN.
 * 
 * @author Karthik.
 * 
 */
public class PanUtil {
    private static final int BIN_LENGTH = 6;
    private static final int LAST_FOUR = 4;
    private static final char ASCII_ZERO = '0';

    /**
     * Private Constructor.
     */
    private PanUtil() {
        // empty
    }

    /**
     * Fetches the bin from the PAN.
     * 
     * @param pan
     *            String - The pan.
     * @return String - The bin.
     */
    public static String getBinFromPan(String pan) {
        return pan.substring(0, BIN_LENGTH);
    }

    /**
     * Fetches the last four digits of the Pan.
     * 
     * @param pan
     *            String - The pan.
     * @return String - the last four digits.
     */
    public static String getLastFourDigits(String pan) {
        int length = pan.length();
        return pan.substring(length - LAST_FOUR, length);
    }

    /**
     * Returns the last digit based on the luhn check algorithm
     * 
     * @param digits
     *            - input string of digits -
     * 
     * @return - integer value
     */
    public static int generateLuhnCheckDigit(String digits) {

        int sum = 0;
        int multiplier = (digits.length() % 2 == 0) ? 1 : 2;
        for (char c : digits.toCharArray()) {
            sum += sumUpDigits((c - ASCII_ZERO) * multiplier);
            multiplier = multiplier % 2 + 1;
        }
        return (10 - sum % 10) % 10;
    }

    /**
     * Sums up the individual digits.
     * 
     * @param value
     *            int - The value.
     * @return int - The summed up value.
     */
    private static int sumUpDigits(int value) {
        int summedValue = value % 10;
        if (value > 9) {
            summedValue += (value / 10);
        }
        return summedValue;
    }

}
