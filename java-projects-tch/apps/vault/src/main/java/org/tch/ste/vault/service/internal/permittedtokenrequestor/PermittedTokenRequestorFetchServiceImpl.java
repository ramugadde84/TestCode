/**
 * 
 */
package org.tch.ste.vault.service.internal.permittedtokenrequestor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.PermittedTokenRequestor;
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
public class PermittedTokenRequestorFetchServiceImpl implements PermittedTokenRequestorFetchService {

    @Autowired
    @Qualifier("permittedTokenRequestorDao")
    private JpaDao<PermittedTokenRequestor, Integer> permittedTokenRequestorDao;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.permittedtokenrequestor.
     * PermittedTokenRequestorFetchService#getByTrId(java.lang.String)
     */
    @Override
    public PermittedTokenRequestor getByTrId(String trId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_TRID, Integer.valueOf(trId));
        return ListUtil.getFirstOrNull(permittedTokenRequestorDao.findByName(
                VaultQueryConstants.GET_TOKENREQUESTOR_DETAILS, params));
    }

}
