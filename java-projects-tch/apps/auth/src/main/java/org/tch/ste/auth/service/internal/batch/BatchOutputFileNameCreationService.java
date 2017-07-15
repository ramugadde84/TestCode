/**
 * 
 */
package org.tch.ste.auth.service.internal.batch;

/**
 * Exposes methods to create output file names correctly.
 * 
 * @author Ramu.Gadde.
 * 
 */
public interface BatchOutputFileNameCreationService {
    /**
     * Creates a temporary file name.
     * 
     * @param fileType
     *            String - The file type.
     * @param iisn
     *            String - The IISN.
     * @param timeStamp
     *            String - The timestamp.
     * @return String - The temporary file name.
     */
    String createTempOutputFileName(String fileType, String iisn, String timeStamp);

    /**
     * Creates the actual output file name.
     * 
     * @param fileType
     *            String - The file type.
     * @param iisn
     *            String - The iisn.
     * @param timeStamp
     *            String - The timestamp.
     * @return String - The actual output file name.
     */
    String createActualOutputFileName(String fileType, String iisn, String timeStamp);

}