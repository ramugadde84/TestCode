/**
 * 
 */
package org.tch.ste.vault.service.core.registration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.PatternMatcherUtil;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequest;
import org.tch.ste.vault.domain.dto.GetIssuerEnrollmentAppUrlRequestBody;
import org.tch.ste.vault.domain.dto.MessageHeader;

/**
 * @author anus
 * 
 */
@Service
public class IssuerEnrollmentValidatorImpl implements IssuerEnrollmentValidator {

    @Autowired
    @Qualifier("tokenRequestorConfigurationDao")
    private JpaDao<TokenRequestorConfiguration, Integer> tokenRequestorConfigurationDao;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors)
     */
    /**
     * This method is used to validate the token requestor,issn and check for
     * madatory fields
     * 
     * @param request
     *            GetIssuerEnrollmentAppUrlRequest - The request.
     */
    @Override
    public boolean validate(GetIssuerEnrollmentAppUrlRequest request) {
        boolean successStatus = false;
        if (validateTokenRequestor(request) && validateIisn(request) && validateMandatoryFields(request)
                && validateSpecialCharacters(request)) {
            successStatus = true;
        }
        return successStatus;
    }

    /**
     * This method is used to validate the madatory fields
     * 
     * @param request
     */
    private static boolean validateMandatoryFields(GetIssuerEnrollmentAppUrlRequest request) {
        boolean successStatus = false;
        MessageHeader messageHeader = request.getMessageHeader();
        GetIssuerEnrollmentAppUrlRequestBody requestBody = request.getRequestBody();
        if (!(StringUtil.isNullOrBlank(messageHeader.getCloudId()))
                && !(StringUtil.isNullOrBlank(messageHeader.getMessageId()))
                && !(StringUtil.isNullOrBlank(messageHeader.getSourceId()))
                && !(StringUtil.isNullOrBlank(messageHeader.getVersion()))
                && !(StringUtil.isNullOrBlank(String.valueOf(requestBody.getIisn())))
                && !(StringUtil.isNullOrBlank(String.valueOf(requestBody.getTrid())))) {
            successStatus = true;
        }
        return successStatus;
    }

    /**
     * This method is used to check whether the iisn is valid.
     * 
     * @param request
     * @param errors
     */
    private boolean validateIisn(GetIssuerEnrollmentAppUrlRequest request) {
        boolean successStatus = false;
        Integer iisn = request.getRequestBody().getIisn();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultConstants.ISSUER_IISN, String.valueOf(iisn));
        IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                VaultConstants.GET_ISSUERS_BY_IISN, params));
        if (null != issuerConfiguration) {
            successStatus = true;
        }
        return successStatus;
    }

    /**
     * This method is used to validate the token requestor.
     * 
     * @param request
     * @param errors
     */
    private boolean validateTokenRequestor(GetIssuerEnrollmentAppUrlRequest request) {
        Integer trId = request.getRequestBody().getTrid();
        boolean successStatus = false;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_NETWORK_TR_ID, String.valueOf(trId));
        TokenRequestorConfiguration tokenRequestorConfiguration = ListUtil
                .getFirstOrNull(tokenRequestorConfigurationDao.findByName(
                        VaultQueryConstants.GET_TOKEN_REQUESTOR_BASED_ON_NETWORK_ID, params));
        if (null != tokenRequestorConfiguration) {
            successStatus = true;
        }
        return successStatus;
    }

    /**
     * This method is used to validate special characters
     * 
     * @param request
     */
    private static boolean validateSpecialCharacters(GetIssuerEnrollmentAppUrlRequest request) {
        boolean successStatus = false;
        MessageHeader messageHeader = request.getMessageHeader();
        GetIssuerEnrollmentAppUrlRequestBody requestBody = request.getRequestBody();
        if (!(PatternMatcherUtil.containsSpecialCharacter(messageHeader.getCloudId()))
                && !(PatternMatcherUtil.containsSpecialCharacter(messageHeader.getMessageId()))
                && !(PatternMatcherUtil.containsSpecialCharacter(messageHeader.getSourceId()))
                && !(PatternMatcherUtil.containsSpecialCharacter(messageHeader.getVersion()))
                && !(PatternMatcherUtil.containsSpecialCharacter(String.valueOf(requestBody.getIisn())))
                && !(PatternMatcherUtil.containsSpecialCharacter(String.valueOf(requestBody.getTrid())))
                && !(PatternMatcherUtil.containsSpecialCharacter(requestBody.getCiid() != null ? requestBody.getCiid()
                        : ""))) {
            successStatus = true;
        }
        return successStatus;
    }
}
