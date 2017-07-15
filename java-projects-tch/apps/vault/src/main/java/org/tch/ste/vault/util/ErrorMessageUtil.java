/**
 * 
 */
package org.tch.ste.vault.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.tch.ste.vault.domain.dto.Errors;

/**
 * Creates errors.
 * 
 * @author kjanani
 * 
 */
public class ErrorMessageUtil {
    /**
     * Constructor.
     */
    private ErrorMessageUtil() {
        // Empty.
    }

    /**
     * Creates Errors.
     * 
     * @param code
     *            String - The code.
     * @param messageSource
     *            MessageSource - The message source.
     * @return Errors - The errors.
     */
    public static Errors getErrors(String code, MessageSource messageSource) {
        Errors errors = new Errors();
        errors.setErrorCode(code);
        errors.setErrorMessage(messageSource.getMessage(code, null, Locale.ENGLISH));
        return errors;
    }
}
