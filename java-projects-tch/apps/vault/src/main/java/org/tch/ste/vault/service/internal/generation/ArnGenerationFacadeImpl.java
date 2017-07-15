package org.tch.ste.vault.service.internal.generation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.Arn;

/**
 * @author sujathas
 * 
 */
@Service
public class ArnGenerationFacadeImpl implements ArnGenerationFacade {

    @Autowired
    private ArnGenerationService arnGenerationService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.generation.ArnGenerationFacade#generateArn
     * (java.lang.String, java.lang.String)
     */
    @Override
    public Arn generateArn(String iisn, String bin) {

        return arnGenerationService.generate(iisn, bin);

    }
}
