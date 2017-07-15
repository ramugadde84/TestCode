package org.tch.ste.vault.service.internal.generation;

import org.tch.ste.domain.entity.Arn;

/**
 * @author sujathas
 * 
 */
public interface ArnGenerationFacade {

    /**
     * @param iisn
     * @param bin
     * @return Generates Unique ARN NO
     */
    Arn generateArn(String iisn, String bin);

}
