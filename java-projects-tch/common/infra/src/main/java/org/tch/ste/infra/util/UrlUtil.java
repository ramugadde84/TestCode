/**
 * 
 */
package org.tch.ste.infra.util;

/**
 * Does methods which work with Url.
 * 
 * @author Karthik.
 * 
 */
public class UrlUtil {
    /**
     * Private Constructor for Utility Class.
     */
    private UrlUtil() {
        // Empty
    }

    /**
     * Appends the url parameters.
     * 
     * @param url
     *            StringBuilder - The url.
     * @param paramName
     *            String - The name.
     * @param paramValue
     *            String - The value.
     */
    public static void appendParam(StringBuilder url, String paramName, String paramValue) {
        if (url.indexOf("?") != -1) {
            url.append('&');
        } else {
            url.append('?');
        }
        url.append(paramName);
        url.append('=');
        url.append(paramValue);
    }
}
