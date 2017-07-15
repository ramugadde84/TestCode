/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * Compares two objects doing null checks as needed.
 * 
 * @author kjanani
 * 
 */
public class CompareUtil {
    /**
     * Private Constructor.
     */
    private CompareUtil() {
        // Empty
    }

    /**
     * Compares the two objects.
     * 
     * @param obj1
     *            Object - The object.
     * @param obj2
     *            Object - The second object.
     * @return boolean - True or False.
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        boolean retVal = false;

        if (obj1 == null) {
            if (obj2 == null) {
                retVal = true;
            }
        } else if (obj2 != null) {
            retVal = obj1.equals(obj2);
        }

        return retVal;
    }
}
