/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.springframework.stereotype.Service;
import org.tch.ste.domain.constant.BatchFileRecordType;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.PatternMatcherUtil;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class PaymentInstrumentPreloadValidationServiceImpl implements PaymentInstrumentPreloadValidationService {

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument.
     * PaymentInstrumentPreloadValidationService#validate(java.lang.String,
     * org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument.
     * PaymentInstrumentPreloadContent)
     */
    @Override
    public BatchResponseCode validate(String iisn, PaymentInstrumentPreloadContent preloadContent) {
        BatchResponseCode retVal = checkMandatoryFields(preloadContent);
        if (retVal == BatchResponseCode.SUCCCESS) {
            retVal = validateDataTypeAndLength(preloadContent);
        }
        return retVal;
    }

    /**
     * Validates whether the format is correct.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @return BatchResponseCode - Success if all types match.
     */
    private static BatchResponseCode validateDataTypeAndLength(PaymentInstrumentPreloadContent preloadContent) {
        BatchResponseCode retVal = BatchResponseCode.SUCCCESS;
        String expiryDate = preloadContent.getExpirationDate();
        if (!PatternMatcherUtil.isNumeric(preloadContent.getPan()) || !PatternMatcherUtil.isNumeric(expiryDate)) {
            retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
        } else {
            if (!DateUtil.isValidMonthYear(expiryDate)) {
                retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
            }
        }
        if (preloadContent.getIssuerAccountId().length() > VaultConstants.ISSUER_ACCOUNT_ID_SIZE) {
            retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
        }
        if (preloadContent.getIssuerCustomerId().length() > VaultConstants.ISSUER_CUSTOMER_ID_SIZE) {
            retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
        }
        return retVal;
    }

    /**
     * Validates whether all the mandatory fields are present and of the right
     * type.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The preload content.
     * @return BatchResponseCode - The response code.
     */
    private static BatchResponseCode checkMandatoryFields(PaymentInstrumentPreloadContent preloadContent) {
        BatchResponseCode retVal = BatchResponseCode.SUCCCESS;
        if (preloadContent.getType() != BatchFileRecordType.CONTENT.ordinal()) {
            retVal = BatchResponseCode.INPUT_VALUE_TYPE_MISMATCH;
        } else if (StringUtil.isNullOrBlank(preloadContent.getPan())
                || StringUtil.isNullOrBlank(preloadContent.getExpirationDate())
                || StringUtil.isNullOrBlank(preloadContent.getIssuerAccountId())
                || StringUtil.isNullOrBlank(preloadContent.getIssuerCustomerId())) {
            retVal = BatchResponseCode.MISSING_MANDATORY_FIELD;
        }
        return retVal;
    }

}
