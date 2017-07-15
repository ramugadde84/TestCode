/**
 * 
 */
package org.tch.ste.infra.service.core.password;

import org.springframework.stereotype.Service;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.PatternMatcherUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.service.core.password.PasswordValidationService#validate
     * (java.lang.String)
     */
    @Override
    public boolean validate(String password) {
        if (password != null) {
            int passwordLength = password.length();
            return passwordLength >= InfraConstants.MIN_PASSWORD_LENGTH
                    && passwordLength <= InfraConstants.MAX_PASSWORD_LENGTH
                    && PatternMatcherUtil.containsLowerCaseCharacter(password)
                    && PatternMatcherUtil.containsUpperCaseCharacter(password)
                    && PatternMatcherUtil.containsDigit(password)
                    && !PatternMatcherUtil.containsSpecialCharacter(password);
        }
        return false;
    }

}
