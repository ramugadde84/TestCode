package org.tch.ste.admin.service.core.mapping;

import java.util.List;

import org.tch.ste.admin.domain.dto.BinMapping;

/**
 * @author pamartheepan
 * 
 */
public interface BinMappingService {
    /**
     * Save the Bin mapping details.
     * 
     * @param binMapping
     *            - Bin Mapping details to be stored.
     */
    public void saveBinMapping(BinMapping binMapping);

    /**
     * Get the bin mapping details based on the id.
     * 
     * @param id
     *            Integer - The id.
     * @return BinMapping - The bin mapping value.
     */
    public BinMapping getBinMapping(Integer id);

    /**
     * Fetch List of Bins by based on IISN.
     * 
     * @param iisn
     *            String - The iisn.
     * @return List of bins.
     */
    public List<BinMapping> getBinMappings(String iisn);
}
