/**
 * 
 */
package org.tch.ste.auth.test.util;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * @author anus
 *
 */
public class AuthTestHelperUtil {
    /**
     * Private constructor.
     */
    private AuthTestHelperUtil() {
        // Private Constructor.
    }

    /**
     * Creates the directories.
     * 
     * @param directories
     *            String [] - Directories to create.
     */
    public static void createDirectories(String[] directories) {
        for (String directory : directories) {
            File dirFile = new File(directory);
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                throw new RuntimeException("Unable to create directory:" + dirFile.getAbsolutePath());
            }
        }
    }

    /**
     * Deletes files in directories.
     * 
     * @param directories
     *            - The directories.
     */
    public static void deleteFilesInDirectories(String[] directories) {
        for (String directory : directories) {
            for (Object file : FileUtils.listFiles(new File(directory), null, false)) {
                if (file instanceof File) {
                    File myFile = (File) file;
                    if (!myFile.delete()) {
                        System.out.println("Unable to delete file: " + myFile.getAbsolutePath());
                    }
                }
            }
        }
    }
}
