/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.tch.ste.infra.annotation.FixedField;
import org.tch.ste.infra.util.ReflectionUtil;

/**
 * @author Karthik.
 * 
 */
public class FixedLengthFileUtil {

    /**
     * Private Constructor to make it a utility class.
     */
    private FixedLengthFileUtil() {
        // Empty.
    }

    /**
     * Fetches the meta data.
     * 
     * @param clazz
     *            Class<T> - The class.
     * @return List<FixedLengthMetaData> - The metadata.
     */
    public static <T> List<FixedLengthMetaData> getMetadata(Class<T> clazz) {
        List<FixedLengthMetaData> retVal = new ArrayList<FixedLengthMetaData>();
        for (Field field : ReflectionUtil.getAllFields(clazz)) {
            if (field.isAnnotationPresent(FixedField.class)) {
                FixedLengthMetaData meta = new FixedLengthMetaData();
                FixedField fixedField = field.getAnnotation(FixedField.class);
                Class<?> type = fixedField.overrideType();
                meta.setFieldName(field.getName());
                if (Object.class.equals(type)) {
                    meta.setFieldType(field.getType());
                } else {
                    meta.setFieldType(type);
                }
                meta.setLength(fixedField.length());
                meta.setOffset(fixedField.offset());
                retVal.add(meta);
            }
        }
        return retVal;
    }

}
