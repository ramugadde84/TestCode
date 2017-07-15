/**
 * 
 */
package org.tch.ste.vault.util;

import java.io.File;
import java.util.List;

/**
 * An interface which provides methods used by all batch processes. The provided
 * methods include the following: - Convert the file into a list of lines. -
 * Read & Validate the header record. - Read & Validate the trailer record.
 * 
 * @author Karthik.
 * 
 */
public interface BatchReader {
    /**
     * Reads the file and returns the lines that make up the file.
     * 
     * @param file
     *            File - the file.
     * @return List<String> - The lines read.
     */
    List<String> readLines(File file);

}
