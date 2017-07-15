/**
 * 
 */
package org.tch.ste.vault.service.core.registration;

import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequest;

/**
 * @author anus
 * 
 */
public interface IssuerEnrollmentValidator {
    /**
     * 
     * @param request
     * @return boolean
     */
    boolean validate(GetIssuerEnrollmentAppUrlRequest request);
}
