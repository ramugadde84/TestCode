/**
 * 
 */
package org.tch.ste.vault.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility with methods for ease of batch file reading.
 * 
 * 
 * @author Karthik.
 * 
 */
public class BatchFileReaderUtil {
    /**
     * Utility Class - So private constructor.
     */
    private BatchFileReaderUtil() {
        // Empty.
    }

    /**
     * Counts the number of lines in a file.
     * 
     * @param file
     *            File - The file.
     * @return long - The number of lines.
     * @throws IOException
     *             - Thrown.
     * @throws FileNotFoundException
     *             - Thrown.
     */
    public static int countLines(File file) throws FileNotFoundException, IOException {
        int count = 0;
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            byte[] c = new byte[1024];

            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
        }
        return count;
    }
}
