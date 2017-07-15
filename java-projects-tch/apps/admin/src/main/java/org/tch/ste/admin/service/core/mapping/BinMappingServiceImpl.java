package org.tch.ste.admin.service.core.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.constant.BinType;
import org.tch.ste.admin.domain.dto.BinMapping;
import org.tch.ste.domain.entity.IisnBin;
import org.tch.ste.domain.entity.TokenBin;
import org.tch.ste.domain.entity.TokenBinMapping;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * @author pamartheepan
 * 
 */
@Service
public class BinMappingServiceImpl implements BinMappingService {

    @Autowired
    @Qualifier("iisnBinDao")
    private JpaDao<IisnBin, Integer> iisnBinDao;

    @Autowired
    @Qualifier("tokenBinDao")
    private JpaDao<TokenBin, Integer> tokenBinDao;

    @Autowired
    @Qualifier("tokenBinMappingDao")
    private JpaDao<TokenBinMapping, Integer> tokenBinMappingDao; // newly added

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.issuer.IssuerService#saveBinMapping(org
     * .tch.ste.admin.domain.dto.BinMapping)
     */
    @Override
    @Transactional(readOnly = false)
    public void saveBinMapping(BinMapping binMapping) {
        IisnBin iisnBin = saveIisnBinDetails(binMapping);
        TokenBin tokenBin = savetokenBinDetails(binMapping);
        saveTokenBinFrmIisn(binMapping);
        Integer id = binMapping.getId();
        if (id == null) {
            TokenBinMapping retVal = saveTokenBinMappingDetails(iisnBin, tokenBin);
            binMapping.setId(retVal.getId());
        } else {
            TokenBinMapping tokenBinMapping = tokenBinMappingDao.get(id);
            tokenBinMapping.setIisnBin(iisnBin);
            tokenBinMapping.setTokenBin(tokenBin);

        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.mapping.BinMappingService#getBinMapping
     * (java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true)
    public BinMapping getBinMapping(Integer id) {
        TokenBinMapping tokenBinMapping = tokenBinMappingDao.get(id);
        return convertToDto(tokenBinMapping);
    }

    /**
     * Converts to the DTO.
     * 
     * @param tokenBinMapping
     *            TokenBinMapping - The token bin mapping.
     * @return BinMapping - the bin mapping.
     */
    private static BinMapping convertToDto(TokenBinMapping tokenBinMapping) {
        // FIXME Do a join fetch later.
        BinMapping retVal = new BinMapping();
        retVal.setId(tokenBinMapping.getId());
        retVal.setRealBin(tokenBinMapping.getIisnBin().getBin());
        retVal.setTokenBin(tokenBinMapping.getTokenBin().getTokenBin());
        return retVal;
    }

    /**
     * Save Iisn details.
     * 
     * @param BinMapping
     *            binMapping - The bin mapping.
     * @return IisnBin - the iisn bin.
     */
    private IisnBin saveIisnBinDetails(BinMapping binMapping) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_BIN, binMapping.getRealBin());
        IisnBin iisnBin = ListUtil.getFirstOrNull(iisnBinDao.findByName(AdminConstants.GET_EXISTING_BINS, params));
        if (iisnBin == null) {
            iisnBin = new IisnBin();
            iisnBin.setIisn(binMapping.getIisn());
            iisnBin.setBin(binMapping.getRealBin());
            iisnBin.setBinType(BinType.REAL_BIN.toString());
        }
        return iisnBinDao.save(iisnBin);
    }

    /**
     * Save Token bin values in IISN table .
     * 
     * @param BinMapping
     *            binMapping - The bin mapping.
     */
    private void saveTokenBinFrmIisn(BinMapping binMapping) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.PARAM_BIN, binMapping.getTokenBin());
        IisnBin iisnBin = ListUtil.getFirstOrNull(iisnBinDao.findByName(AdminConstants.GET_EXISTING_BINS, params));
        if (iisnBin == null) {
            iisnBin = new IisnBin();
            iisnBin.setIisn(binMapping.getIisn());
            iisnBin.setBin(binMapping.getTokenBin());
            iisnBin.setBinType(BinType.TOKEN_BIN.toString());
        }
        iisnBinDao.save(iisnBin);
    }

    /**
     * Save TokenBin details.
     * 
     * @param BinMapping
     *            binMapping - The bin mapping.
     * @return TokenBin - the token bin.
     */
    private TokenBin savetokenBinDetails(BinMapping binMapping) {
        Map<String, Object> params = new HashMap<String, Object>();
        String myTokenBin = binMapping.getTokenBin();
        params.put(AdminConstants.PARAM_BIN, myTokenBin);
        TokenBin tokenBin = ListUtil.getFirstOrNull(tokenBinDao.findByName(AdminConstants.GET_EXISTING_TOKEN_BINS,
                params));
        if (tokenBin == null) {
            tokenBin = new TokenBin();
            tokenBin.setTokenBin(myTokenBin);
        }
        return tokenBinDao.save(tokenBin);
    }

    /**
     * Save TokenBinMapping details.
     * 
     * @param tokenBinMapping
     *            TokenBinMapping - The token bin mapping.
     * @return BinMapping - the bin mapping.
     */
    private TokenBinMapping saveTokenBinMappingDetails(IisnBin iisnBin, TokenBin tokenBin) {
        TokenBinMapping tokenBinMapping = new TokenBinMapping();
        tokenBinMapping.setIisnBin(iisnBin);
        tokenBinMapping.setTokenBin(tokenBin);
        tokenBinMappingDao.save(tokenBinMapping);
        return tokenBinMapping;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.mapping.BinMappingService#getBinMappings
     * (java.lang.String)
     */
    @Override
    public List<BinMapping> getBinMappings(String iisn) {
        List<TokenBinMapping> binMappings = tokenBinMappingDao.findByName(AdminConstants.GET_BIN_MAPPINGS,
                new HashMap<String, Object>());
        List<BinMapping> retVal = new ArrayList<BinMapping>();
        for (TokenBinMapping binMapping : binMappings) {
            retVal.add(convertToDto(binMapping));
        }
        return retVal;
    }
}
