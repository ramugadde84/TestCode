package org.tch.ste.infra.service.core.security;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation for HashingService
 * 
 * @author sujathas
 * 
 * 
 */
@Service
public class HashingServiceImpl implements HashingService {

    private static final Long SALT = 96532890176L;
    private ShaPasswordEncoder hashEncoder = new ShaPasswordEncoder(512);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.service.core.security.HashingService#hash(java.lang
     * .String)
     */
    @Override
    public String hash(String value) {
        return hashEncoder.encodePassword(value, SALT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.infra.service.core.security.HashingService#hash(java.lang
     * .String, java.lang.Object)
     */
    @Override
    public String hash(String value, Object salt) {
        return hashEncoder.encodePassword(value, salt);
    }
}
