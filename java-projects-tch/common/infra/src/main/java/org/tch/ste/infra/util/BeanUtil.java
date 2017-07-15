/**
 * 
 */
package org.tch.ste.infra.util;

import java.lang.reflect.Field;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Simple bean based methods.
 * 
 * @author Karthik.
 * 
 */
public class BeanUtil {

    /**
     * Private Constructor.
     */
    private BeanUtil() {
        // Empty.
    }

    /**
     * Copies the destination object to the source.
     * 
     * @param dest
     *            Object - The destination.
     * @param src
     *            Object - The source.
     */
    public static void copyObject(Object dest, Object src) {
        for (Field field : ReflectionUtil.getAllFields(src)) {
            Object existingValue = ReflectionUtil.getFieldValue(src, field);
            if (!field.isAnnotationPresent(OneToMany.class) && !field.isAnnotationPresent(ManyToOne.class)) {
                ReflectionUtil.setFieldValue(dest, existingValue, field);
            } else if (existingValue != null) {
                ReflectionUtil.setFieldValue(dest, existingValue, field);
            }
        }
    }

}
