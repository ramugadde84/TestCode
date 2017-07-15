/**
 * 
 */
package org.tch.ste.auth.service.internal.authattempts;

/**
 * @author anus
 *
 */
public interface AuthAttemptsService {

    /**
     * @param iisn
     */
    void generateAuthAttemptsReport(String iisn);

}
