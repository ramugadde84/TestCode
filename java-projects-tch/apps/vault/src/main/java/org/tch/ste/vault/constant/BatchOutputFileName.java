/**
 * 
 */
package org.tch.ste.vault.constant;

/**
 * The names of the output files.
 * 
 * @author Karthik.
 * 
 */
public enum BatchOutputFileName {
    /**
     * Daily password generation output file.
     */
    CREDUPDATEDAILY,
    /**
     * Confirmation file for payment instrument preload.
     */
    PYMNTAUTHCREATECONF,
    /**
     * Confirmation file for Lock or Unlock customer accounts.
     */
    SETACCOUNTLOCKCONF,
    /**
     * Daily Token User Report
     */
    TOKENUSEREPORTDAILY;

}
