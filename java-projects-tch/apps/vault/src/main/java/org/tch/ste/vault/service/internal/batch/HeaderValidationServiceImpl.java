/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.domain.dto.BatchFileHeader;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class HeaderValidationServiceImpl implements HeaderValidationService {

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.HeaderValidationService#validate
     * (org.tch.ste.vault.domain.dto.BatchFileHeader, java.lang.String,
     * java.lang.String)
     */
    @Override
    public BatchResponseCode validate(BatchFileHeader header, String iisn, String fileType) {
        BatchResponseCode retVal = BatchResponseCode.SUCCCESS;
        if (header.getType() != BatchFileRecordType.HEADER.ordinal()) {
            retVal = BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR;
        } else if (!fileType.equals(header.getFileType())) {
            retVal = BatchResponseCode.FILE_TYPE_INDICATOR_MISMATCH;
        } else if (!DateUtil.validateTimestampFormat(header.getTimeStamp())) {
            retVal = BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR;
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(VaultQueryConstants.PARAM_IID, header.getIid());
            IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                    VaultQueryConstants.FIND_ISSUER_BY_IID, params));
            if (issuerConfiguration == null || !issuerConfiguration.getIisn().equals(iisn)) {
                retVal = BatchResponseCode.IISN_MISMATCH;
            }
        }
        return retVal;
    }
}
