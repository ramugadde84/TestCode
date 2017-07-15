/**
 * 
 */
package org.tch.ste.vault.test.paymentinstrumentdeactivation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalIntegrationTest;
import org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationFacade;

/**
 * @author pamartheepan
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class PaymentInstrumentDeactivationTest extends BaseTest {

    private static final String pan = "234";
    private static final String date = "2016";
    private static final String iisn = "123456";
    private static final String arn = "205";

    @Autowired
    @Qualifier("hdfcJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    TokenDeactivationFacade paymentInstrumentDeactivationFacade;

    /**
     * Validate to deactivate token using pan and date.
     * 
     */
    @Test
    public void tokenDeactivationUsingPanAndDateTest() {
        jdbcTemplate.update("UPDATE TOKENS SET IS_ACTIVE= ?", Boolean.TRUE);
        paymentInstrumentDeactivationFacade.tokenDeactivation(iisn, pan, date);
        int rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TOKENS WHERE  IS_ACTIVE =? ", Integer.class,
                Boolean.FALSE);
        assertTrue(rowCount == 1);
    }

    /**
     * validate to deactivate token using arn.
     */
    @Test
    public void tokenDeactivationUsingArnTest() {
        jdbcTemplate.update("UPDATE TOKENS SET IS_ACTIVE= ?", Boolean.TRUE);
        paymentInstrumentDeactivationFacade.tokenDeactivation(iisn, arn);
        int rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TOKENS WHERE  IS_ACTIVE =? ", Integer.class,
                Boolean.FALSE);
        assertTrue(rowCount == 1);
    }

}
