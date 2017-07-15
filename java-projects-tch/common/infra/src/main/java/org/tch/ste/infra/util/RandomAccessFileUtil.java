/**
 * 
 */
package org.tch.ste.infra.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.tch.ste.infra.constant.InfraConstants;

/**
 * Provides methods for randomly accessing stuff from a file.
 * 
 * @author Karthik.
 * 
 */
public class RandomAccessFileUtil {
    /**
     * Private constructor to make this a utility class.
     */
    private RandomAccessFileUtil() {
        // Empty.
    }

    /**
     * Reads the last line from a text file. Assuming that it is UTF-8 and that
     * CR/LF are ascii.
     * 
     * @param file
     *            File - The file.
     * @return String - The last line.
     * @throws IOException
     *             - When file is not found or access is denied.
     */
    public static String readLastLine(File file) throws IOException {
        StringBuilder retVal = new StringBuilder();
        try (RandomAccessFile myFile = new RandomAccessFile(file, "r")) {
            long len = myFile.length();
            for (long i = len - 1; i >= 0; --i) {
                myFile.seek(i);
                int value = myFile.readByte();
                if (value == InfraConstants.CARRIAGE_RETURN || value == InfraConstants.LINE_FEED) {
                    if (i != len - 1 && i != len - 2) {
                        break;
                    }
                } else {
                    retVal.append((char) value);
                }
            }
        }
        return retVal.reverse().toString();
    }

    /**
     * Reads the first line in the file.
     * 
     * @param file
     *            File - The file.
     * @return String - The first line.
     * @throws IOException
     *             - In case we cannot access file.
     * @throws FileNotFoundException
     *             - In case we can't find the file.
     */
    public static String readFirstLine(File file) throws FileNotFoundException, IOException {
        StringBuilder retVal = new StringBuilder();
        try (RandomAccessFile myFile = new RandomAccessFile(file, "r")) {
            for (long i = 0; i < myFile.length(); ++i) {
                int value = myFile.readByte();
                if (value == InfraConstants.CARRIAGE_RETURN || value == InfraConstants.LINE_FEED) {
                    break;
                }
                retVal.append((char) value);
            }
        }
        return retVal.toString();
    }
}
