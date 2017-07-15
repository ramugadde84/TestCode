package org.tch.ste.admin.service.core.tokenrules;

import java.util.List;

import org.tch.ste.admin.domain.dto.TokenRule;

/**
 * Exposes methods for Token Rules manipulation.
 * 
 * @author ramug
 * 
 */
public interface TokenRulesService {
    /**
     * Return the token rule for the given bin.
     * 
     * @param iisnBin
     *            String - The bin.
     * @return TokenRule - The token rule.
     */
    TokenRule get(String iisnBin);

    /**
     * Saves the token in the DB.
     * 
     * @param tokenRule
     *            TokenRule - The token rule dto.
     * @return TokenRule - The token rule which is saved.
     */
    TokenRule save(TokenRule tokenRule);

    /**
     * Fetches the real bins for the issuer.
     * 
     * @param iisn
     *            String - The iisn.
     * 
     * @return List<String> - The bins.
     */
    List<String> getBins(String iisn);

    /**
     * @return load list of token bins from token bin mapping.
     */
    List<String> loadListOfTokenBins();
}
