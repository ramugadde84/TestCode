/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.configuration.ConfigurationPropertiesService;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchOutputFileNameCreationServiceImpl implements BatchOutputFileNameCreationService {

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    @Autowired
    private ConfigurationPropertiesService configurationPropertiesService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchOutputFileNameCreationService
     * #createTempOutputFileName(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public String createTempOutputFileName(String fileType, String iisn, String timeStamp) {
        StringBuilder retVal = new StringBuilder(fileType);
        retVal.append('_');
        retVal.append(iisn);
        retVal.append('_');
        retVal.append(timeStamp);
        return retVal.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchOutputFileNameCreationService
     * #createActualOutputFileName(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public String createActualOutputFileName(String fileType, String iisn, String timeStamp) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_IISN, iisn);
        IssuerConfiguration issuer = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                VaultQueryConstants.GET_ISSUER_BY_IISN, params));
        StringBuilder retVal = new StringBuilder(fileType);
        retVal.append('_');
        retVal.append(iisn);
        retVal.append('_');
        retVal.append(timeStamp);
        return FilenameUtils.concat(FilenameUtils.concat(
                FilenameUtils.concat(configurationPropertiesService.getBatchRoot(), issuer.getDropzonePath()),
                VaultConstants.OUTBOUND_DIR), retVal.toString());
    }

}
