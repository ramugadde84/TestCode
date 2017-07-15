/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * @param <T>
 *            - The template parameter.
 * 
 */
public class FixedLengthReaderImpl<T> implements FixedLengthReader<T> {

    private static Logger logger = LoggerFactory.getLogger(FixedLengthReaderImpl.class);

    private Class<T> genericClazz;

    /**
     * Constructor.
     * 
     * @param clazz
     *            Class<T> - The class.
     */
    public FixedLengthReaderImpl(Class<T> clazz) {
        this.genericClazz = clazz;
        logger.debug("Created a fixed length reader with type {}", this.genericClazz.getSimpleName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.FixedLengthReader#read(java.util.List,
     * boolean)
     */
    @Override
    public List<T> read(List<String> lines, boolean treatEmptyAsNull) {
        List<T> retVal = new ArrayList<T>();
        List<FixedLengthMetaData> metadata = FixedLengthFileUtil.getMetadata(this.genericClazz);
        for (String line : lines) {
            T object = convertLine(line, metadata, treatEmptyAsNull);
            retVal.add(object);
        }
        return retVal;
    }

    /**
     * Converts the line into an object.
     * 
     * @param line
     *            String - the line which needs to be parsed.
     * @param metadata
     *            List<FixedLengthMetaData> - The metadata.
     * @param treatEmptyAsNull
     *            boolean - Whether to treat an empty value as null.
     * @return T - The object.
     */
    private T convertLine(String line, List<FixedLengthMetaData> metadata, boolean treatEmptyAsNull) {
        T retVal = null;
        try {
            retVal = this.genericClazz.newInstance();
            for (FixedLengthMetaData metadatum : metadata) {
                int offset = metadatum.getOffset();
                int length = metadatum.getLength();
                if (line.length() >= (offset + length)) {
                    String value = parse(line, offset, length, treatEmptyAsNull);
                    BeanUtils.setProperty(retVal, metadatum.getFieldName(), value);
                }
            }
        } catch (InstantiationException e) {
            logger.warn("Unable to instantiate the object", e);
        } catch (IllegalAccessException e) {
            logger.warn("Unable to instantiate the object", e);
        } catch (InvocationTargetException e) {
            logger.warn("Unable to set a value to the target object", e);
        }

        return retVal;
    }

    /**
     * Parse the given string with offset and length.
     * 
     * @param string
     *            String - The string.
     * @param offset
     *            int - The offset.
     * @param length
     *            int - The length.
     * @param treatEmptyAsNull
     *            boolean - Whether to treat an empty value as null.
     * @return String - The substring.
     */
    private static String parse(String string, int offset, int length, boolean treatEmptyAsNull) {
        String retVal = string.substring(offset, offset + length).trim();
        if (treatEmptyAsNull && retVal.isEmpty()) {
            retVal = null;
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.batch.support.FixedLengthReader#readLine(java.lang.
     * String, boolean)
     */
    @Override
    public T readLine(String line, boolean treatEmptyAsNull) {
        List<FixedLengthMetaData> metadata = FixedLengthFileUtil.getMetadata(this.genericClazz);
        return convertLine(line, metadata, treatEmptyAsNull);
    }
}
