/**
 * 
 */
package org.tch.ste.vault.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.tch.ste.vault.constant.VaultConstants;

/**
 * Util to manipulate files for batch processing.
 * 
 * @author Karthik.
 * 
 */
public class BatchFileUtil {

    /**
     * Private Constructor.
     */
    private BatchFileUtil() {
        // Empty.
    }

    /**
     * Moves the file to the processed directory.
     * 
     * @param file
     *            File - The file.
     * 
     * @return File - The moved file.
     * @throws IOException
     *             Exception - Thrown when file moving fails.
     */
    public static File moveToProcessedDir(File file) throws IOException {
        return BatchFileUtil.moveFileToSiblingDir(file, VaultConstants.PROCESSED_DIR);
    }

    /**
     * Moves the file to the error directory.
     * 
     * @param file
     *            File - The File.
     * @return File - The moved file.
     * @throws IOException
     *             Exception thrown when movement fails.
     */
    public static File moveToErrorDir(File file) throws IOException {
        return BatchFileUtil.moveFileToSiblingDir(file, VaultConstants.ERROR_DIR);
    }

    /**
     * Moves the file to the in-process directory.
     * 
     * @param file
     *            File - The file.
     * @return File - The moved file.
     * @throws IOException
     *             Exception - Thrown when the process is unable to move the
     *             file.
     */
    public static File moveToInProcessDir(File file) throws IOException {
        return BatchFileUtil.moveFileToSiblingDir(file, VaultConstants.INPROCESS_DIR);
    }

    /**
     * This moves the file to a sibling directory.
     * 
     * @param file
     *            File - The file.
     * @param siblingDir
     *            String - The directory to which it needs to be moved.
     * @return File - The moved file.
     * @throws IOException
     *             Exception - When movement fails.
     */
    private static File moveFileToSiblingDir(File file, String siblingDir) throws IOException {
        File newDir = new File(file.getParentFile().getParent(), siblingDir);
        if (!newDir.exists() && !newDir.mkdirs()) {
            throw new IOException("Unable to create directory with path" + newDir.getAbsolutePath());
        }
        File newFile = new File(newDir.getAbsolutePath(), file.getName());
        if (newFile.exists() && !newFile.delete()) {
            throw new IOException("Unable to delete existing file with path" + newFile.getAbsolutePath());
        }
        FileUtils.moveFile(file, newFile);
        return newFile;
    }

    /**
     * Create an error file and copy it to the output directory.
     * 
     * @param file
     *            File - The file.
     * @throws IOException
     *             - When unable to copy a file.
     */
    public static void copyErrorFileToOutputDir(File file) throws IOException {
        String errorFileName = FilenameUtils.concat(
                FilenameUtils.concat(file.getParentFile().getParent(), VaultConstants.OUTBOUND_DIR), file.getName()
                        + VaultConstants.ERROR_FILE_APPEND_STR);
        FileUtils.copyFile(file, new File(errorFileName));
    }

    /**
     * Creates the batch file name from its component parts.
     * 
     * @param fileType
     *            String - The file type.
     * @param iisn
     *            String - The IISN.
     * @param timeStamp
     *            String - The timestamp.
     * @return String - The filename.
     */
    public static String createFileName(String fileType, String iisn, String timeStamp) {
        StringBuilder retVal = new StringBuilder(fileType);
        retVal.append(VaultConstants.FILE_PART_SEPERATOR);
        retVal.append(iisn);
        retVal.append(VaultConstants.FILE_PART_SEPERATOR);
        retVal.append(timeStamp);
        return retVal.toString();
    }

}
