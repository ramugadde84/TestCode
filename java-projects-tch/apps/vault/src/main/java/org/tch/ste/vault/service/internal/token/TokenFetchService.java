/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

import java.util.List;

import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.domain.entity.Token;

/**
 * Interface which exposes methods to :
 * - Fetch Permitted token requestor ARNs.
 * - Actual tokens themselves.
 * 
 * @author Karthik.
 *
 */
public interface TokenFetchService {
    /**
     * Fetches an active permitted token requestor for the given parameters.
     * Note: If CIID is null, then it is fetched at a TRID level.
     * 
     * @param arn String - The arn.
     * @param tokenRequestorId - The token requestor id, maps to NETWORK_TR_ID.
     * @param ciid String - The ciid.
     * @return PermittedTokenRequestorArn - The token requestor ARN if it exists, or Null if not.
     */
    PermittedTokenRequestorArn getPermittedTokenRequestorArn(String arn, String tokenRequestorId, String ciid);
    
    
    /**
     * Fetches the active tokens for the given parameters.
     * Note: If CIID is null, then it is fetched at a TRID level.
     * 
     * @param arn String - The arn.
     * @param tokenRequestorId - The token requestor id, maps to NETWORK_TR_ID.
     * @param ciid String - The ciid.
     * @return List<Token> - The active tokens.
     */
    List<Token> getTokens(String arn, String tokenRequestorId, String ciid);
}
