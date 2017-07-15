/**
 * 
 */
package org.tch.ste.auth.service.core.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.auth.constant.AuthConstants;
import org.tch.ste.auth.service.internal.lockoutuser.LockedOutUsersReportGenerationService;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.infra.repository.JpaDao;

/**
 * LockoutUsers Batch Watcher initiator.
 * 
 * @author ramug
 * 
 */
public class LockedOutUsersReportGenerationBatchWatchInitiator {

    @Autowired
    private LockedOutUsersReportGenerationService lockoutUserService;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    /**
     * This method is start up file for batch process.
     */
    public void lockedOutUsersReportGenerationWatchInitiator() {
        for (IssuerConfiguration issuerConfiguration : issuerConfigurationDao.getAll()) {
            int authenticationType = issuerConfiguration.getAuthenticationType();
            if (AuthConstants.AUTHENTICATION_TYPE_THREE == authenticationType
                    || AuthConstants.AUTHENTICATION_TYPE_FOUR == authenticationType) {
                lockoutUserService.generateLockoutUsersReport(issuerConfiguration.getIisn());
            }
        }
    }

}
