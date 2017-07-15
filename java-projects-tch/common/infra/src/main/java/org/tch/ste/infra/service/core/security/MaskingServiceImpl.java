/**
 * 
 */
package org.tch.ste.infra.service.core.security;

import org.springframework.stereotype.Service;
import org.tch.ste.infra.constant.InfraConstants;

/**
 * File used to masking the given PAN & CVV numbers.
 * 
 * @author pamartheepan
 * 
 */
@Service
public class MaskingServiceImpl implements MaskingService {

    /**
     * Masking the given number.
     * 
     * Format- First six and last for digits number should be displayed
     * Remaining digits should display as format of X.
     * 
     * @param maskingDigits
     *            String - The PAN or CVV number.
     * @return String - The response.
     */
    @Override
    public String mask(String maskingDigits) {
        String masked = null;
        if (maskingDigits != null) {
            int total = maskingDigits.trim().length();
            if (total > 10) {
                int startLen = InfraConstants.DISPALY_LENGTH_OF_DIGIT_DISPALY_INFRONT, endLen = InfraConstants.DISPALY_LENGTH_OF_DIGIT_DISPALY_LAST;
                int maskLen = total - (startLen + endLen);
                StringBuilder maskedbuf = new StringBuilder(maskingDigits.substring(0, startLen));
                for (int i = 0; i < maskLen; i++) {
                    maskedbuf.append(InfraConstants.MASK_SYMBOL);
                }
                maskedbuf.append(maskingDigits.substring(startLen + maskLen, total));
                masked = maskedbuf.toString();
            } else {
                throw new IllegalStateException("Masking Number should be greater than 10 digits, but is actually"
                        + total);
            }
        }
        return masked;
    }

}