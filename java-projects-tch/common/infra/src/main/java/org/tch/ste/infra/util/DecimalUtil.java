/**
 * 
 */
package org.tch.ste.infra.util;

import java.math.BigDecimal;

/**
 * @author Karthik.
 * 
 */
public class DecimalUtil {

    /**
     * Private Constructor.
     */
    private DecimalUtil() {
        // Empty.
    }

    /**
     * Returns the precision of a number.
     * 
     * @param value
     *            Object - The value.
     * @return String - The string.
     */
    public static String getPrecisionFormat(Object value) {
        String precision = null;
        if (value != null) {
            BigDecimal decValue = new BigDecimal(value.toString());
            Integer actPrec = 0;
            if (decValue.doubleValue() != 0.0) {
                actPrec = decValue.scale();
            }
            precision = "." + actPrec.toString();
        }
        return precision;
    }
}
