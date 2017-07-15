/**
 * 
 */
package org.tch.ste.auth.constant;

/**
 * Query constants.
 * 
 * @author Karthik.
 * 
 */
public interface AuthQueryConstants {
	/**
	 * IISN.
	 */
	String PARAM_IISN = "iisn";
	/**
	 * Fetch the issuer using the iisn.
	 */
	String GET_ISSUER_BY_IISN = "getIssuerByIisn";
	/**
	 * The user id.
	 */
	String PARAM_USER_ID = "userId";
	/**
	 * Find Payment Instruments By Customer.
	 */
	String FIND_PAYMENT_INSTRUMENTS_BY_CUSTOMER = "findPaymentInstrumentsByCustomer";
	/**
	 * User Name parameter.
	 */
	String PARAM_USER_NAME = "userName";
	/**
	 * Get Customer by User Name.
	 */
	String GET_CUSTOMER_BY_USER_NAME = "getCustomerByUserName";
	/**
	 * Get Token Requestor by Network ID.
	 */
	String GET_TOKEN_REQUESTOR_BY_TRID = "getTokenRequestorByTrid";
	/**
	 * Get Issuer Token Requestor .
	 */
	String GET_ISSUER_TOKEN_REQUESTOR = "getIssuerTokenRequestorByTridAndIisn";
        /**
     * Tr Id.
     */
    String PARAM_TRID = "trId";
    /**
     * Fetches token requestor by Tr Id.
     */
    String GET_TOKEN_REQUESTOR_BY_TR_ID = "getTokenRequestorByTrId";
	/**
	 * Get Permitted Token Requestor Arn .
	 */
	String CHECK_TOKEN_REQUESTOR_ID_ARN = "checkTokenRequestorIdArn";

}
