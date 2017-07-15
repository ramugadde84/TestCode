/**
 * 
 */
package org.tch.ste.vault.test.generation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.repository.support.EntityManagerInjector;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.internal.token.TokenCreationService;

/**
 * This method is used to test the token generation service
 * 
 * @author kjanani
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class TokenGenerationTest extends BaseTest {

    private static final String iisn = "123456";
    private static String tokenRequestorId = "1234";
    private static String arn_16 = "100";
    private static String arn_19 = "105";
    private static String arn = "200";
    private static int HASH_SIZE = 10033;
    private static String ciid ="12245";
    @Autowired
    private EntityManagerInjector entityManagerInjector;

    @Autowired
    TokenCreationService tokenCreationService;

    /**
     * Sets the entity manager for a given IISN.
     */
    @BeforeTransaction
    public void setEntityManager() {
        entityManagerInjector.inject(iisn);
    }

    /**
     * Test for Token generation - 16 digit length token
     * 
     */
    @Test
    @Transactional
    public void testTokenGenerationForSixteen() {
        Map<String, Boolean> tokenGenerated = new HashMap<String, Boolean>(HASH_SIZE);
        for (int i = 0; i < 1000; ++i) {
            Token token = tokenCreationService.createToken(iisn, tokenRequestorId, arn_16,(i%2==0)?ciid:null);
            String tokenValue = token.getEncryptedPanToken();
            assertFalse(tokenGenerated.containsKey(tokenValue));
            tokenGenerated.put(tokenValue, Boolean.TRUE);
            assertTrue(token.getPlainTextToken().length() == 16);
        }
    }

    /**
     * Test for Token generation - 19 digit length token
     * 
     */
    @Test
    @Transactional
    public void testTokenGenerationForNineteen() {

        Map<String, Boolean> tokenGenerated = new HashMap<String, Boolean>(HASH_SIZE);
        for (int i = 0; i < 1000; ++i) {
            Token token = tokenCreationService.createToken(iisn, tokenRequestorId, arn_19,(i%2==0)?ciid:null);
            String tokenValue = token.getEncryptedPanToken();
            assertFalse(tokenGenerated.containsKey(tokenValue));
            tokenGenerated.put(tokenValue, Boolean.TRUE);
            assertTrue(token.getPlainTextToken().length() == 19);

        }
    }

    /**
     * Test for negative scenario when the payment instrument is inactive
     * 
     */
    @Test
    @Transactional
    public void testTokenGenerationForInactivePaymentInstrument() {
        assertNull(tokenCreationService.createToken(iisn, tokenRequestorId, arn,ciid));
    }

}
