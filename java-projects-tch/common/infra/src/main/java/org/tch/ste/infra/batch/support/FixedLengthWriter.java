/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.util.List;

/**
 * Writes a list of objects and converts into a string of lines.
 * 
 * @author Karthik.
 * @param <T>
 *            - The template parameter.
 * 
 */
public interface FixedLengthWriter<T> {
    /**
     * Writes the objects as lines.
     * 
     * @param objects
     *            List<T> - The objects to be written.
     * @return List<String> - The fixed length lines.
     */
    List<String> write(List<T> objects);

    /**
     * Writes the object as a fixed length field.
     * 
     * @param object
     *            T - The object.
     * @return String - The fixed length string.
     */
    String write(T object);
}
