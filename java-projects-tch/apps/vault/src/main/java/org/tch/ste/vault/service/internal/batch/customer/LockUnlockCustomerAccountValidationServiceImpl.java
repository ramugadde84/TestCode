/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.BatchResponseCode;

/**
 * Lock or unlock customer account validations service.
 * 
 * @author ramug
 * 
 */
@Service
public class LockUnlockCustomerAccountValidationServiceImpl implements LockUnlockCustomerAccountValidationService {

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.customer.
     * LockUnlockCustomerAccountValidationService#validate(java.lang.String,
     * org.tch.ste.vault.service.internal.batch.customer.
     * LockUnlockCustomerAccountContent)
     */
    @Override
    public BatchResponseCode validate(String iisn, LockUnlockCustomerAccountContent accountContent) {
        // Direct return is safe.
        return checkMandatoryFields(accountContent);
    }

    /**
     * Validates whether all the mandatory fields are present and of the right
     * type.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The preload content.
     * @return BatchResponseCode - The response code.
     */
    private static BatchResponseCode checkMandatoryFields(LockUnlockCustomerAccountContent accountContent) {
        BatchResponseCode retVal = BatchResponseCode.SUCCCESS;
        if (accountContent.getType() != BatchFileRecordType.CONTENT.ordinal()) {
            retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
        } else if (StringUtil.isNullOrBlank(accountContent.getIssuerCustomerId())
                || StringUtil.isNullOrBlank(accountContent.getUserName())
                || StringUtil.isNullOrBlank(accountContent.getLockState())) {
            retVal = BatchResponseCode.MISSING_MANDATORY_FIELD;
        }
        return retVal;
    }

}
