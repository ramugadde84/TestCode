/**
 * 
 */
package org.tch.ste.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation which maps to a fixed length field.
 * 
 * @author Karthik.
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedField {
    /**
     * The offset.
     * 
     * @return int - The offset.
     */
    int offset();

    /**
     * The length for the field.
     * 
     * @return int - The length.
     */
    int length();

    /**
     * Override Type.
     * 
     * @return Class<?>.
     */
    Class<?> overrideType() default Object.class;
}
