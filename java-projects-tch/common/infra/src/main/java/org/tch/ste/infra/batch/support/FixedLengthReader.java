/**
 * 
 */
package org.tch.ste.infra.batch.support;

import java.util.List;

/**
 * Reads the data from a fixed file and returns the sequence of records as an
 * object.
 * 
 * @author Karthik.
 * @param <T>
 *            - The template parameter.
 * 
 */
public interface FixedLengthReader<T> {
    /**
     * Reads all records from a file and returns as a list of items.
     * 
     * @param lines
     *            List<String> - The lines which need to be read.
     * @param treatEmptyAsNull
     *            boolean - Whether an empty value is to be treated as null.
     * @return List<T> - The elements of the file.
     */
    List<T> read(List<String> lines, boolean treatEmptyAsNull);

    /**
     * Reads the line.
     * 
     * @param line
     *            String - The line.
     * @param treatEmptyAsNull
     *            boolean - Whether an empty value is to be treated as null.
     * @return T - The object.
     */
    T readLine(String line, boolean treatEmptyAsNull);
}
