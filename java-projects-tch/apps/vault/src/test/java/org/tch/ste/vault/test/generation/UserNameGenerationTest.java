/**
 * 
 */
package org.tch.ste.vault.test.generation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.internal.generation.UserNameGenerationService;

/**
 * Tests the user name generation.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class UserNameGenerationTest extends BaseTest {

    private static int HASH_SIZE = 10033;

    @Autowired
    private UserNameGenerationService userNameGenerationService;

    /**
     * Tests for user name generation.
     */
    @Test
    public void testUserNameGeneration() {
        Map<String, Boolean> usersGenerated = new HashMap<String, Boolean>(HASH_SIZE);
        for (int i = 0; i < 50000; ++i) {
            String userName = userNameGenerationService.generate();
            validateUserName(userName);
            assertFalse(usersGenerated.containsKey(userName));
            usersGenerated.put(userName, Boolean.TRUE);
        }
    }

    /**
     * Validates the user name.
     * 
     * @param userName
     *            String - The user name.
     */
    private static void validateUserName(String userName) {
        assertEquals(userName.length(), 8);
        assertTrue(userName.matches("(.*)[abcdefghijklmnopqrstuvwxyz](.*)"));
        assertTrue(userName.matches("(.*)[0123456789](.*)"));
    }
}
