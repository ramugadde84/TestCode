/**
 * 
 */
package org.tch.ste.vault.service.internal.generation;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.AlphaNumericGenerator;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 */
@Service
public class UserNameGenerationServiceImpl implements UserNameGenerationService {

    private static final int NUM_SEED_BYTES = 30;

    private static final int NUM_CHOICES = 2;

    private static final int MAX_ALLOWED_CHARACTERS = 6;

    private static final char[] lowerCaseAlphabets = AlphaNumericGenerator.getLowerCaseAlphabets();

    private static final char[] digits = AlphaNumericGenerator.getDigits();

    private SecureRandom generator;

    /**
     * Default Constructor
     * 
     * @throws NoSuchAlgorithmException
     *             - Thrown.
     */
    public UserNameGenerationServiceImpl() throws NoSuchAlgorithmException {
        generator = SecureRandom.getInstance(InfraConstants.RANDOM_ALGORITHM);
        generator.setSeed(generator.generateSeed(NUM_SEED_BYTES));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.core.generation.UserNameGenerationService#generate
     * ()
     */
    @Override
    public String generate() {
        StringBuilder retVal = new StringBuilder();
        int type = generator.nextInt(NUM_CHOICES);
        retVal.append(generateRandomCharacter(type));
        retVal.append(generateRandomCharacter((type + 1) % NUM_CHOICES));
        for (int i = 0; i < MAX_ALLOWED_CHARACTERS; ++i) {
            retVal.append(generateRandomCharacter(generator.nextInt(NUM_CHOICES)));
        }
        return retVal.toString();
    }

    /**
     * Generates a random character.
     * 
     * @param type
     *            int - The type. 0 means alphabets. 1 means digits.
     * @return char - The generated character.
     */
    private char generateRandomCharacter(int type) {
        char retVal = '0';
        switch (type) {
        case 0:
            retVal = lowerCaseAlphabets[generator.nextInt(lowerCaseAlphabets.length)];
            break;
        case 1:
            retVal = digits[generator.nextInt(digits.length)];
            break;
        default:
            break;
        }
        return retVal;
    }

}
