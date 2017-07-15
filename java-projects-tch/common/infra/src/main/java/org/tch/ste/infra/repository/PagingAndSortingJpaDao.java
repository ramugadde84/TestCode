/**
 * 
 */
package org.tch.ste.infra.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface for paging and sorting using Jpa.
 * 
 * @author Karthik.
 * @param <T>
 *            T - The class type.
 * @param <ID>
 *            ID - The primary key type.
 * 
 */
public interface PagingAndSortingJpaDao<T, ID extends Serializable> extends JpaDao<T, ID> {
    /**
     * Gets all the records based on the paging and sorting criteria.
     * 
     * @param pager
     *            PageSort - The pager.
     * @return List<T> - The list of objects.
     */
    List<T> getAllPaged(PageSort pager);

    /**
     * Fetches the results of named query and paginates based on the parameter.
     * Note: The sorting criteria needs to be specified in the named query
     * itself.
     * 
     * @param name
     *            String - The name of the query.
     * @param params
     *            Map<String,Object> - The parameters.
     * @param pager
     *            PageSort - The pager.
     * @return List<T> - The result set or empty list if no matching results.
     */
    List<T> findByNamePaged(String name, Map<String, Object> params, PageSort pager);

    /**
     * Fetches the results of query and paginates based on the parameter.
     * 
     * @param query
     *            String - The query.
     * @param params
     *            Map<String,Object> - The parameters.
     * @param pager
     *            PageSort - The pager.
     * @return List<T> - The result set or empty list if no matching results.
     */
    List<T> findByQueryPaged(String query, Map<String, Object> params, PageSort pager);
}
