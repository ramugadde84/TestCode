/**
 * 
 */
package org.tch.ste.infra.repository;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.tch.ste.domain.constant.MivConstants;

/**
 * Proxy for Entity Manager Factory.
 * 
 * @author Karthik.
 * 
 */
public class SteEntityManagerFactory implements EntityManagerFactory {

    private Map<String, EntityManagerFactory> emFactories = new HashMap<String, EntityManagerFactory>();

    private String defaultPersistenceUnit;

    /**
     * Overloaded Constructor.
     * 
     * @param emFactories
     *            List<String> - Names of the persistence units.
     * @param defaultPersistenceUnit
     *            String - The default persistence unit.
     */
    public SteEntityManagerFactory(Map<String, EntityManagerFactory> emFactories, String defaultPersistenceUnit) {
        this.emFactories = emFactories;
        this.defaultPersistenceUnit = defaultPersistenceUnit;
    }

    /**
     * 
     * @return EntityManagerFactory - The factory which should be used for this
     *         request.
     */
    private EntityManagerFactory getThreadLocalEntityManagerFactory() {
        String currPersistenceUnit = (String) TransactionSynchronizationManager
                .getResource(MivConstants.CURRENT_PERSISTENCE_UNIT);
        if (currPersistenceUnit == null) {
            currPersistenceUnit = defaultPersistenceUnit;
        }
        return emFactories.get(currPersistenceUnit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#close()
     */
    @Override
    public void close() {
        this.getThreadLocalEntityManagerFactory().close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#createEntityManager()
     */
    @Override
    public EntityManager createEntityManager() {
        return this.getThreadLocalEntityManagerFactory().createEntityManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.persistence.EntityManagerFactory#createEntityManager(java.util.Map)
     */
    @Override
    public EntityManager createEntityManager(Map arg0) {
        return this.getThreadLocalEntityManagerFactory().createEntityManager(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#getCache()
     */
    @Override
    public Cache getCache() {
        return this.getThreadLocalEntityManagerFactory().getCache();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#getCriteriaBuilder()
     */
    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return this.getThreadLocalEntityManagerFactory().getCriteriaBuilder();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#getMetamodel()
     */
    @Override
    public Metamodel getMetamodel() {
        return this.getThreadLocalEntityManagerFactory().getMetamodel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#getPersistenceUnitUtil()
     */
    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return this.getThreadLocalEntityManagerFactory().getPersistenceUnitUtil();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#getProperties()
     */
    @Override
    public Map<String, Object> getProperties() {
        return this.getThreadLocalEntityManagerFactory().getProperties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.persistence.EntityManagerFactory#isOpen()
     */
    @Override
    public boolean isOpen() {
        return this.getThreadLocalEntityManagerFactory().isOpen();
    }

}
