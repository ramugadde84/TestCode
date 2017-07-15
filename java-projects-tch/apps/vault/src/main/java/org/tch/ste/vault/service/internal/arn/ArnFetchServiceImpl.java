/**
 * 
 */
package org.tch.ste.vault.service.internal.arn;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.Arn;
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
public class ArnFetchServiceImpl implements ArnFetchService {

    @Autowired
    @Qualifier("arnDao")
    private JpaDao<Arn, Integer> arnDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.arn.ArnFetchService#getArn(java.lang
     * .String)
     */
    @Override
    public Arn getArn(String arn) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_ARN, arn);
        return ListUtil.getFirstOrNull(arnDao.findByName(VaultQueryConstants.GET_ARN_DETAILS, params));
    }

}
