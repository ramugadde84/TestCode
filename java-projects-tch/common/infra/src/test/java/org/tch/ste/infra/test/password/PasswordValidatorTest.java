/**
 * 
 */
package org.tch.ste.infra.test.password;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.service.core.password.PasswordValidationService;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * Tests the password validation rules.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class PasswordValidatorTest extends BaseTest {

    @Autowired
    private PasswordValidationService passwordValidationService;

    /**
     * Tests the validation.
     */
    @Test
    public void testValidation() {
        String validPassword = "ab123sAxdc12";
        assertTrue(passwordValidationService.validate(validPassword));
        String invalidPasswordByLength = "abc123AB";
        assertFalse(passwordValidationService.validate(invalidPasswordByLength));
        String invalidPasswordWithNoDigits = "abcdefABCDEF";
        assertFalse(passwordValidationService.validate(invalidPasswordWithNoDigits));
        String invalidPasswordWithNoUpperCase = "abcdef123456";
        assertFalse(passwordValidationService.validate(invalidPasswordWithNoUpperCase));
        String invalidPasswordWithNoLowerCase = "ABCDEF123456";
        assertFalse(passwordValidationService.validate(invalidPasswordWithNoLowerCase));
        String invalidPasswordWithSpecialChars = "aabb12(12345";
        assertFalse(passwordValidationService.validate(invalidPasswordWithSpecialChars));
    }
}
