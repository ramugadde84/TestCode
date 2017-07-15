/**
 * 
 */
package org.tch.ste.infra.util;

import java.util.List;

/**
 * @author Karthik.
 * 
 */
public class ListUtil {

    /**
     * Private Constructor to make this a utility class.
     */
    private ListUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Fetches the first value of a list or null if the list is empty.
     * 
     * @param <T>
     *            - The generic parameter.
     * @param values
     *            List<T> - The values.
     * @return T - The value.
     */
    public static <T> T getFirstOrNull(List<T> values) {
        return (!values.isEmpty()) ? values.get(0) : null;
    }

}
