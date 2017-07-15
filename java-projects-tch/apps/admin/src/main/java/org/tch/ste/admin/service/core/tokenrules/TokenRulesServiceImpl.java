/**
 * 
 */
package org.tch.ste.admin.service.core.tokenrules;

import static org.tch.ste.admin.constant.AdminConstants.GET_BIN_MAPPINGS;
import static org.tch.ste.admin.constant.AdminConstants.GET_LIST_OF_BINS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.admin.constant.AdminConstants;
import org.tch.ste.admin.domain.dto.TokenRule;
import org.tch.ste.domain.entity.IisnBin;
import org.tch.ste.domain.entity.PanBin;
import org.tch.ste.domain.entity.TokenBinMapping;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.ListUtil;

/**
 * Provides information about tokens rules and their information.
 * 
 * @author ramug
 * 
 */
@Service
public class TokenRulesServiceImpl implements TokenRulesService {

    @Autowired
    @Qualifier("iisnBinDao")
    private JpaDao<IisnBin, Integer> iisnBinDao;

    @Autowired
    @Qualifier("panBinDao")
    private JpaDao<PanBin, Integer> panBinDao;

    @Autowired
    @Qualifier("tokenBinMappingDao")
    private JpaDao<TokenBinMapping, Integer> tokenBinMappingDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.tokenrules.TokenRulesService#get(java.
     * lang.String)
     */
    @Override
    public TokenRule get(String iisnBin) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.GET_IISN_BIN, iisnBin);
        PanBin panBin = ListUtil.getFirstOrNull(panBinDao.findByName(AdminConstants.GET_PAN_BINS, params));
        TokenRule tokenRule = null;
        if (panBin != null) {
            tokenRule = convertToDto(panBin);
        } else {
            tokenRule = new TokenRule();
        }
        return tokenRule;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.tokenrules.TokenRulesService#save(org.
     * tch.ste.admin.domain.dto.TokenRule)
     */

    @Override
    @Transactional(readOnly = false)
    public TokenRule save(TokenRule tokenRule) {
        PanBin panBin = convertToEntity(tokenRule);
        panBinDao.save(panBin);

        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.admin.service.core.tokenrules.TokenRulesService#getBins(java
     * .lang.String)
     */
    @Override
    public List<String> getBins(String iisn) {
        List<String> listOfBins = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(AdminConstants.iisn, iisn);
        List<IisnBin> listOfIisnBins = iisnBinDao.findByName(GET_LIST_OF_BINS, params);
        for (IisnBin iisnBin : listOfIisnBins) {
            listOfBins.add(iisnBin.getBin());
        }
        return listOfBins;

    }

    /**
     * Converts to the DTO.
     * 
     * @param panBin
     *            PanBin - The Pan Bin.
     * @return TokenRule - The token rule.
     */
    private static TokenRule convertToDto(PanBin panBin) {
        TokenRule tokenRule = new TokenRule();
        tokenRule.setDollarAmountForExpireDpi(String.valueOf(panBin.getTokenExpirationAmount()));
        tokenRule.setDpiPerDay(String.valueOf(panBin.getMaxTokensPerDay()));
        tokenRule.setId(panBin.getId());
        tokenRule.setRealBin(panBin.getRealBin());
        tokenRule.setTimeInMinutesAfterExpirationForReuse(String.valueOf(panBin.getMaxMinutesExpiredToken()));
        tokenRule.setTokenExpirationMinutes(String.valueOf(panBin.getTokenExpriationTimeInMinutes()));
        tokenRule.setTokenPullEnable(String.valueOf(panBin.getPullEnabled()));
        tokenRule.setTokenPushOnCreation(String.valueOf(panBin.getPushOnCreate()));
        tokenRule
                .setTokenExpireAfterSingleDollarAmountTransaction(String.valueOf(panBin.getMaxAmountForExpiredToken()));
        tokenRule.setNumberOfDetokenizedRequests(String.valueOf(panBin.getTokenExpirationUses()));
        return tokenRule;
    }

    /**
     * Converts to Entity.
     * 
     * @param tokenRule
     *            which contains token rule values.
     */
    private static PanBin convertToEntity(TokenRule tokenRule) {
        PanBin panBin = new PanBin();
        panBin.setMaxAmountForExpiredToken(Integer.valueOf(tokenRule.getTokenExpireAfterSingleDollarAmountTransaction()));
        panBin.setMaxMinutesExpiredToken(Integer.valueOf(tokenRule.getTimeInMinutesAfterExpirationForReuse()));
        panBin.setMaxTokensPerDay(Integer.valueOf(tokenRule.getDpiPerDay()));
        panBin.setPullEnabled(Integer.valueOf(tokenRule.getTokenPullEnable()));
        panBin.setPushOnCreate(Integer.valueOf(tokenRule.getTokenPushOnCreation()));
        panBin.setRealBin(tokenRule.getRealBin());
        panBin.setTokenExpirationAmount(Integer.valueOf(tokenRule.getDollarAmountForExpireDpi()));
        panBin.setTokenExpirationUses(Integer.valueOf(tokenRule.getNumberOfDetokenizedRequests()));
        panBin.setTokenExpriationTimeInMinutes(Integer.valueOf(tokenRule.getTokenExpirationMinutes()));
        panBin.setId(tokenRule.getId());
        return panBin;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.admin.service.core.tokenrules.TokenRulesService#
     * loadListOfTokenBins()
     */
    @Override
    public List<String> loadListOfTokenBins() {
        List<String> listOfTokenBins = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();
        List<TokenBinMapping> listOfTokenBinsMapping = tokenBinMappingDao.findByName(GET_BIN_MAPPINGS, params);
        for (TokenBinMapping tokenBinMapping : listOfTokenBinsMapping) {
            listOfTokenBins.add(tokenBinMapping.getIisnBin().getBin());
        }
        return listOfTokenBins;

    }

}
