package org.tch.ste.infra.repository.support;

/**
 * This interface exposes methods to inject and remove an entity manager.
 * 
 * @author Karthik.
 * 
 */
public interface EntityManagerInjector {

    /**
     * Injects the provided issuer id. If it returns false, the caller <i>should
     * not</i> call remove.
     * 
     * @param issuerId
     *            String - The issuer id.
     * @return boolean - True if the em was injected, false if the em was
     *         already present.
     */
    boolean inject(String issuerId);

    /**
     * Removes the issuer id.
     * 
     * @param issuerId
     *            String - The issuer id.
     */
    void remove(String issuerId);
}
