/**
 * 
 */
package org.tch.ste.infra.repository;

import java.io.Serializable;
import java.util.List;

/**
 * A standard DAO interface.
 * 
 * @author Karthik.
 * @param <T>
 *            - The entity for which the DAO is instantiated.
 * @param <ID>
 *            - The primary key for the above entity.
 * 
 */
public interface Dao<T, ID extends Serializable> {
    /**
     * Saves the object.
     * 
     * @param obj
     *            Object - The object.
     * @return T - The saved object.
     */
    T save(T obj);

    /**
     * Fetches the object with the given ID.
     * 
     * @param id
     *            ID - The id of the object.
     * @return T - The object if it exists, null if it does not.
     */
    T get(ID id);

    /**
     * Deletes the object.
     * 
     * @param obj
     *            T - The object to be deleted.
     */
    void delete(T obj);

    /**
     * Fetches all the objects which are of this entity type.
     * 
     * @return List<T> - The list of objects.
     */
    List<T> getAll();

    /**
     * Returns the number of records.
     * 
     * @return long - The number of records in the repository.
     */
    long count();
}
