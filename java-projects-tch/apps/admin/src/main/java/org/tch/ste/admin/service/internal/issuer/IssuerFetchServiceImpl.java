/**
 * 
 */
package org.tch.ste.admin.service.internal.issuer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implements the interface.
 * 
 * @author anus
 * 
 */
@Service
public class IssuerFetchServiceImpl implements IssuerFetchService {

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.internal.issuer.IssuerFetchService#getIssuerByIisn
     * (java.lang.String)
     */
    @Override
    public IssuerConfiguration getIssuerByIisn(String iisn) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_ISSUER_IISN, iisn);
        return ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(AdminConstants.GET_ISSUERS_BY_IISN, params));
    }

}
