/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultQueryConstants;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class TokenFetchServiceImpl implements TokenFetchService {

    @Autowired
    @Qualifier("permittedTokenRequestorArnDao")
    private JpaDao<PermittedTokenRequestorArn, Integer> permittedTokenRequestorArnDao;

    @Autowired
    @Qualifier("tokenDao")
    private JpaDao<Token, Integer> tokenDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.token.TokenFetchService#
     * getPermittedTokenRequestorArn(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public PermittedTokenRequestorArn getPermittedTokenRequestorArn(String arn, String tokenRequestorId, String ciid) {
        String query = (ciid != null) ? VaultQueryConstants.GET_PERMITTED_TOKEN_ARN_WITH_CIID
                : VaultQueryConstants.GET_PERMITTED_TOKEN_ARN_WITHOUT_CIID;
        Map<String,Object> params=formStandardParams(arn, tokenRequestorId, ciid);
        params.put(VaultQueryConstants.PARAM_ACTIVE, Boolean.TRUE);
        return ListUtil.getFirstOrNull(permittedTokenRequestorArnDao.findByName(query,
                params));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.token.TokenFetchService#getTokens(
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Token> getTokens(String arn, String tokenRequestorId, String ciid) {
        String query = (ciid != null) ? VaultQueryConstants.GET_TOKENS_WITH_CIID
                : VaultQueryConstants.GET_TOKENS_WITHOUT_CIID;
        return tokenDao.findByName(query, formStandardParams(arn, tokenRequestorId, ciid));
    }

    /**
     * Forms the standard parameters.
     * 
     * @param arn
     *            String - The Arn.
     * @param tokenRequestorId
     *            String - The token requestor id.
     * @param ciid
     *            String - The ciid.
     * @return Map<String,Object> - The parameters.
     */
    private static Map<String, Object> formStandardParams(String arn, String tokenRequestorId, String ciid) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_ARN, arn);
        params.put(VaultQueryConstants.PARAM_TRID, Integer.valueOf(tokenRequestorId));
        if (ciid != null) {
            params.put(VaultQueryConstants.PARAM_CIID, ciid);
        }
        return params;
    }
}
