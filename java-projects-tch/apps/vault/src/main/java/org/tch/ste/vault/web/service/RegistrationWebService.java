/**
 * 
 */
package org.tch.ste.vault.web.service;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_ISSUER_ENROLLMENT_APP_URL;
import static org.tch.ste.vault.constant.VaultControllerConstants.C_REGISTRATION_SERVICE;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.vault.constant.VaultErrorCode;
import org.tch.ste.vault.constant.VaultResponseCode;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequest;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequestBody;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlResponse;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlResponseBody;
import org.tch.ste.vault.domain.dto.IssuerEnrollmentUrlRequestObj;
import org.tch.ste.vault.domain.dto.IssuerEnrollmentUrlResponseObj;
import org.tch.ste.vault.domain.dto.MessageHeader;
import org.tch.ste.vault.service.core.registration.IssuerEnrollmentAppService;
import org.tch.ste.vault.util.ErrorMessageUtil;

/**
 * @author anus
 * 
 */
@RestController
@RequestMapping(C_REGISTRATION_SERVICE)
public class RegistrationWebService implements MessageSourceAware {

    private static Logger logger = LoggerFactory.getLogger(RegistrationWebService.class);

    @Autowired
    private IssuerEnrollmentAppService issuerEnrollmentAppService;

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<IssuerEnrollmentUrlRequestObj> requestConverter;

    private MessageSource messageSource;

    @Autowired
    private VaultProperties vaultProperties;

    /**
     * This method is used to get the issuer enrollment URL.
     * 
     * @return response
     * @param request
     *            request - The request.
     * 
     * @throws InvocationTargetException
     *             - Thrown.
     * @throws IllegalAccessException
     *             - Thrown.
     */
    @RequestMapping(value = C_ISSUER_ENROLLMENT_APP_URL)
    public IssuerEnrollmentUrlResponseObj getIssuerEnrollmentAppURL(HttpServletRequest request)
            throws IllegalAccessException, InvocationTargetException {
        IssuerEnrollmentUrlResponseObj response = null;
        try {
            IssuerEnrollmentUrlRequestObj req = requestConverter.objectify(IOUtils.toString(request.getInputStream()),
                    IssuerEnrollmentUrlRequestObj.class);
            GetIssuerEnrollmentAppUrlRequest actReq = req.getIssuerEnrollmentUrlRequest();
            response = issuerEnrollmentAppService.getIssuerEnrollmentAppurl(actReq);
            GetIssuerEnrollmentAppUrlResponseBody responseBody = response.getIssuerEnrollmentUrlResponse()
                    .getResponseBody();
            if (responseBody.getErrorlist() != null) {
                responseBody.setErrorlist(ErrorMessageUtil.getErrors(responseBody.getErrorlist().getErrorCode(),
                        messageSource));
            }
            copyRequestValues(actReq, response);
        } catch (Throwable t) {
            logger.warn("ERROR_OCCURED_AT_VAULT_WHEN_PROCESSING_THE_REQUEST", t);
            response = createInvalidJsonResponse();
        }
        return response;
    }

    /**
     * This method is used to set the errormessage for invalid Json response
     * 
     * @return
     */
    private IssuerEnrollmentUrlResponseObj createInvalidJsonResponse() {
        IssuerEnrollmentUrlResponseObj response = new IssuerEnrollmentUrlResponseObj();
        GetIssuerEnrollmentAppUrlResponse getIssuerEnrollmentAppURLResponse = new GetIssuerEnrollmentAppUrlResponse();
        response.setIssuerEnrollmentUrlResponse(getIssuerEnrollmentAppURLResponse);
        GetIssuerEnrollmentAppUrlResponseBody responseBody = response.getIssuerEnrollmentUrlResponse().getResponseBody();
        String responseCode = VaultErrorCode.ERROR_OCCURED_AT_VAULT_WHEN_PROCESSING_THE_REQUEST.getResponseStr();
        responseBody.setErrorlist(ErrorMessageUtil.getErrors(responseCode, messageSource));
        responseBody.setResponseCode(VaultResponseCode.FAILURE_RESPONSE_CODE.getResponseStr());
        return response;
    }

    /**
     * This method is used to copy the request values to response object.
     * 
     * @param getIssuerEnrollmentAppUrlRequest
     * @param getIssuerEnrollmentAppUrlResponse
     */
    private IssuerEnrollmentUrlResponseObj copyRequestValues(GetIssuerEnrollmentAppUrlRequest request,
            IssuerEnrollmentUrlResponseObj response) {
        GetIssuerEnrollmentAppUrlResponseBody responseBody = response.getIssuerEnrollmentUrlResponse()
                .getResponseBody();
        GetIssuerEnrollmentAppUrlRequestBody requestBody = request.getRequestBody();
        MessageHeader messageHeader = response.getIssuerEnrollmentUrlResponse().getMessageHeader();
        responseBody.setCiid(requestBody.getCiid());

        responseBody.setTrid(requestBody.getTrid());
        responseBody.setIisn(requestBody.getIisn());

        messageHeader.setMessageId(request.getMessageHeader().getMessageId());
        messageHeader.setVersion(request.getMessageHeader().getVersion());
        messageHeader.setCloudId(vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_CLOUD_SWITCH_ID));
        messageHeader.setSourceId(vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_SOURCE_ID));

        return response;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
     * springframework.context.MessageSource)
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;

    }
}
