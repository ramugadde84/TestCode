/**
 * 
 */
package org.tch.ste.test.auth.login;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for login in lock outs
 * 
 * @author kjanani
 * 
 */

public class LoginLockOutTestForIssuer2 extends LoginLockOutTestForIssuer1 {

    /**
     *             
     */
    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.auth.login.LoginLockOutTestForIssuer1#
     * getLoginAttemptsForLockOut()
     */
    @Override
    public int getLoginAttemptsForLockOut() {
        return 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.test.auth.login.LoginLockOutTestForIssuer1#getErrorMessage()
     */
    @Override
    public String getErrorMessage() {
        return "ICICI Error.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.test.auth.login.LoginLockOutTestForIssuer1#getLockoutMessage
     * ()
     */
    @Override
    public String getLockoutMessage() {
        return "ICICI Lockout.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.auth.base.BaseAuthTest#getIisn()
     */
    @Override
    public String getIisn() {
        return "123556";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.auth.base.BaseAuthTest#getTrId()
     */
    @Override
    public String getTrId() {
        return "123556";
    }

    /**
     * @throws Exception
     *             -
     */
    @Override
    @Test
    @Transactional
    public void testLockout() throws Exception {
        super.testLockout();
    }

}
