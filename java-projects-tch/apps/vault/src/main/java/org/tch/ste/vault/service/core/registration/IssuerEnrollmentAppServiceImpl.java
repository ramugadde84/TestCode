/**
 * 
 */
package org.tch.ste.vault.service.core.registration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.AuthenticationType;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.configuration.VaultProperties;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.infra.util.UrlUtil;
import org.tch.ste.vault.constant.VaultErrorCode;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.constant.VaultResponseCode;
import org.tch.ste.vault.domain.dto.Errors;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequest;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequestBody;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlResponse;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlResponseBody;
import org.tch.ste.vault.domain.dto.IssuerEnrollmentUrlResponseObj;
import org.tch.ste.vault.domain.dto.MessageHeader;

/**
 * @author anus
 * 
 */
@Service
public class IssuerEnrollmentAppServiceImpl implements IssuerEnrollmentAppService {

    private static Logger logger = LoggerFactory.getLogger(IssuerEnrollmentAppServiceImpl.class);

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    @Autowired
    private VaultProperties vaultProperties;

    @Autowired
    private IssuerEnrollmentValidator issuerEnrollmentValidator;

    /**
     * This method is used to get the issuer enrollment URL
     * 
     * @param request
     *            The request.
     * 
     * @return string - The response.
     */
    @Override
    public IssuerEnrollmentUrlResponseObj getIssuerEnrollmentAppurl(GetIssuerEnrollmentAppUrlRequest request) {
        IssuerEnrollmentUrlResponseObj response = new IssuerEnrollmentUrlResponseObj();
        GetIssuerEnrollmentAppUrlResponse getIssuerEnrollmentAppURLResponse = new GetIssuerEnrollmentAppUrlResponse();
        response.setIssuerEnrollmentUrlResponse(getIssuerEnrollmentAppURLResponse);
        GetIssuerEnrollmentAppUrlResponseBody responseBody = response.getIssuerEnrollmentUrlResponse().getResponseBody();
        Errors errors = new Errors();
        try {
            GetIssuerEnrollmentAppUrlRequestBody requestBody = request.getRequestBody();
            Integer iisn = requestBody.getIisn();
            Integer trId = requestBody.getTrid();
            // FIXME Use the SLF4j style logger below - No need for checking
            // isInfoEnabled.
            logger.info("A GetIssuerEnrollmentAppURL message was received from Token Requestor {} for issuer {}", trId,
                    iisn);
            if (issuerEnrollmentValidator.validate(request)) {
                MessageHeader messageHeader = response.getIssuerEnrollmentUrlResponse().getMessageHeader();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(VaultQueryConstants.PARAM_IISN, iisn.toString());
                IssuerConfiguration issuerConfig = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                        VaultQueryConstants.GET_ISSUER_BY_IISN, params));
                // FIXME Use the SLF4j style logger below - No need for checking
                // isInfoEnabled.
                logger.info("A GetIssuerEnrollmentAppURL response is being sent to Token Requestor {} for issuer {}",
                        trId, iisn);
                StringBuilder url = new StringBuilder();
                int authenticationType = issuerConfig.getAuthenticationType();
                if (AuthenticationType.ISSUER_HOSTED_WITH_PRELOAD.getType() == authenticationType
                        || AuthenticationType.ISSUER_HOSTED_WITH_REALTIME.getType() == authenticationType) {

                    url.append(issuerConfig.getAuthenticationAppUrl());
                } else {
                    url.append(vaultProperties.getKey(MivConstants.VAULT_AUTHENTICATION_APP_URL));

                }
                UrlUtil.appendParam(url, MivConstants.ISSUER_ENROLLMENT_URL_PARAM_IISN, iisn.toString());
                UrlUtil.appendParam(url, MivConstants.ISSUER_ENROLLMENT_URL_PARAM_TRID, trId.toString());
                if (!(StringUtil.isNullOrBlank(request.getRequestBody().getCiid()))) {
                    UrlUtil.appendParam(url, MivConstants.ISSUER_ENROLLMENT_URL_PARAM_CIID, request.getRequestBody()
                            .getCiid());
                }
                responseBody.setIssuerUrl(url.toString());

                messageHeader.setCloudId(vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_CLOUD_SWITCH_ID));
                messageHeader.setSourceId(vaultProperties.getKey(MivConstants.VAULT_CONFIGURATION_VAULT_SWITCH_ID));
                responseBody.setResponseCode(VaultResponseCode.SUCCESS_RESPONSE_CODE.getResponseStr());
                response.getIssuerEnrollmentUrlResponse().setResponseBody(responseBody);
                response.getIssuerEnrollmentUrlResponse().setMessageHeader(messageHeader);
            } else {
                String responseCode = VaultErrorCode.INPUT_PARAMETER_VALUE_IS_INVALID.getResponseStr();
                errors.setErrorCode(responseCode);
                responseBody.setErrorlist(errors);
                responseBody.setResponseCode(VaultResponseCode.FAILURE_RESPONSE_CODE.getResponseStr());
            }
        } catch (Throwable t) {
            String responseCode = VaultErrorCode.ERROR_OCCURED_AT_VAULT_WHEN_PROCESSING_THE_REQUEST.getResponseStr();
            errors.setErrorCode(responseCode);
            responseBody.setErrorlist(errors);
            responseBody.setResponseCode(VaultResponseCode.FAILURE_RESPONSE_CODE.getResponseStr());
        }
        return response;
    }

}
