/**
 * 
 */
package org.tch.ste.infra.batch.support;

/**
 * Interface with methods to do formatting.
 * 
 * @author Karthik.
 * 
 */
public interface FixedLengthFormatter {
    /**
     * Formats the given object.
     * 
     * @param value
     *            String - The object.
     * @param length
     *            int - The length.
     * @return String - The formatted value.
     */
    String format(String value, int length);
}
