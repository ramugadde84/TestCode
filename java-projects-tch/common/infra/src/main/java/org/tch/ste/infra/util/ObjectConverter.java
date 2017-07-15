/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * Provides methods to convert an object to and from strings.
 * 
 * @author Karthik.
 * @param <T>
 *            Generic Type.
 * 
 */
public interface ObjectConverter<T> {

    /**
     * Converts to a string representation.
     * 
     * @param obj
     *            Object - The object.
     * @return String - The string representation.
     */
    String stringify(T obj);

    /**
     * Converts the string to an object.
     * 
     * @param str
     *            String - The string value.
     * @param clazz
     *            Class<T> - The class.
     * @return T - The converted object.
     */
    T objectify(String str, Class<T> clazz);
}
