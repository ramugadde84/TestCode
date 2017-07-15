/**
 * 
 */
package org.tch.ste.vault.test.generation;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.internal.generation.UniqueUserNameGenerationService;

/**
 * Validates the generation of unique usernames.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class UniqueUserNameGenerationTest extends BaseTest {

    private static int HASH_SIZE = 10033;

    private static final String iisn = "123456";

    @Autowired
    private EntityManagerInjector entityManagerInjector;

    @Autowired
    private UniqueUserNameGenerationService uniqueUserNameGenerationService;

    /**
     * Sets the entity manager for a given IISN.
     */
    @BeforeTransaction
    public void setEntityManager() {
        entityManagerInjector.inject(iisn);
    }

    /**
     * Tests for user name generation.
     */
    @Test
    @Transactional
    public void testUserNameGeneration() {
        Map<String, Boolean> usersGenerated = new HashMap<String, Boolean>(HASH_SIZE);
        for (int i = 0; i < 10000; ++i) {
            Customer cust = uniqueUserNameGenerationService.generate();
            String userName = cust.getUserName();
            assertFalse(usersGenerated.containsKey(userName));
            usersGenerated.put(userName, Boolean.TRUE);
        }
    }

}
