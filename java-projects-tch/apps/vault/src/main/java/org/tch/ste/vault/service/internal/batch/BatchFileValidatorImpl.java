/**
 * 
 */
package org.tch.ste.vault.service.internal.batch;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.BatchFileType;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.repository.PagingAndSortingJpaDao;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.ValidationErrorCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.batchhistory.BatchHistoryFacade;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class BatchFileValidatorImpl implements BatchFileValidator {

    private static Logger logger = LoggerFactory.getLogger(BatchFileValidatorImpl.class);

    @Autowired(required = false)
    private BatchFileValidationHook validationHook;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private PagingAndSortingJpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    @Autowired
    @Qualifier("batchFileTypeDao")
    private PagingAndSortingJpaDao<BatchFileType, Integer> batchFileTypeDao;

    @Autowired
    private BatchHistoryFacade batchHistoryFacade;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.batch.BatchFileValidator#validate(java
     * .io.File)
     */
    @Override
    public BatchFileValidationResult validate(File file) {
        BatchFileValidationResult retVal = new BatchFileValidationResult();
        retVal.setSuccess(true);
        if (validationHook != null) {
            validationHook.validationStart(file);
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.DROPZONE_PATH, FilenameUtils.getName(file.getParentFile().getParent()));
        IssuerConfiguration issuerConfiguration = ListUtil.getFirstOrNull(issuerConfigurationDao.findByName(
                VaultQueryConstants.FIND_ISSUER_BY_DROPZONE_PATH, params));
        String iisn = issuerConfiguration.getIisn();
        if (!validateFileName(file)) {
            retVal.setSuccess(false);
            retVal.setErrorCode(ValidationErrorCode.FILE_NAME_NOT_RECOGNIZED);
        } else if (!validateFileType(file, iisn)) {
            retVal.setSuccess(false);
            retVal.setErrorCode(ValidationErrorCode.INVALID_BATCH_FILE_TYPE);
        } else if (!iisnMatches(file, iisn)) {
            retVal.setSuccess(false);
            retVal.setErrorCode(ValidationErrorCode.FILE_IISN_NOT_MATCHING);
        } else if (isDuplicate(file)) {
            retVal.setSuccess(false);
            retVal.setErrorCode(ValidationErrorCode.DUPLICATE_FILE);
        }
        if (validationHook != null) {
            validationHook.validationComplete(file, retVal);
        }
        return retVal;
    }

    /**
     * Validates the file type.
     * 
     * @param file
     *            File - The file.
     * @param iisn
     *            String - The iisn.
     * @return boolean - True if the file type is valid.
     */
    private boolean validateFileType(File file, String iisn) {
        String fileName = file.getName();
        String fileParts[] = fileName.split(VaultConstants.FILE_PART_SEPERATOR);
        String fileType = fileParts[VaultConstants.FILE_TYPE_PART];
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.BATCH_FILE_TYPE, fileType);
        boolean retVal = !batchFileTypeDao.findByName(VaultQueryConstants.FIND_BY_BATCH_FILE_TYPE, params).isEmpty();
        if (!retVal) {
            logger.error("Input file type {} not recognized, IISN = {}, full file name = " + fileName, fileType, iisn);
        }
        return retVal;
    }

    /**
     * Checks if the file is already processed.
     * 
     * @param file
     *            File - The file.
     * @return boolean - True if the file has already been processed.
     */
    private boolean isDuplicate(File file) {
        String fileName = file.getName();
        String[] splitFileName = fileName.split(VaultConstants.FILE_PART_SEPERATOR);
        boolean retVal = batchHistoryFacade.hasProcessed(splitFileName[VaultConstants.IISN_PART],
                splitFileName[VaultConstants.TIMESTAMP_PART], splitFileName[VaultConstants.FILE_TYPE_PART]);
        if (retVal == true) {
            logger.error("duplicate input file detected, filename = {}", fileName);
        }
        return retVal;
    }

    /**
     * Checks if the IISN matches the file name.
     * 
     * @param file
     *            File - The file.
     * @param iisn
     *            String - The iisn.
     * @return boolean - True if the IISN matches.
     */
    private static boolean iisnMatches(File file, String iisn) {
        String fileName = file.getName();
        String fileParts[] = fileName.split(VaultConstants.FILE_PART_SEPERATOR);

        boolean retVal = iisn.equals(fileParts[VaultConstants.IISN_PART]);
        if (!retVal) {
            logger.error("Input file type {} IISN mismatch, input directory IISN = " + iisn
                    + ", file IISN = {}, full file name = " + fileName, fileParts[VaultConstants.FILE_TYPE_PART],
                    fileParts[VaultConstants.IISN_PART]);
        }
        return retVal;
    }

    /**
     * Validates if the file name matches the pattern:
     * <FILE_TYPE>_<IISN>_<TIMESTAMP>.
     * 
     * @param file
     *            File - the file.
     * @return boolean - True if it matches the pattern.
     */
    private static boolean validateFileName(File file) {
        String[] fileNameParts = file.getName().split(VaultConstants.FILE_PART_SEPERATOR);
        boolean retVal = false;
        if (fileNameParts.length == VaultConstants.NUM_EXPECTED_FILE_NAME_PARTS) {
            String timestampPart = fileNameParts[VaultConstants.TIMESTAMP_PART];
            retVal = timestampPart.length() == InfraConstants.STD_TIMESTAMP_FMT.length()
                    && DateUtil.validateTimestampFormat(timestampPart);
            if (!retVal) {
                logger.error("Invalid timestamp format for file: {}", file.getAbsolutePath());
            }
        }
        return retVal;
    }

}
