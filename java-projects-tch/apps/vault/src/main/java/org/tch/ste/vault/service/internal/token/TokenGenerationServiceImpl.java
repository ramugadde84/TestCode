/**
 * 
 */
package org.tch.ste.vault.service.internal.token;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.entity.TokenBinMapping;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.util.AlphaNumericGenerator;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.util.PanUtil;

/**
 * @author kjanani
 * 
 */
@Service
public class TokenGenerationServiceImpl implements TokenGenerationService {

    @Autowired
    @Qualifier("TokenBinMappingsDao")
    private JpaDao<TokenBinMapping, String> tokenBinMappingDao;

    private static final char[] digits = AlphaNumericGenerator.getDigits();

    private SecureRandom generator;
    private static final int NUM_SEED_BYTES = 30;

    /**
     * Default Constructor
     * 
     * @throws NoSuchAlgorithmException
     *             - Thrown.
     */
    public TokenGenerationServiceImpl() throws NoSuchAlgorithmException {
        generator = SecureRandom.getInstance(InfraConstants.RANDOM_ALGORITHM);
        generator.setSeed(generator.generateSeed(NUM_SEED_BYTES));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.generation.TokenGenerationService#
     * generate(int, java.lang.String)
     */
    @Override
    public String generate(int length, String realBin) {
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put(VaultQueryConstants.PARAM_REALBIN, realBin);
        TokenBinMapping tokenBin1 = ListUtil.getFirstOrNull(tokenBinMappingDao.findByName(
                VaultQueryConstants.GET_TOKEN_BIN, param1));

        String panDigit = tokenBin1.getTokenBin().getTokenBin();

        String randomNumber = createRandomToken(length, panDigit);
        StringBuilder generatedToken = new StringBuilder();

        int lastDigit = PanUtil.generateLuhnCheckDigit(randomNumber);

        generatedToken.append(randomNumber).append(lastDigit);

        return generatedToken.toString();
    }

    /**
     * Method to generate random digits
     * 
     * @return - Randomly generated digits
     */
    private String createRandomToken(int length, String panDigit) {

        StringBuilder retVal = new StringBuilder(panDigit);

        int numDigits = length - panDigit.length() - 1;
        for (int i = 0; i < numDigits; i++) {
            retVal.append(digits[generator.nextInt(digits.length)]);
        }

        return retVal.toString();
    }

}
