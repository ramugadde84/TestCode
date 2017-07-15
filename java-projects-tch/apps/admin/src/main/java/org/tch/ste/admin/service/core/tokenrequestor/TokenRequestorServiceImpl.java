package org.tch.ste.admin.service.core.tokenrequestor;

import static org.tch.ste.admin.constant.AdminConstants.DELETE_ISSUER_TOKEN_REQUESTORS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.dto.TokenRequestor;
import org.tch.ste.domain.constant.BoolStr;
import org.tch.ste.domain.entity.PermittedTokenRequestor;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.repository.JpaDao;

/**
 * Implements the interface.
 * 
 * @author anus
 * 
 */

@Service
public class TokenRequestorServiceImpl implements TokenRequestorService {

    private static Logger logger = LoggerFactory.getLogger(TokenRequestorServiceImpl.class);

    @Autowired
    @Qualifier("tokenRequestorConfigurationDao")
    private JpaDao<TokenRequestorConfiguration, Integer> tokenRequestorConfigurationDao;

    @Autowired
    @Qualifier("permittedTokenRequestorDao")
    private JpaDao<PermittedTokenRequestor, Integer> permittedTokenRequestorDao;

    /**
     * This method is used to load the token requestors.
     */
    @Override
    public List<TokenRequestor> loadTokenRequestors() {
        List<TokenRequestor> tokenRequestors = new ArrayList<TokenRequestor>();
        List<TokenRequestorConfiguration> tokenRequestorConfigurations = tokenRequestorConfigurationDao.findByName(
                AdminConstants.GET_TOKEN_REQUESTORS, new HashMap<String, Object>());
        for (TokenRequestorConfiguration tokenRequestorConfiguration : tokenRequestorConfigurations) {
            TokenRequestor tokenRequestor = new TokenRequestor();
            tokenRequestor.setId(tokenRequestorConfiguration.getId());
            tokenRequestor.setName(tokenRequestorConfiguration.getName());
            tokenRequestor.setNetworkId(tokenRequestorConfiguration.getNetworkTokenRequestId());
            tokenRequestors.add(tokenRequestor);
        }

        return tokenRequestors;
    }

    /**
     * This method is used to save the token requestors
     */
    @Override
    @Transactional(readOnly = false)
    public void saveTokenRequestor(TokenRequestor tokenRequestor) {
        TokenRequestorConfiguration tokenRequestorConfiguration = tokenRequestorConfigurationDao
                .save(convertToEntity(tokenRequestor));
        if (tokenRequestor.getId() == null) {
            tokenRequestor.setId(tokenRequestorConfiguration.getId());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorService#
     * deleteTokenRequestor(org.tch.ste.admin.domain.dto.TokenRequestor)
     */
    @Override
    @Transactional(readOnly = false)
    public TokenRequestor deleteTokenRequestor(TokenRequestor tokenRequestor) {
        Integer tokenRequestorId = tokenRequestor.getId();
        TokenRequestorConfiguration tokenRequestorConfiguration = tokenRequestorConfigurationDao.get(tokenRequestorId);
        TokenRequestor retVal = convertToDto(tokenRequestorConfiguration);
        tokenRequestorConfiguration.setDeleteFlag(BoolStr.Y.name());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_ID, tokenRequestorId);
        tokenRequestorConfigurationDao.updateByName(DELETE_ISSUER_TOKEN_REQUESTORS, params);
        return retVal;
    }

    /**
     * Converts the DTO into the entities.
     * 
     * @param TokenRequestorConfiguration
     *            TokenRequestorConfiguration - The token requestor
     *            configuration.
     * @return List<TokenRequestorConfiguration> - The entity objects.
     */
    private static TokenRequestorConfiguration convertToEntity(TokenRequestor tokenRequestor) {
        TokenRequestorConfiguration tokenRequestorConfiguration = new TokenRequestorConfiguration();
        tokenRequestorConfiguration.setName(tokenRequestor.getName());
        tokenRequestorConfiguration.setNetworkTokenRequestId(tokenRequestor.getNetworkId());
        tokenRequestorConfiguration.setId(tokenRequestor.getId());
        tokenRequestorConfiguration.setDeleteFlag(BoolStr.N.name());
        return tokenRequestorConfiguration;
    }

    /**
     * Converts the DTO into the entities.
     * 
     * @param tokenRequestorConfiguration
     *            TokenRequestorConfiguration - The token requestor
     *            configuration.
     * @return List<TokenRequestorConfiguration> - The entity objects.
     */
    private static TokenRequestor convertToDto(TokenRequestorConfiguration tokenRequestorConfiguration) {
        TokenRequestor tokenRequestor = new TokenRequestor();
        tokenRequestor.setName(tokenRequestorConfiguration.getName());
        tokenRequestor.setNetworkId(tokenRequestorConfiguration.getNetworkTokenRequestId());
        tokenRequestor.setId(tokenRequestorConfiguration.getId());
        return tokenRequestor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorService#
     * getTokenRequestorDtls(java.lang.Integer)
     */
    @Override
    public TokenRequestor getTokenRequestorDtls(Integer id) {
        TokenRequestorConfiguration tokenRequestorConfiguration = getTokenRequestor(id);
        TokenRequestor retVal = null;
        if (BoolStr.N.name().equals(tokenRequestorConfiguration.getDeleteFlag())) {
            retVal = convertToDto(tokenRequestorConfiguration);
        }
        return retVal;
    }

    /**
     * Retrieves all token requestors
     * 
     * @return List<TokenRequestor> - Get all token requestors.
     */
    @Override
    public List<TokenRequestor> getAll() {
        List<TokenRequestor> tokenRequestors = new ArrayList<TokenRequestor>();
        List<TokenRequestorConfiguration> tokenRequestorConfigurations = tokenRequestorConfigurationDao.getAll();
        for (TokenRequestorConfiguration tokenRequestorConfiguration : tokenRequestorConfigurations) {
            TokenRequestor tokenRequestor = new TokenRequestor();
            tokenRequestor.setName(tokenRequestorConfiguration.getName());
            tokenRequestor.setId(tokenRequestorConfiguration.getId());
            tokenRequestors.add(tokenRequestor);
        }
        return tokenRequestors;
    }

    /**
     * Fetches the token requestor.
     * 
     * @param id
     *            Integer - The id.
     * @return TokenRequestorConfiguration - value if found, null otherwise.
     */
    private TokenRequestorConfiguration getTokenRequestor(Integer id) {
        return tokenRequestorConfigurationDao.get(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrequestor.TokenRequestorService#
     * savePermittedTokenRequestor(java.lang.String, java.lang.Integer[])
     */
    @Override
    @Transactional(readOnly = false)
    public void savePermittedTokenRequestor(String iisn, Integer[] selectedTokenRequestors) {
        // First fetch all the actual token requestors from the DB to get the
        // Network TR ID
        // Then add the new Permitted Token Requestors.
        // And remove if needed, ones which are not selected.
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_TOKEN_REQUESTORS_IDS, Arrays.asList(selectedTokenRequestors));
        List<TokenRequestorConfiguration> tokenRequestorConfigurations = tokenRequestorConfigurationDao.findByName(
                AdminConstants.GET_TOKEN_REQUESTORS_BY_ID, params);

        Map<String, PermittedTokenRequestor> existingTokenRequestors = new HashMap<String, PermittedTokenRequestor>();
        for (PermittedTokenRequestor permittedTokenRequestor : permittedTokenRequestorDao.getAll()) {
            existingTokenRequestors.put(permittedTokenRequestor.getNetworkTokenRequestorId().toString(),
                    permittedTokenRequestor);
        }
        Map<String, Boolean> currentTokenRequestors = new HashMap<String, Boolean>();
        for (TokenRequestorConfiguration tokenRequestorConfiguration : tokenRequestorConfigurations) {
            String id = tokenRequestorConfiguration.getNetworkTokenRequestId();
            currentTokenRequestors.put(id, Boolean.TRUE);
            if (!existingTokenRequestors.containsKey(id)) {
                PermittedTokenRequestor permittedTokenRequestor = new PermittedTokenRequestor();
                permittedTokenRequestor.setNetworkTokenRequestorId(Integer.valueOf(id));
                permittedTokenRequestorDao.save(permittedTokenRequestor);
            }
        }

        for (Entry<String, PermittedTokenRequestor> entry : existingTokenRequestors.entrySet()) {
            if (!currentTokenRequestors.containsKey(entry.getKey())) {
                PermittedTokenRequestor permittedTokenRequestor = entry.getValue();
                if (permittedTokenRequestor.getTokenRequestorArns() == null
                        || permittedTokenRequestor.getTokenRequestorArns().isEmpty()) {
                    permittedTokenRequestorDao.delete(permittedTokenRequestor);
                } else {
                    // FIXME - Should be talking to Ganesh & Jithu about this.
                    logger.warn(
                            "Cannot delete Token Requestor: {} from Permitted Token Requestor since it has children",
                            permittedTokenRequestor.getNetworkTokenRequestorId());
                }
            }
        }
    }
}