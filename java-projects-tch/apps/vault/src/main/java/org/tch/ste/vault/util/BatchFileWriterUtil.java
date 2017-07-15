/**
 * 
 */
package org.tch.ste.vault.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Writes lines to a file.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileWriterUtil {

    /**
     * Private Constructor.
     */
    private BatchFileWriterUtil() {
        // Empty
    }

    /**
     * Writes a line to a file.
     * 
     * @param file
     *            File - The file.
     * @param line
     *            String - The line.
     * @throws IOException
     *             Thrown.
     */
    public static void writeLine(File file, String line) throws IOException {
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), true),
                "UTF-8")) {
            out.append(String.format("%s\r\n", line));
        }
    }

    /**
     * Writes the lines to the file in append mode.
     * 
     * @param file
     *            File - The file.
     * @param lines
     *            List<String> - The lines.
     * @throws IOException
     *             Thrown.
     * @throws FileNotFoundException
     *             Thrown.
     * @throws UnsupportedEncodingException
     *             Thrown.
     */
    public static void writeLines(File file, List<String> lines) throws UnsupportedEncodingException,
            FileNotFoundException, IOException {
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), true),
                "UTF-8")) {
            for (String line : lines) {
                out.append(String.format("%s\r\n", line));
            }
        }
    }
}
