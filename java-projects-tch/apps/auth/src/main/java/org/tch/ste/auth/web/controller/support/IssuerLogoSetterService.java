/**
 * 
 */
package org.tch.ste.auth.web.controller.support;

import java.io.File;

/**
 * Sets the issuer logo for an issuer - To be used only for development.
 * 
 * @author Karthik.
 *
 */
interface IssuerLogoSetterService {
    /**
     * Sets the issuer logo.
     * 
     * @param iisn String - The iisn.
     * @param file File  - The logo file.
     */
    void setIssuerLogo(String iisn,File file);
}
