/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * @param <T>
 *            - The template parameter.
 * 
 */
public class FixedLengthWriterImpl<T> implements FixedLengthWriter<T>, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(FixedLengthWriterImpl.class);

    private static final String formatterName = "Formatter";

    private Class<T> genericClazz;

    private ApplicationContext ctx;

    private int paddingLength = 0;

    /**
     * Overloaded constructor.
     * 
     * @param genericClazz
     *            Class<T> - The generic class.
     * @param paddingLength
     *            int - The length of the padding at the end of the record.
     * 
     */
    public FixedLengthWriterImpl(Class<T> genericClazz, int paddingLength) {
        this.genericClazz = genericClazz;
        this.paddingLength = paddingLength;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.FixedLengthWriter#write(java.util.List)
     */
    @Override
    public List<String> write(List<T> objects) {
        List<String> retVal = new ArrayList<String>();
        List<FixedLengthMetaData> metaData = FixedLengthFileUtil.getMetadata(genericClazz);
        Collections.sort(metaData);
        for (T object : objects) {
            retVal.add(convertToString(object, metaData));
        }
        return retVal;
    }

    /**
     * Converts the object into a fixed format string.
     * 
     * @param object
     *            T - The object to be converted.
     * @param metaData
     *            List<FixedLengthMetaData> - The metadata.
     * @return String - The fixed format string.
     */
    private String convertToString(T object, List<FixedLengthMetaData> metaData) {
        StringBuilder retVal = new StringBuilder();
        for (FixedLengthMetaData metaDatum : metaData) {
            String fieldName = metaDatum.getFieldName();
            try {
                String value = BeanUtils.getProperty(object, fieldName);
                retVal.append(padValue(value, metaDatum.getFieldType(), metaDatum.getLength()));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.warn("Error while fetching value {}", fieldName, e);
            }
        }
        if (paddingLength > 0) {
            retVal.append(padValue("", String.class, paddingLength));
        }
        return retVal.toString();
    }

    /**
     * Pads the value with spaces/0s based on type.
     * 
     * @param value
     *            String - The value.
     * @param fieldType
     *            Class<?> - The field type.
     * @param length
     *            - The length.
     * @return String - The padded value.
     */
    private String padValue(String value, Class<?> fieldType, int length) {
        return ctx.getBean(fieldType.getSimpleName() + formatterName, FixedLengthFormatter.class).format(value, length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.FixedLengthWriter#write(java.lang.Object)
     */
    @Override
    public String write(T object) {
        List<FixedLengthMetaData> metaData = FixedLengthFileUtil.getMetadata(genericClazz);
        return convertToString(object, metaData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
