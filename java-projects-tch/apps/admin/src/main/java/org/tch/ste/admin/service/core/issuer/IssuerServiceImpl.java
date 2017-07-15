package org.tch.ste.admin.service.core.issuer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.dto.Issuer;
import org.tch.ste.admin.domain.dto.IssuerDetail;
import org.tch.ste.domain.entity.AuthenticationMechanism;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.domain.entity.IssuerTokenRequestors;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implementation of issuer service
 * 
 * @author janarthanans
 * 
 */
@Service
public class IssuerServiceImpl implements IssuerService {

    private static Logger logger = LoggerFactory.getLogger(IssuerServiceImpl.class);

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    @Autowired
    @Qualifier("issuerTokenRequestorDao")
    private JpaDao<IssuerTokenRequestors, Integer> issuerTokenRequestorDao;

    @Autowired
    @Qualifier("authenticationMechanismDao")
    private JpaDao<AuthenticationMechanism, String> authenticationMechanismDao; // newly
                                                                                // added

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.issuer.IssuerService#getAllIssuers()
     */
    @Override
    @Transactional(readOnly = false)
    public List<Issuer> loadIssuers() {
        List<Issuer> issuers = new ArrayList<Issuer>();
        List<IssuerConfiguration> issuerConfigurations = issuerConfigurationDao.findByName(
                AdminConstants.GET_ISSUERS_TOKEN_REQUESTERS, new HashMap<String, Object>());
        for (IssuerConfiguration issuerConfiguration : issuerConfigurations) {
            List<String> listOfTokens = new ArrayList<String>();
            boolean isFirst = true;
            Issuer issuer = new Issuer();
            issuer.setName(issuerConfiguration.getIssuerName());
            issuer.setIisn(issuerConfiguration.getIisn());
            issuer.setId(issuerConfiguration.getId());
            List<IssuerTokenRequestors> issuerTokenRequestor = issuerConfiguration.getIssuerTokenRequestors();
            for (IssuerTokenRequestors requestorMap : issuerTokenRequestor) {
                TokenRequestorConfiguration requestorConfiguration = requestorMap.getTokenRequestorConfiguration();
                listOfTokens.add(requestorConfiguration.getName());
            }
            StringBuilder tokensAppender = new StringBuilder();
            Collections.sort(listOfTokens);
            for (String str : listOfTokens) {
                if (isFirst) {
                    tokensAppender.append(str);
                    isFirst = false;
                } else {
                    tokensAppender.append(",");
                    tokensAppender.append(str);
                }
            }
            issuer.setTokenRequestor(tokensAppender.toString());
            issuers.add(issuer);
        }

        return issuers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.issuer.IssuerService#getAuthentication()
     */
    @Override
    public String[] getAuthentication() {
        return AdminConstants.AUTHENTICATION_MECHANISMS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.issuer.IssuerService#saveIssuerConfiguration
     * (org.tch.ste.admin.domain.dto.IssuerDetail)
     */
    @Override
    @Transactional(readOnly = false)
    public void saveIssuerConfiguration(IssuerDetail issuer) {
        Integer id = issuer.getId();
        IssuerConfiguration issuerConfiguration = null;
        if (id != null) {
            // Delete existing token requestors.
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AdminConstants.PARAM_NAME_ISSUER_ID, id);
            issuerTokenRequestorDao.updateByName(AdminConstants.DELETE_EXISTING_ISSUER_TOKEN_REQUESTORS, params);
            issuerConfiguration = issuerConfigurationDao.get(id);
        }

        /* Bind the issuer details in issuer configuration domain entity */
        if (issuerConfiguration == null) {
            issuerConfiguration = new IssuerConfiguration();
        }
        issuerConfiguration.setIssuerName(issuer.getName());
        issuerConfiguration.setIid(issuer.getIid());
        issuerConfiguration.setIisn(issuer.getIisn());
        issuerConfiguration.setDropzonePath(issuer.getDropzonePath());
        issuerConfiguration.setAuthenticationType(issuer.getAuthMechanism());
        issuerConfiguration.setAccountListEndpoint(issuer.getAccountListEndpoint());
        issuerConfiguration.setAuthEndpoint(issuer.getAuthEndpoint());
        issuerConfiguration.setAuthenticationAppUrl(issuer.getAuthenticationAppUrl());
        issuerConfiguration.setAuthErrorMessage(issuer.getAuthErrorMessage());
        issuerConfiguration.setAuthLockoutMessage(issuer.getAuthLockoutMessage());
        issuerConfiguration.setDisableCredentialAfterLogin(Boolean.valueOf(issuer.isDisableCredentialsAfterLogin()));
        try {
            issuerConfiguration.setFailedLoginsToLockout(Integer.valueOf(issuer.getFailedAttemptsToTriggerLockout()));
        } catch (Throwable t) {
            issuerConfiguration.setFailedLoginsToLockout(null);
        }
        issuerConfiguration.setId(issuer.getId());
        try {
            MultipartFile issuerLogo = issuer.getIssuerLogo();
            if (issuerLogo != null && issuerLogo.getSize() > 0) {

                issuerConfiguration.setLogoImage(issuerLogo.getBytes());
                issuerConfiguration.setFileName(FilenameUtils.getName(issuerLogo.getOriginalFilename()));
            } else if (issuer.getAuthMechanism() < 3) {
                issuerConfiguration.setFileName(null);
            }
        } catch (IOException e) {
            logger.warn("Unable to fetch the image", e);
        }
        /* Bind supported token requestor in issuer configuration */
        List<IssuerTokenRequestors> issuerTokenRequestors = new ArrayList<IssuerTokenRequestors>();
        for (Integer tokenRequestorId : issuer.getSelectedTokenRequestors()) {
            IssuerTokenRequestors issuerTokenRequestor = new IssuerTokenRequestors();

            /* Bind supported token requestor details */
            TokenRequestorConfiguration tokenRequestorConfiguration = new TokenRequestorConfiguration();
            tokenRequestorConfiguration.setId(tokenRequestorId);
            issuerTokenRequestor.setTokenRequestorConfiguration(tokenRequestorConfiguration);
            issuerTokenRequestor.setIssuerConfiguration(issuerConfiguration);

            /* Add the supported token requestor to the requestors list */
            issuerTokenRequestors.add(issuerTokenRequestor);
        }
        /* Set supported token requestors to the issuer configuration */
        issuerConfiguration.setIssuerTokenRequestors(issuerTokenRequestors);
        if (issuerConfiguration.getId() == null) {
            IssuerConfiguration savedIssuerConfiguration = issuerConfigurationDao.save(issuerConfiguration);
            issuer.setId(savedIssuerConfiguration.getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.issuer.IssuerService#
     * getAllAuthenticationMechanism()
     */
    @Override
    public List<AuthenticationMechanism> getAllAuthenticationMechanism() {
        return authenticationMechanismDao.getAll();
    }

    /**
     * Returns the details of the edit issuer page
     * 
     * @param id
     *            - row id
     * 
     * @return IssuerDetail - returns the issuerDetail dto
     */
    @Override
    public IssuerDetail getIssuerEditDetails(Integer id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_NAME_ISSUER_ID, id);
        IssuerDetail issuer = new IssuerDetail();
        IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                AdminConstants.GET_ISSUER_BY_ID, params));

        issuer.setName(issuerConfiguration.getIssuerName());
        issuer.setIisn(issuerConfiguration.getIisn());
        issuer.setIid(issuerConfiguration.getIid());
        issuer.setDropzonePath(issuerConfiguration.getDropzonePath());
        issuer.setAuthMechanism(issuerConfiguration.getAuthenticationType());
        issuer.setAccountListEndpoint(issuerConfiguration.getAccountListEndpoint());
        issuer.setAuthEndpoint(issuerConfiguration.getAuthEndpoint());
        issuer.setAuthErrorMessage(issuerConfiguration.getAuthErrorMessage());
        issuer.setAuthLockoutMessage(issuerConfiguration.getAuthLockoutMessage());
        if (issuerConfiguration.getDisableCredentialAfterLogin() != null) {
            issuer.setDisableCredentialsAfterLogin(issuerConfiguration.getDisableCredentialAfterLogin());
        } else {
            issuer.setDisableCredentialsAfterLogin(false);
        }
        Integer failedLoginsToLockout = issuerConfiguration.getFailedLoginsToLockout();
        if (failedLoginsToLockout != null) {
            issuer.setFailedAttemptsToTriggerLockout(String.valueOf(failedLoginsToLockout));
        }
        issuer.setId(issuerConfiguration.getId());
        issuer.setFileName(issuerConfiguration.getFileName());
        issuer.setAuthenticationAppUrl(issuerConfiguration.getAuthenticationAppUrl());
        for (IssuerTokenRequestors issuerTokenRequestor : issuerConfiguration.getIssuerTokenRequestors()) {
            issuer.getCurrentTokenRequestors().put(issuerTokenRequestor.getTokenRequestorConfiguration().getId(), true);
        }

        return issuer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.issuer.IssuerService#
     * getIssuersSortedAscendingByName()
     */
    @Override
    public List<IssuerConfiguration> getIssuersSortedAscendingByName() {
        return issuerConfigurationDao.findByName(AdminConstants.GET_ISSUERS_SORTED_BY_NAME,
                new HashMap<String, Object>());
    }

}
