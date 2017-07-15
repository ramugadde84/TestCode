package org.tch.ste.infra.service.core.password;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.util.AlphaNumericGenerator;

/**
 * Implementation for PasswordGenerationService
 * 
 * @author kjanani
 * 
 */
@Service
public class PasswordGenerationServiceImpl implements PasswordGenerationService {

    private static final int NUM_SEED_BYTES = 30;

    private static final int NUM_TYPES = 3;

    private static final char[] lowerCaseAlphabets = AlphaNumericGenerator.getLowerCaseAlphabets();

    private static final char[] upperCaseAlphabets = AlphaNumericGenerator.getUpperCaseAlphabets();

    private static final char[] digits = AlphaNumericGenerator.getDigits();

    private SecureRandom generator;

    private ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(512);

    /**
     * Default Constructor
     * 
     * @throws NoSuchAlgorithmException
     *             - Thrown.
     */
    public PasswordGenerationServiceImpl() throws NoSuchAlgorithmException {
        generator = SecureRandom.getInstance(InfraConstants.RANDOM_ALGORITHM);
        generator.setSeed(generator.generateSeed(NUM_SEED_BYTES));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.service.core.PasswordGenerationService#generatePassword
     * ()
     */
    @Override
    public Password generatePassword() {
        Password password = new Password();
        String plainTextPassword = createRandomPassword();
        Long salt = generator.nextLong();
        String hashedPassword = passwordEncoder.encodePassword(plainTextPassword, salt);
        password.setHashedPassword(hashedPassword);
        password.setPasswordSalt(salt.toString());
        password.setPlainTextPassword(plainTextPassword);
        return password;
    }

    /**
     * Creates a random password.
     * 
     * @return String - A random password.
     */
    private String createRandomPassword() {
        StringBuilder retVal = new StringBuilder();
        int firstType = generator.nextInt(NUM_TYPES);
        retVal.append(generatePasswordCharacter(firstType));
        // We could have as easily done firstType+1 % NUM_TYPES but that would
        // make it slightly predictable.
        int secondType = generator.nextInt(NUM_TYPES);
        while (secondType == firstType) {
            secondType = generator.nextInt(NUM_TYPES);
        }
        retVal.append(generatePasswordCharacter(secondType));
        int thirdType = generator.nextInt(NUM_TYPES);
        while (thirdType == firstType || thirdType == secondType) {
            thirdType = generator.nextInt(NUM_TYPES);
        }
        retVal.append(generatePasswordCharacter(thirdType));
        for (int i = 3; i < InfraConstants.MIN_PASSWORD_LENGTH; ++i) {
            int nextType = generator.nextInt(NUM_TYPES);
            retVal.append(generatePasswordCharacter(nextType));
        }
        return retVal.toString();
    }

    /**
     * Generates a password character.
     * 
     * @param type
     *            int - The type of character to be generated.
     * @return char - The character.
     */
    private char generatePasswordCharacter(int type) {
        char retVal = '0';
        switch (type) {
        case 0:
            retVal = lowerCaseAlphabets[generator.nextInt(lowerCaseAlphabets.length)];
            break;
        case 1:
            retVal = upperCaseAlphabets[generator.nextInt(upperCaseAlphabets.length)];
            break;
        case 2:
            retVal = digits[generator.nextInt(digits.length)];
            break;
        default:
            break;
        }
        return retVal;
    }

}
