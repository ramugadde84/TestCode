/**
 * 
 */
package org.tch.ste.admin.service.internal.user;

/**
 * Provides methods to search users.
 * 
 * @author Karthik.
 * 
 */
public interface UserSearchService {
    /**
     * Checks whether the user with the given id is present.
     * 
     * @param userId
     *            String - The user id.
     * @return boolean - True if exists, false otherwise.
     */
    boolean checkForUser(String userId);
}
