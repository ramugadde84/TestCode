package org.tch.ste.infra.util;

/**
 * TODO: Document Me
 * 
 * @author: Ganeshji Marwaha
 * @since: 5/16/14
 */
public class FinanceUtil {

    /**
     * 
     */
    public static final int[] delta = { 0, 1, 2, 3, 4, -4, -3, -2, -1, 0 };

    /**
     * @param card
     *            String.
     * @return String.
     */
    public static String calculateLuhnDigit1(String card) {
        int sum = 0;
        for (int i = 0; i < card.length(); i++) {
            sum += card.charAt(i) - '0';
        }

        int deltaIndex = 0;
        int deltaValue = 0;
        for (int i = card.length() - 1; i >= 0; i -= 2) {
            deltaIndex = card.charAt(i) - '0';
            deltaValue = delta[deltaIndex];
            sum += deltaValue;
        }
        int mod10 = sum % 10;
        mod10 = 10 - mod10;

        if (mod10 == 10) {
            mod10 = 0;
        }
        return String.valueOf(mod10);
    }

    /**
     * @param cardNumber
     *            String.
     * @return String.
     */
    public static String calculateLuhnDigit2(String cardNumber) {
        int sum = 0;
        for (int i = cardNumber.length() - 1, even = 0; i >= 0; i--, even++) {
            int digit = cardNumber.charAt(i) - '0';
            if (even % 2 == 0) {
                if ((digit *= 2) > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
        }
        return String.valueOf((sum * 9) % 10);
    }

    /**
     * @param cardNumber
     *            String.
     * @return String.
     */
    public static String calculateLuhnDigit3(String cardNumber) {

        int sum = 0;
        boolean isAlternate = true;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = cardNumber.charAt(i) - '0';
            if (isAlternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            isAlternate = !isAlternate;
            sum += digit;
        }
        int checkDigit = 10 - sum % 10;
        return String.valueOf(checkDigit == 10 ? 0 : checkDigit);
    }

    /**
     * @param args
     *            String[].
     */
    public static void main(String[] args) {
        String cardNumbers[] = { "123456781234560", "123456781234561", "123456781234562", "123456781234563",
                "123456781234564", "123456781234565", "123456781234566", "123456781234567", "123456781234568", };

        for (String cardNumber : cardNumbers) {
            System.out.println("1: " + calculateLuhnDigit1(cardNumber));
            System.out.println("2: " + calculateLuhnDigit2(cardNumber));
        }

        // long start = System.currentTimeMillis();
        // for(int i = 0; i <1000000; i++) {
        // calculateLuhnDigit3("123456781234566");
        // }
        // long stop = System.currentTimeMillis();
        //
        // System.out.println("Time: " + (stop-start));

    }
}
