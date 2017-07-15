/**
 * 
 */
package org.tch.ste.vault.service.internal.issuer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.AuthenticationType;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.PagingAndSortingJpaDao;
import org.tch.ste.vault.constant.VaultQueryConstants;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class IssuerRetrievalServiceImpl implements IssuerRetrievalService {

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private PagingAndSortingJpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.internal.issuer.IssuerRetrievalService#getAll()
     */
    @Override
    public List<IssuerConfiguration> getAll() {
        return issuerConfigurationDao.getAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.issuer.IssuerRetrievalService#
     * fetchIssuersWithGeneratedUsernamePasswords()
     */
    @Override
    public List<IssuerConfiguration> fetchIssuersWithGeneratedUsernamePasswords() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_AUTHENTICATION_TYPE,
                AuthenticationType.TCH_HOSTED_WITH_GENERATED_CREDS.getType());
        return issuerConfigurationDao.findByName(VaultQueryConstants.GET_ISSUERS_BY_AUTH_TYPE, params);
    }

}
