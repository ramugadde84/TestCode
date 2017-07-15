package org.tch.ste.infra.repository.support;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tch.ste.domain.constant.MivConstants;
import org.tch.ste.infra.constant.InfraConstants;

/**
 * Implements the interface as a Spring Bean.
 * 
 * @author Karthik.
 */
public class EntityManagerInjectorImpl implements EntityManagerInjector {

    private static Logger logger = LoggerFactory.getLogger(EntityManagerInjectorImpl.class);

    private Map<String, String> issuerToPuMapper;

    /**
     * Default Constructor.
     * 
     * @param issuerToPuMapper
     *            Map<String,String> - The issuer to persistence unit mapper.
     */
    public EntityManagerInjectorImpl(Map<String, String> issuerToPuMapper) {
        this.issuerToPuMapper = issuerToPuMapper;
    }

    /**
     * Returns the persistence unit id.
     * 
     * @param issuerId
     *            String - The issuer id.
     * @return String - The persistence unit id.
     */
    protected String getPersistenceUnit(String issuerId) {
        if (issuerToPuMapper.containsKey(issuerId)) {
            return issuerToPuMapper.get(issuerId);
        }

        return InfraConstants.PERSISTENCE_UNIT_COMMON;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.repository.support.EntityManagerInjector#inject(java
     * .lang.String)
     */
    @Override
    public boolean inject(String issuerId) {
        boolean retVal = false;
        if (!TransactionSynchronizationManager.hasResource(MivConstants.CURRENT_PERSISTENCE_UNIT)) {
            String persistenceUnitName = getPersistenceUnit(issuerId);
            logger.debug("Injecting Persistence Unit: {}", persistenceUnitName);
            TransactionSynchronizationManager.bindResource(MivConstants.CURRENT_PERSISTENCE_UNIT, persistenceUnitName);
            retVal = true;
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.repository.support.EntityManagerInjector#remove(java
     * .lang.String)
     */
    @Override
    public void remove(String issuerId) {
        logger.debug("Removing Injected Persistence Unit: ");
        TransactionSynchronizationManager.unbindResource(MivConstants.CURRENT_PERSISTENCE_UNIT);
    }

}
