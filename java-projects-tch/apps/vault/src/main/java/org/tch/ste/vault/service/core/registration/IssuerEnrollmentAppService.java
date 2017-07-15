/**
 * 
 */
package org.tch.ste.vault.service.core.registration;

import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequest;
import org.tch.ste.vault.domain.dto.IssuerEnrollmentUrlResponseObj;

/**
 * @author anus
 * 
 */
public interface IssuerEnrollmentAppService {
    /**
     * @param request
     * @param errors
     * @return GetIssuerEnrollmentAppUrlResponse
     */
    IssuerEnrollmentUrlResponseObj getIssuerEnrollmentAppurl(GetIssuerEnrollmentAppUrlRequest request);

}
