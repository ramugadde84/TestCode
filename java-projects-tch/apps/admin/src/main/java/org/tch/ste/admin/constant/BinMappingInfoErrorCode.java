package org.tch.ste.admin.constant;

/**
 * @author pamartheepan
 * 
 */
public enum BinMappingInfoErrorCode {

    /**
     * 
     */
    REAL_BIN_ALREADY_EXISTS,

    /**
     * 
     */
    TOKEN_BIN_ALREADY_EXISTS,

    /**
     * 
     */
    FIRIST_DIGIT_VALIDATION_FOR_REAL_AND_TOKEN_BIN,

    /**
     * 
     */
    BIN_NETWORK_FIRST_DIGIT_VALIDATOR,

    /**
     * Real bin should not be same as Token bin.
     */
    REAL_BIN_SHOULD_NOT_BE_SAME_AS_TOKEN_BIN,
    /**
     * Token bin should not be same as Real bin.
     */
    TOKEN_BIN_SHOULD_NOT_BE_SAME_AS_REAL_BIN,
    /**
     * Bins must not be the same.
     */
    BINS_CANNOT_BE_SAME,
    /**
     * Token bin should not be same as Real bin.
     */
    REAL_BIN_USED_OTHER_TOKEN_BIN_MAPPING,
    /**
     * Token bin should not map other IISN.
     */
    TOKEN_BIN_SHOULD_NOT_MAP_OTHER_IISN,
    /**
     * Real bin should not map other IISN.
     */
    REAL_BIN_SHOULD_NOT_MAP_OTHER_IISN;
}
