package org.tch.ste.vault.service.internal.generation;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.domain.entity.IisnBin;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.AlphaNumericGenerator;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;

/**
 * @author sujathas
 * 
 */
@Service
public class ArnGenerationServiceImpl implements ArnGenerationService {

    private static Logger logger = LoggerFactory.getLogger(ArnGenerationServiceImpl.class);

    private static final int NUM_SEED_BYTES = 30;

    private static final char[] digits = AlphaNumericGenerator.getDigits();

    @Autowired
    @Qualifier("iisnBinDao")
    private JpaDao<IisnBin, Integer> iisnBinDao;

    @Autowired
    @Qualifier("arnDao")
    private JpaDao<Arn, Integer> arnDao;

    private SecureRandom generator;

    /**
     * Default Constructor
     * 
     * @throws NoSuchAlgorithmException
     *             - Thrown.
     */
    public ArnGenerationServiceImpl() throws NoSuchAlgorithmException {
        generator = SecureRandom.getInstance(InfraConstants.RANDOM_ALGORITHM);
        generator.setSeed(generator.generateSeed(NUM_SEED_BYTES));
    }

    /*
	 * 
	 */

    @Override
    @Transactional(readOnly=false)
    public Arn generate(String iisn, String bin) {
        Arn retVal = null;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put(VaultQueryConstants.PARAM_BIN, bin);
        param.put(VaultQueryConstants.PARAM_IISN, iisn);
        if (ListUtil.getFirstOrNull(iisnBinDao.findByName(VaultQueryConstants.GET_IISN_BY_BIN, param)) != null) {
            retVal = generateArn(bin);
        } else {
            logger.error("Error in generating ARN. IISN {} or Real BIN {} not available in repository", iisn, bin);
            return null;
        }
        return retVal;
    }

    /**
     * @param bin
     *            Bin Value
     * @return Returns Random ARN generated
     */
    public String randNumber(String bin) {
        StringBuilder retVal = new StringBuilder(bin);
        int numDigits = VaultQueryConstants.ARN_LENGTH - bin.length();
        for (int i = 0; i < numDigits; i++) {
            retVal.append(digits[generator.nextInt(digits.length)]);
        }

        return retVal.toString();
    }

    /**
     * @param bin
     *            Bin Value
     * @return Returns ARN no
     */
    public Arn generateArn(String bin) {
        Arn arn = new Arn();
        for(int i=0; i < VaultConstants.MAX_RETRIES_FOR_UNIQUE_CREATION;++i) {
            String arnValue = randNumber(bin);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(VaultQueryConstants.PARAM_ARN_ID, arnValue);
            if (ListUtil.getFirstOrNull(arnDao.findByName(VaultQueryConstants.GET_ID_BY_ARN, params)) == null) {
                try {
                    arn.setArn(arnValue);
                    arn = arnDao.saveAndFlush(arn);
                    break;
                } catch (Throwable e) {
                    continue;
                }
            }
        }

        return arn;

    }
}
