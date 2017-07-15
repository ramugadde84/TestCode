/**
 * 
 */

package org.tch.ste.infra.test.batch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.service.core.security.MaskingService;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * Test file for masking data.
 * 
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class MaskingServiceTest extends BaseTest {
    private static final String maskingValue = "987654321123456789";
    private static final String maskedValue = "987654XXXXXXXX6789";
    private static final String maskedInvalidData = "987123";
    private static final String expMsg = "Masking Number should be greater than 10 digits, but is actually";

    @Autowired
    private MaskingService maskingService;

    /**
     * Method used to test valid data.
     * 
     * @throws Exception
     *             -Exception.
     */
    @Test
    public void testMaskingService() throws Exception {
        assertTrue(maskedValue.equals(maskingService.mask(maskingValue)));
    }

    /**
     * Method used to test empty mask value.
     * 
     * @throws Exception
     *             - Exception.
     */
    @Test
    public void testEmptyMaskData() throws Exception {
        try {
            maskingService.mask(maskedInvalidData);
        } catch (IllegalStateException exp) {
            assertTrue(exp.toString().contains(expMsg));
        }

    }

}
