/**
 * 
 */
package org.tch.ste.auth.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.tch.ste.auth.dto.AbstractResponse;
import org.tch.ste.auth.dto.Errors;

/**
 * Handles error messages.
 * 
 * @author Karthik.
 * 
 */
public class ErrorMessageUtil {
    /**
     * Private Constructor.
     */
    private ErrorMessageUtil() {
        // Empty.
    }

    /**
     * Adds the error message to the source.
     * 
     * @param messageSource
     *            MessageSource- The message source.
     * @param fieldName
     *            String - The field to which this error belongs.
     * @param errorCode
     *            String - The error code.
     * @param response
     *            AbstractResponse- The response.
     * @param locale
     *            Locale - The locale.
     * @param args
     *            Object [] - The arguments to the message.
     */
    public static void addErrorMessage(MessageSource messageSource, String fieldName, String errorCode,
            AbstractResponse response, Object[] args, Locale locale) {
        Locale myLocale = locale;
        if (myLocale == null) {
            myLocale = Locale.getDefault();
        }
        Map<String, String> errorMessages = response.getFieldErrorMessages();
        if (errorMessages == null) {
            errorMessages = new HashMap<String, String>();
            response.setFieldErrorMessages(errorMessages);
        }
        errorMessages.put(fieldName, messageSource.getMessage(errorCode, args, myLocale));
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