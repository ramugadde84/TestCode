/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.util.Formatter;
import java.util.Locale;

/**
 * @author Karthik.
 * 
 */
public abstract class AbstractFormatterImpl implements FixedLengthFormatter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.FixedLengthFormatter#format(java.lang
     * .String, int)
     */
    @Override
    public String format(String value, int length) {
        StringBuilder retVal = new StringBuilder();
        StringBuilder format = new StringBuilder(getFlags());
        format.append(length);
        Object convertedValue = convertValue(value);
        String precision = getPrecision(convertedValue);
        if (precision != null) {
            format.append(precision);
        }
        format.append(getType());
        try (Formatter formatter = new Formatter(retVal, Locale.getDefault())) {
            formatter.format(format.toString(), convertedValue);
        } catch (Throwable t) {
            // Rethrow.
            throw t;
        }
        return retVal.toString();
    }

    /**
     * Fetch the formatting for precision.
     * 
     * @param value
     *            Object - The value.
     * @return String - The precision string or null if it does not apply.
     */
    protected abstract String getPrecision(Object value);

    /**
     * Returns the type of format according to the specifications.
     * 
     * @return String - The type.
     */
    protected abstract String getType();

    /**
     * Returns the flags.
     * 
     * @return String - The flags
     */
    protected abstract String getFlags();

    /**
     * Converts the value into the correct datatype.
     * 
     * @param value
     *            String - The input value.
     * @return Object - The converted value.
     */
    protected abstract Object convertValue(String value);

}
