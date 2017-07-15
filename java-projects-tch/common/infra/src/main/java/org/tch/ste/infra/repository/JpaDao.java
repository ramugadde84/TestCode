/**
 * 
 */
package org.tch.ste.infra.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Extends the base interface and introduces JPA specific methods.
 * 
 * @author Karthik.
 * @param <T>
 *            - The entity.
 * @param <ID>
 *            - The primary key.
 * 
 */
public interface JpaDao<T, ID extends Serializable> extends Dao<T, ID> {
    
    /**
     * Saves and flushes the object.
     * 
     * @param obj T - The object.
     * @return T - The object.
     */
    T saveAndFlush(T obj);

    /**
     * Fetches the objects using the provided named query.
     * 
     * @param name
     *            String - The name of the named query.
     * @param params
     *            Map<String,Object> - The parameters which are passed to the
     *            named query.
     * @return List<T> - The objects which match the query or an empty list if
     *         none match.
     */
    List<T> findByName(String name, Map<String, Object> params);

    /**
     * Fetches the objects using the provided query.
     * 
     * @param query
     *            String - The JPQL query.
     * @param params
     *            Map<String,Object> - The parameters for the query.
     * @return List<T> - The objects which match the query or an empty list if
     *         none match.
     */
    List<T> findByQuery(String query, Map<String, Object> params);

    /**
     * Updates based on the provided named query.
     * 
     * @param name
     *            String - The name of the named query.
     * @param params
     *            Map<String,Object> - The parameters passed to the named query.
     * @return int - The number of rows which were updated.
     */
    int updateByName(String name, Map<String, Object> params);

    /**
     * Updates based on the provided named query.
     * 
     * @param query
     *            String - The query.
     * @param params
     *            Map<String,Object> - The parameters passed to the named query.
     * @return int - The number of rows which were updated.
     */
    int updateByQuery(String query, Map<String, Object> params);
}
