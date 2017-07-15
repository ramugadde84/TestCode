/**
 * 
 */
package org.tch.ste.infra.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.BeanUtil;
import org.tch.ste.infra.util.ReflectionUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * @param <T>
 *            - The entity class.
 * @param <ID>
 *            - The primary key.
 * 
 */
@Repository(value = "baseJpaDaoImpl")
public class BaseJpaDao<T, ID extends Serializable> implements PagingAndSortingJpaDao<T, ID> {

    private Class<T> myClass;

    private String getAllQuery;

    private String getCountQuery;

    @PersistenceContext
    private EntityManager em;

    /**
     * Default Constructor.
     */
    public BaseJpaDao() {
        // Only for instantiating this instance.
    }

    /**
     * Overloaded constructor for use by actual DAO classes.
     * 
     * @param clazz
     *            Class<T> - The class of the entity.
     */
    public BaseJpaDao(Class<T> clazz) {
        myClass = clazz;
        String modelName = myClass.getSimpleName();

        getAllQuery = "select i from " + modelName + " i ";
        getCountQuery = "select COUNT(i) from " + modelName + " i";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseDao#save(java.lang.Object)
     */
    @Override
    public T save(T obj) {
        ID id = ReflectionUtil.getAnnotatedValue(obj, Id.class);
        T retVal;
        if (id == null || (retVal = em.find(myClass, id)) == null) {
            em.persist(obj);
            retVal = obj;
        } else {
            BeanUtil.copyObject(retVal, obj);
        }
        return retVal;
    }

    /**
     * Fetches the thread local entity manager.
     * 
     * @return EntityManager - The entitymanager.
     */
    private EntityManager getEntityManager() {
        return this.em;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseDao#get(java.io.Serializable)
     */
    @Override
    public T get(ID id) {
        return getEntityManager().find(myClass, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseDao#delete(java.lang.Object)
     */
    @Override
    public void delete(T obj) {
        getEntityManager().remove(obj);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseDao#getAll()
     */
    @Override
    public List<T> getAll() {
        return getEntityManager().createQuery(getAllQuery, myClass).getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseJpaDao#findByName(java.lang.String,
     * java.util.Map)
     */
    @Override
    public List<T> findByName(String name, Map<String, Object> params) {
        TypedQuery<T> query = getEntityManager().createNamedQuery(name, myClass);
        addParamsToQuery(params, query);
        return query.getResultList();
    }

    /**
     * Adds all the parameters to the query.
     * 
     * @param params
     *            Map<String,Object> - The params.
     * @param query
     *            TypedQuery<T> - The query.
     */
    private static <T> void addParamsToQuery(Map<String, Object> params, TypedQuery<T> query) {
        if (params != null) {
            for (Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseJpaDao#findByQuery(java.lang.String,
     * java.util.Map)
     */
    @Override
    public List<T> findByQuery(String query, Map<String, Object> params) {
        TypedQuery<T> myQuery = getEntityManager().createQuery(query, myClass);
        addParamsToQuery(params, myQuery);
        return myQuery.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.tsc.miv.repository.BaseJpaDao#updateByName(java.lang.String,
     * java.util.Map)
     */
    @Override
    public int updateByName(String name, Map<String, Object> params) {
        Query query = getEntityManager().createNamedQuery(name);
        addParamsToQuery(params, query);
        return query.executeUpdate();
    }

    /**
     * Add the parameters to the query.
     * 
     * @param params
     *            Map<String, Object> - The parameters.
     * @param query
     *            Query - The query.
     */
    private static void addParamsToQuery(Map<String, Object> params, Query query) {
        if (params != null) {
            for (Entry<String, Object> param : params.entrySet()) {
                query.setParameter(param.getKey(), param.getValue());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.tsc.miv.repository.BaseJpaDao#updateByQuery(java.lang.String,
     * java.util.Map)
     */
    @Override
    public int updateByQuery(String query, Map<String, Object> params) {
        Query myQuery = getEntityManager().createQuery(query);
        addParamsToQuery(params, myQuery);
        return myQuery.executeUpdate();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.infra.repository.Dao#count()
     */
    @SuppressWarnings("unchecked")
    @Override
    public long count() {
        Query countQuery = getEntityManager().createQuery(getCountQuery);
        List<Object> values = countQuery.getResultList();
        Long retVal = (Long) ((!values.isEmpty()) ? values.get(0) : 0);
        return retVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.repository.PagingAndSortingJpaDao#getAllPaged(org.tch
     * .ste.infra.repository.PageSort)
     */
    @Override
    public List<T> getAllPaged(PageSort pager) {
        StringBuilder query = new StringBuilder(getAllQuery);
        applySortingCriteria(query, pager);
        TypedQuery<T> myQuery = getEntityManager().createQuery(query.toString(), myClass);
        applyPaging(myQuery, pager);
        return myQuery.getResultList();
    }

    /**
     * Applies the paging to the query.
     * 
     * @param query
     *            TypedQuery<T> - The query.
     * @param pager
     *            PageSort - The paging criteria.
     */
    protected void applyPaging(TypedQuery<T> query, PageSort pager) {
        Integer start = pager.getStart();
        if (start != null) {
            query.setFirstResult(start);
        }
        Integer max = pager.getMax();
        if (max != null) {
            query.setMaxResults(max);
        }
    }

    /**
     * Applies the sorting criteria to the query.
     * 
     * @param query
     *            StringBuilder - The query.
     * @param pager
     *            PageSort - The sorting criteria.
     */
    protected void applySortingCriteria(StringBuilder query, PageSort pager) {
        List<String> sortCriteria = pager.getSortCriteria();
        if (sortCriteria != null && !sortCriteria.isEmpty()) {
            query.append(InfraConstants.ORDER_BY_CLAUSE);
            boolean isNotFirst = false;
            for (String sortCriterion : sortCriteria) {
                if (isNotFirst) {
                    query.append(',');
                } else {
                    isNotFirst = true;
                }
                query.append(sortCriterion);
            }
            query.append(pager.getSortType().toString());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.repository.PagingAndSortingJpaDao#findByNamePaged(java
     * .lang.String, java.util.Map, org.tch.ste.infra.repository.PageSort)
     */
    @Override
    public List<T> findByNamePaged(String name, Map<String, Object> params, PageSort pager) {
        TypedQuery<T> query = getEntityManager().createNamedQuery(name, myClass);
        addParamsToQuery(params, query);
        applyPaging(query, pager);
        return query.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.repository.PagingAndSortingJpaDao#findByQueryPaged(
     * java.lang.String, java.util.Map, org.tch.ste.infra.repository.PageSort)
     */
    @Override
    public List<T> findByQueryPaged(String query, Map<String, Object> params, PageSort pager) {
        StringBuilder sortingQuery = new StringBuilder(query);
        applySortingCriteria(sortingQuery, pager);
        TypedQuery<T> myQuery = getEntityManager().createQuery(sortingQuery.toString(), myClass);
        addParamsToQuery(params, myQuery);
        applyPaging(myQuery, pager);
        return myQuery.getResultList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.infra.repository.JpaDao#saveAndFlush(java.lang.Object)
     */
    @Override
    public T saveAndFlush(T obj) {
        T retVal = save(obj);
        em.flush();
        return retVal;
    }

}
