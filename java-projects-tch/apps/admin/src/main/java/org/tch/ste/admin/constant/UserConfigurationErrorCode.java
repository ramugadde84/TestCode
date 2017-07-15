package org.tch.ste.admin.constant;

/**
 * Enums definig the Error Codes for User.
 * 
 * @author sharduls
 * 
 */
public enum UserConfigurationErrorCode {

    /**
     * User Already Exists
     */
    USER_ALREADY_EXISTS,

    /**
     * No Role Selected.
     * 
     */
    SHOULD_SELECT_ATLEAST_ONE_ROLE,
    /**
     * No Issuer Selected.
     * 
     */
    SHOULD_SELECT_AN_ISSUER,
    /**
     * User does not exist in active directory.
     */
    USER_NOT_EXISTS_IN_AD,
    /**
     * User saved successfully.
     */
    SAVE_USER_MESSAGE,
    /**
     * User deleted successfully.
     */
    DELETE_USER_MESSAGE;

}
