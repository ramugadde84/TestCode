/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.tch.ste.domain.constant.AuthenticationType;
import org.tch.ste.domain.dto.Password;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.IssuerConfiguration;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.repository.JpaDao;
import org.tch.ste.infra.service.core.password.PasswordGenerationService;
import org.tch.ste.infra.service.core.security.EncryptionService;
import org.tch.ste.infra.service.core.security.HashingService;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.ListUtil;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.constant.VaultQueryConstants;
import org.tch.ste.vault.service.internal.generation.ArnGenerationFacade;
import org.tch.ste.vault.service.internal.generation.UniqueUserNameGenerationService;
import org.tch.ste.vault.util.PanUtil;

/**
 * Implements the interface.
 * 
 * @author Karthik.
 * 
 * 
 */
public class PaymentInstrumentPreloadServiceImpl implements PaymentInstrumentPreloadService {

    private static final int HASH_SIZE = 10003;

    private static Logger logger = LoggerFactory.getLogger(PaymentInstrumentPreloadServiceImpl.class);

    /**
     * Inner class.
     * 
     * 
     * @author Karthik.
     * 
     */
    private static class UserNamePasswordHolder {
        private String userName;

        private Password password;

        /**
         * /** Default Constructor.
         */
        public UserNamePasswordHolder() {
            // Empty.
        }

        /**
         * Returns the field userName.
         * 
         * @return userName String - Get the field.
         */
        public String getUserName() {
            return userName;
        }

        /**
         * Sets the field userName.
         * 
         * @param userName
         *            String - Set the field userName.
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * Returns the field password.
         * 
         * @return password Password - Get the field.
         */
        public Password getPassword() {
            return password;
        }

        /**
         * Sets the field password.
         * 
         * @param password
         *            Password - Set the field password.
         */
        public void setPassword(Password password) {
            this.password = password;
        }

    }

    /**
     * Customer response static class created.
     */
    private static class CustomerResponse {
        private Customer customer;

        private PaymentInstrumentPreloadRecordResponse paymentInstrumentPreloadRecordResponse;

        /**
         * 
         */
        public CustomerResponse() {
            // TODO Auto-generated constructor stub
        }

        /**
         * Returns the field customer.
         * 
         * @return customer Customer - Get the field.
         */
        public Customer getCustomer() {
            return customer;
        }

        /**
         * Sets the field customer.
         * 
         * @param customer
         *            Customer - Set the field customer.
         */
        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        /**
         * Returns the field paymentInstrumentPreloadRecordResponse.
         * 
         * @return paymentInstrumentPreloadRecordResponse
         *         PaymentInstrumentPreloadRecordResponse - Get the field.
         */
        public PaymentInstrumentPreloadRecordResponse getPaymentInstrumentPreloadRecordResponse() {
            return paymentInstrumentPreloadRecordResponse;
        }

        /**
         * Sets the field paymentInstrumentPreloadRecordResponse.
         * 
         * @param paymentInstrumentPreloadRecordResponse
         *            PaymentInstrumentPreloadRecordResponse - Set the field
         *            paymentInstrumentPreloadRecordResponse.
         */
        public void setPaymentInstrumentPreloadRecordResponse(
                PaymentInstrumentPreloadRecordResponse paymentInstrumentPreloadRecordResponse) {
            this.paymentInstrumentPreloadRecordResponse = paymentInstrumentPreloadRecordResponse;
        }
    }

    @Autowired
    @Qualifier("paymentInstrumentDao")
    private JpaDao<PaymentInstrument, Integer> paymentInstrumentDao;

    @Autowired
    @Qualifier("customerDao")
    private JpaDao<Customer, Integer> customerDao;

    @Autowired
    @Qualifier("issuerConfigurationDao")
    private JpaDao<IssuerConfiguration, Integer> issuerConfigurationDao;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private UniqueUserNameGenerationService userNameGenerationService;

    @Autowired
    private ArnGenerationFacade arnGenerationFacade;

    @Autowired
    private HashingService hashingService;

    @Autowired
    private PasswordGenerationService passwordGenerationService;

    @Autowired
    private PaymentInstrumentPreloadValidationService validationService;

    @Autowired
    private PaymentInstrumentPreloadUtil paymentInstrumentPreloadUtil;

    private Map<String, Boolean> enteredValues = new HashMap<String, Boolean>(HASH_SIZE);

    private Map<String, UserNamePasswordHolder> generatedPasswords = new HashMap<String, UserNamePasswordHolder>(
            HASH_SIZE);

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument.
     * PaymentInstrumentPreloadService#addPaymentInstrument(java.lang.String,
     * org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument.
     * PaymentInstrumentPreloadContent)
     */
    @Override
    @Transactional(readOnly = false)
    public PaymentInstrumentPreloadRecordResponse addPaymentInstrument(String iisn,
            PaymentInstrumentPreloadContent preloadContent) {
        PaymentInstrumentPreloadRecordResponse retVal = null;
        BatchResponseCode responseCode;
        String pan = StringUtil.stripLeadingZeros(preloadContent.getPan());
        if ((responseCode = validationService.validate(iisn, preloadContent)) == BatchResponseCode.SUCCCESS) {
            preloadContent.setPan(pan);
            String panCustId = concatPanAndCustomerId(preloadContent);
            if (!enteredValues.containsKey(panCustId)) {
                enteredValues.put(panCustId, Boolean.TRUE);
                Map<String, Object> params = new HashMap<String, Object>();
                try {
                    String encPan = encryptionService.encrypt(pan);
                    params.put(VaultQueryConstants.PARAM_ENC_PAN, encPan);
                    params.put(VaultQueryConstants.PARAM_CUSTOMER_ID, preloadContent.getIssuerCustomerId());
                    PaymentInstrument existingPaymentInstrument = null;
                    if ((existingPaymentInstrument = ListUtil.getFirstOrNull(paymentInstrumentDao.findByName(
                            VaultQueryConstants.FIND_BY_PAN_AND_CUSTOMER_ID, params))) == null) {
                        retVal = doAdd(iisn, preloadContent, encPan);
                    } else {
                        retVal = doUpdate(iisn, preloadContent, existingPaymentInstrument);
                    }
                } catch (SteException e) {
                    retVal = new PaymentInstrumentPreloadRecordResponse();
                    retVal.setResponseCode(BatchResponseCode.UNEXPECTED_ERROR_DURING_PROCESSING.getResponseStr());
                }

            } else {
                retVal = new PaymentInstrumentPreloadRecordResponse();
                retVal.setResponseCode(BatchResponseCode.DUPLICATE_RECORD_IN_FILE.getResponseStr());
            }
        } else {
            retVal = new PaymentInstrumentPreloadRecordResponse();
            preloadContent.setPan(pan);
            retVal.setResponseCode(responseCode.getResponseStr());
        }
        paymentInstrumentPreloadUtil.copyValues(retVal, preloadContent);
        return retVal;
    }

    /**
     * Concatenates the pan and expiry date.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @return String - The concatenated value.
     */
    private static String concatPanAndCustomerId(PaymentInstrumentPreloadContent preloadContent) {
        StringBuilder retVal = new StringBuilder(preloadContent.getPan());
        retVal.append(VaultConstants.FILE_PART_SEPERATOR);
        retVal.append(preloadContent.getIssuerCustomerId());
        return retVal.toString();
    }

    /**
     * Updates the payment instrument.
     * 
     * @param iisn
     *            String - The IISN.
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The preload content.
     * @param existingPaymentInstrument
     *            PaymentInstrument - The existing instrument.
     * @return PaymentInstrumentPreloadRecordResponse- The response.
     */
    private PaymentInstrumentPreloadRecordResponse doUpdate(String iisn,
            PaymentInstrumentPreloadContent preloadContent, PaymentInstrument existingPaymentInstrument) {
        PaymentInstrumentPreloadRecordResponse response = new PaymentInstrumentPreloadRecordResponse();
        response.setResponseCode(BatchResponseCode.SUCCCESS.getResponseStr());
        response.setArn(existingPaymentInstrument.getArn().getArn());
        Customer customer = existingPaymentInstrument.getCustomer();
        response.setUserName(customer.getUserName());
        copyValues(preloadContent, existingPaymentInstrument);
        paymentInstrumentDao.save(existingPaymentInstrument);
        return response;
    }

    /**
     * Does the adding.
     * 
     * @param iisn
     *            String - The IISN.
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @param encPan
     *            String - The encrypted PAN.
     * @return PaymentInstrumentPreloadRecordResponse - The response.
     */
    private PaymentInstrumentPreloadRecordResponse doAdd(String iisn, PaymentInstrumentPreloadContent preloadContent,
            String encPan) {
        PaymentInstrumentPreloadRecordResponse retVal = null;
        String successStr = BatchResponseCode.SUCCCESS.getResponseStr();
        IssuerConfiguration issuerConfiguration = getIssuerConfiguration(iisn);
        if (AuthenticationType.TCH_HOSTED_WITH_GENERATED_CREDS.getType() == issuerConfiguration.getAuthenticationType()) {
            Arn arn = arnGenerationFacade.generateArn(iisn, PanUtil.getBinFromPan(preloadContent.getPan()));
            if (arn != null) {
                CustomerResponse customerResponse = generateUserNameAndPassword(preloadContent, iisn);
                Customer customer = customerResponse.getCustomer();
                retVal = customerResponse.getPaymentInstrumentPreloadRecordResponse();
                if (successStr.equals(retVal.getResponseCode())) {
                    retVal.setArn(arn.getArn());
                    retVal.setResponseCode(savePaymentInstrument(preloadContent, encPan, arn, customer)
                            .getResponseStr());
                }
            } else {
                retVal = new PaymentInstrumentPreloadRecordResponse();
                retVal.setResponseCode(BatchResponseCode.UNABLE_TO_GENERATE_ARN.getResponseStr());
            }
        } else {
            retVal = new PaymentInstrumentPreloadRecordResponse();
            retVal.setResponseCode(BatchResponseCode.UNSUPPORTED_AUTHENTICATION_OPTION.getResponseStr());
        }

        return retVal;
    }

    /**
     * Saves the payment instrument.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @param encPan
     *            String - The encrypted PAN.
     * @param arn
     *            String - The arn.
     * @param customer
     *            Customer - The customer.
     */
    private BatchResponseCode savePaymentInstrument(PaymentInstrumentPreloadContent preloadContent, String encPan,
            Arn arn, Customer customer) {
        BatchResponseCode retVal = BatchResponseCode.SUCCCESS;
        PaymentInstrument paymentInstrument = new PaymentInstrument();
        String pan = preloadContent.getPan();
        paymentInstrument.setPanHash(hashingService.hash(pan));
        try {
            paymentInstrument.setPanFirstSixDigits(encryptionService.encrypt(PanUtil.getBinFromPan(pan)));
            paymentInstrument.setPanLastFourDigits(encryptionService.encrypt(PanUtil.getLastFourDigits(pan)));
            paymentInstrument.setArn(arn);
            paymentInstrument.setCustomer(customer);
            paymentInstrument.setEncryptedPan(encPan);
            paymentInstrument.setPanLength(pan.length());
            copyValues(preloadContent, paymentInstrument);
            paymentInstrumentDao.save(paymentInstrument);
        } catch (SteException e) {
            retVal = BatchResponseCode.UNEXPECTED_ERROR_DURING_PROCESSING;
        }
        return retVal;
    }

    /**
     * Copies the values from the DTO to the entity.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @param paymentInstrument
     *            PaymentInstrument - The payment instrument.
     */
    private static void copyValues(PaymentInstrumentPreloadContent preloadContent, PaymentInstrument paymentInstrument) {
        paymentInstrument.setIssuerAccountId(preloadContent.getIssuerAccountId());
        paymentInstrument.setActive(false);
        paymentInstrument.setExpiryMonthYear(preloadContent.getExpirationDate());
        paymentInstrument.setNickName(preloadContent.getNickName());
    }

    /**
     * Creates a new customer based on the input preload content.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent- The preload content.
     * @param preloadRecordResponse
     *            - The response.
     * @param iisn
     *            String - The iisn.
     * @return PreloadResponseCode - The response code.
     */
    private CustomerResponse generateUserNameAndPassword(PaymentInstrumentPreloadContent preloadContent, String iisn) {
        CustomerResponse retVal = new CustomerResponse();
        PaymentInstrumentPreloadRecordResponse preloadRecordResponse = new PaymentInstrumentPreloadRecordResponse();
        preloadRecordResponse.setResponseCode(BatchResponseCode.SUCCCESS.getResponseStr());
        retVal.setPaymentInstrumentPreloadRecordResponse(preloadRecordResponse);
        // Find an existing customer and if it has a generated user name and
        // password, return the same.
        String issuerCustomerId = preloadContent.getIssuerCustomerId();
        UserNamePasswordHolder existingUser = generatedPasswords.get(issuerCustomerId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_ISSUER_CUSTOMER_ID, issuerCustomerId);
        Customer customer = ListUtil.getFirstOrNull(customerDao.findByName(
                VaultQueryConstants.FIND_CUSTOMER_BY_ISSUER_CUSTOMER_ID, params));
        if (existingUser == null) {
            // Check if the customer is already in the DB,
            // This would happen when payment instruments for the same customer
            // are part of different preload files.
            if (customer == null) {
                customer = userNameGenerationService.generate();
                if (customer != null) {
                    Password password = passwordGenerationService.generatePassword();
                    customer.setHashedPassword(password.getHashedPassword());
                    customer.setPasswordSalt(password.getPasswordSalt());
                    customer.setIssuerCustomerId(issuerCustomerId);
                    customer.setLastPasswordChange(DateUtil.getUtcTime());
                    String userName = customer.getUserName();
                    UserNamePasswordHolder userNamePasswordHolder = new UserNamePasswordHolder();
                    userNamePasswordHolder.setUserName(userName);
                    userNamePasswordHolder.setPassword(password);
                    generatedPasswords.put(issuerCustomerId, userNamePasswordHolder);
                    preloadRecordResponse.setUserName(userName);
                    preloadRecordResponse.setPassword(password.getPlainTextPassword());
                } else {
                    logger.error("Unable to generate an unique username for issuer {}.", iisn);
                    preloadRecordResponse.setResponseCode(BatchResponseCode.UNABLE_TO_GENERATE_UNIQUE_USERNAME
                            .getResponseStr());
                }
            } else {
                preloadRecordResponse.setUserName(customer.getUserName());
            }
        } else {
            preloadRecordResponse.setUserName(existingUser.getUserName());
            Password password = existingUser.getPassword();
            if (password != null) {
                preloadRecordResponse.setPassword(password.getPlainTextPassword());
            } else {
                preloadRecordResponse.setResponseCode(BatchResponseCode.UNEXPECTED_ERROR_DURING_PASSWORD_GENERATION
                        .getResponseStr());
            }
        }
        retVal.setCustomer(customer);
        return retVal;
    }

    /**
     * Fetches the issuer configuration for the given IISN.
     * 
     * @param iisn
     *            String - The iisn.
     * @return IssuerConfiguration - The issuer configuration.
     */
    private IssuerConfiguration getIssuerConfiguration(String iisn) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(VaultQueryConstants.PARAM_IISN, iisn);
        return ListUtil.getFirstOrNull(issuerConfigurationDao
                .findByName(VaultQueryConstants.GET_ISSUER_BY_IISN, params));
    }
}
