package org.tch.ste.infra.test.security;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.service.core.security.HashingService;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * Test Cases to validate HashService
 * 
 * @author sujathas
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class HashServiceTest extends BaseTest {

    @Autowired
    private HashingService hashingService;

    private static final String pan = "2356891";
    private static final String salt = "abcdefghijklmnopqrstuvwxyz";
    private static final Long SALT = 96532890176L;

    private ShaPasswordEncoder hashEncoder = new ShaPasswordEncoder(512);

    /**
     * Hashing the Pan Value
     */
    @Test
    public void testPan() {

        String hashedVal = hashingService.hash(pan);
        assertTrue(hashedVal != pan);
        String hashVal = hashEncoder.encodePassword(pan, SALT);
        assertTrue(hashedVal.equals(hashVal));
    }

    /**
     * Hashing the Pan value with Salt
     */
    @Test
    public void testPanSalt() {

        String hashedVal = hashingService.hash(pan, salt);
        assertTrue(hashedVal != pan);
        String hashVal = hashEncoder.encodePassword(pan, salt);
        assertTrue(hashedVal.equals(hashVal));
    }

}
