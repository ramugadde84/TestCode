package org.tch.ste.vault.test.generation;

/**
 * @author sujathas
 *
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.generation.ArnGenerationFacade;

/**
 * @author sujathas
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class ArnGenerationTest extends BaseTest {
    private static int HASH_SIZE = 10033;

    private static final String iisn = "123456";
    private static final String bin = "123466";
    private static final String newiisn = "123457";
    private static final String newbin = "123456";

    @Autowired
    private EntityManagerInjector entityManagerInjector;

    @Autowired
    private ArnGenerationFacade arnGenerationFacade;

    /**
     * Sets the entity manager for a given IISN.
     */
    @BeforeTransaction
    public void setEntityManager() {
        entityManagerInjector.inject(iisn);
    }

    /**
     * Tests for ARN generation.-
     */
    @Test
    @Transactional
    public void testarnGeneration() {
        Map<Arn, Boolean> arnGenerated = new HashMap<Arn, Boolean>(HASH_SIZE);
        for (int i = 0; i < 10000; ++i) {
            Arn arnId = arnGenerationFacade.generateArn(iisn, bin);
            assertFalse(arnGenerated.containsKey(arnId));
            assertTrue(arnId.getArn().length() == VaultQueryConstants.ARN_LENGTH);
            arnGenerated.put(arnId, Boolean.TRUE);

        }

    }

    /**
     * Generating ARN with Invalid IISN & BIN value.
     */
    @Test
    @Transactional
    public void testarnGenerationFailure() {
        Arn arnId = arnGenerationFacade.generateArn(newiisn, newbin);
        assertEquals(arnId, null);
    }

}
