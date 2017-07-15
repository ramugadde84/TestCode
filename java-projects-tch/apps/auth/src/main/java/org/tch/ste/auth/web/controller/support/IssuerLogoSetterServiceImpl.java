/**
 * 
 */
package org.tch.ste.auth.web.controller.support;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.auth.constant.AuthQueryConstants;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class IssuerLogoSetterServiceImpl implements IssuerLogoSetterService {

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.auth.web.controller.support.IssuerLogoSetterService#setIssuerLogo
     * (java.lang.String, java.io.File)
     */
    @Override
    @Transactional(readOnly = false)
    public void setIssuerLogo(String iisn, File logoFile) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AuthQueryConstants.PARAM_IISN, iisn);
        IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                AuthQueryConstants.GET_ISSUER_BY_IISN, params));
        if (issuerConfiguration != null) {
            try {
                issuerConfiguration.setLogoImage(FileUtil.readAsByteArray(logoFile));
                issuerConfiguration.setFileName(logoFile.getName());
                issuerConfigurationDao.save(issuerConfiguration);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
