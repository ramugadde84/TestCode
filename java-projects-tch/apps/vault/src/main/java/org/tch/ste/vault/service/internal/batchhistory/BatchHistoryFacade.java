/**
 * 
 */
package org.tch.ste.vault.service.internal.batchhistory;

/**
 * Exposes methods for processing and fetching Batch Histories.
 * 
 * @author Karthik.
 * 
 */
public interface BatchHistoryFacade {
    /**
     * Returns if a given batch file has already been processed.
     * 
     * @param iisn
     *            String - The iisn.
     * @param timeStamp
     *            String - The timestamp on the file.
     * @param batchFileType
     *            String - The type on the file.
     * @return boolean - True if the file has already been processed.
     */
    boolean hasProcessed(String iisn, String timeStamp, String batchFileType);

    /**
     * Starts the processing of a batch file.
     * 
     * @param iisn
     *            String - The iisn.
     * @param timeStamp
     *            String - The timestamp on the file.
     * @param batchFileType
     *            String - The type on the file.
     */
    void processingStart(String iisn, String timeStamp, String batchFileType);

    /**
     * Marks the processing of a batch file as done.
     * 
     * @param iisn
     *            String - The iisn.
     * @param timeStamp
     *            String - The timestamp on the file.
     * @param batchFileType
     *            String - The type on the file.
     * @param recordCount
     *            int - The number of records processed.
     * @param success
     *            boolean - If the batch was successful.
     */
    void processingEnd(String iisn, String timeStamp, String batchFileType, int recordCount, boolean success);

}
