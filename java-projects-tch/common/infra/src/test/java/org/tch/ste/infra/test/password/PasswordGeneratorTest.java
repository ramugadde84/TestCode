/**
 * 
 */
package org.tch.ste.infra.test.password;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.service.core.password.PasswordGenerationService;
import org.tch.ste.infra.service.core.password.PasswordValidationService;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * Tests password generation.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class PasswordGeneratorTest extends BaseTest {

    private static final int HASH_SIZE = 10053;

    @Autowired
    private PasswordGenerationService passwordGenerationService;

    @Autowired
    private PasswordValidationService passwordValidationService;

    /**
     * Password generation.
     */
    @Test
    public void testPasswordGeneration() {
        Map<String, Boolean> generatedPasswords = new HashMap<String, Boolean>(HASH_SIZE);
        for (int i = 0; i < 50000; ++i) {
            Password password = passwordGenerationService.generatePassword();
            String plainTextPassword = password.getPlainTextPassword();
            assertEquals(plainTextPassword.length(), InfraConstants.MIN_PASSWORD_LENGTH);
            passwordValidationService.validate(plainTextPassword);
            assertFalse(generatedPasswords.containsKey(plainTextPassword));
            generatedPasswords.put(plainTextPassword, Boolean.TRUE);
        }
    }
}
