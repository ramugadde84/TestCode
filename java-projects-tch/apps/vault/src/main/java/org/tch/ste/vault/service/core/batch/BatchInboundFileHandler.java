/**
 * 
 */
package org.tch.ste.vault.service.core.batch;

import java.io.File;

/**
 * Exposes methods to handle inbound files according to use case 5.
 * 
 * @author Karthik.
 * 
 */
public interface BatchInboundFileHandler {
    /**
     * Handles an inbound file.
     * 
     * @param file
     *            - The file.
     */
    void handle(File file);
}
