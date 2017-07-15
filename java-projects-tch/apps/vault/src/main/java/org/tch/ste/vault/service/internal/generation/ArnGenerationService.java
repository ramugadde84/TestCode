/**
 * 
 */
package org.tch.ste.vault.service.internal.generation;

import org.tch.ste.domain.entity.Arn;

/**
 * Generates Unique ARN and validates with ARNS table for unique ARN.
 * 
 * @author sujathas
 * 
 */
public interface ArnGenerationService {

    /**
     * 
     * @param iisn
     * @param bin
     * @return Generates the unique ARN and validates with ARN table for unique.
     */
    Arn generate(String iisn, String bin);

}
