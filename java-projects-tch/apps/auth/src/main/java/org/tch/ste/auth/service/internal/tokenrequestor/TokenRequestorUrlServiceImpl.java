/**
 * 
 */
package org.tch.ste.auth.service.internal.tokenrequestor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.domain.entity.TokenRequestorConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.UrlUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class TokenRequestorUrlServiceImpl implements TokenRequestorUrlService {

    @Autowired
    @Qualifier("tokenRequestorConfigurationDao")
    private JpaDao<TokenRequestorConfiguration, Integer> tokenRequestorConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.service.internal.tokenrequestor.TokenRequestorUrlService
     * #formUrl(java.lang.String, boolean)
     */
    @Override
    public String formUrl(String trId, boolean success) {
        StringBuilder retVal = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AuthQueryConstants.PARAM_TRID, trId);
        TokenRequestorConfiguration tokenRequestorConfiguration = ListUtil
                .getFirstOrNull(tokenRequestorConfigurationDao.findByName(
                        AuthQueryConstants.GET_TOKEN_REQUESTOR_BY_TR_ID, params));
        if (tokenRequestorConfiguration != null) {
            UrlUtil.appendParam(
                    retVal,
                    tokenRequestorConfiguration.getStatusUriKey(),
                    (success) ? tokenRequestorConfiguration.getSuccessUriValue() : tokenRequestorConfiguration
                            .getFailureUriValue());
        }
        return retVal.toString();
    }

}
