package org.tch.ste.admin.web.controller;

import static org.tch.ste.admin.constant.AdminControllerConstants.C_CUSTOMER_MANAGEMENT;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_ISSUER_LIST_REQ_MAPPING;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_LOCK;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_MODEL_ATTR_ISSUER;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_CUSTOMER_ID;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_REQUEST_PARAM_ISSUER_IISN;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_RESET_PASSWORD;
import static org.tch.ste.admin.constant.AdminControllerConstants.C_UNLOCK;
import static org.tch.ste.admin.constant.AdminViewConstants.V_CUSTOMER_DETAILS_PAGE;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tch.ste.admin.constant.CustomerManagementErrorCode;
import org.tch.ste.admin.domain.dto.CustomerLockResponse;
import org.tch.ste.admin.domain.dto.CustomerManagement;
import org.tch.ste.admin.domain.dto.CustomerUnLockResponse;
import org.tch.ste.admin.domain.dto.ResetPasswordResponse;
import org.tch.ste.admin.service.core.customermanagement.CustomerManagementService;
import org.tch.ste.admin.service.core.issuer.IssuerService;

/**
 * @author anus
 * 
 */
@Controller
@RequestMapping(C_CUSTOMER_MANAGEMENT)
public class CustomerManagementController implements MessageSourceAware {

    private static Logger logger = LoggerFactory.getLogger(CustomerManagementController.class);

    @Autowired
    private CustomerManagementService customerMgmtService;

    @Autowired
    private IssuerService issuerService;

    private MessageSource messageSource;

    /**
     * This method is used to fetch the list of isuers.
     * 
     * @param model
     *            Model - The model.
     * @return customer page
     */
    @RequestMapping(value = C_ISSUER_LIST_REQ_MAPPING, method = RequestMethod.GET)
    public String loadIssuerDtls(Model model) {
        logger.info("Issuer User Management sub menu Viewed by user {}", SecurityContextHolder.getContext()
                .getAuthentication().getName());
        model.addAttribute(C_MODEL_ATTR_ISSUER, issuerService.getIssuersSortedAscendingByName());
        return V_CUSTOMER_DETAILS_PAGE;
    }

    /**
     * This is used for resetting the password
     * 
     * -
     * 
     * @param iisn
     *            String - The iisn.
     * @param id
     *            Integer - The id.
     * @return - ResetPasswordResponse
     * 
     */
    @RequestMapping(value = C_RESET_PASSWORD, method = RequestMethod.POST)
    public @ResponseBody
    ResetPasswordResponse resetPassword(
            @RequestParam(value = C_REQUEST_PARAM_ISSUER_IISN, required = false) String iisn,
            @RequestParam(value = C_REQUEST_PARAM_CUSTOMER_ID, required = false) Integer id) {

        ResetPasswordResponse retVal = new ResetPasswordResponse();
        retVal.setSuccess(true);
        retVal.setUserPassword(customerMgmtService.resetPassword(id, iisn));
        return retVal;

    }

    /**
     * Fetches the customers for the given issuer.
     * 
     * @param iisn
     *            String - The iisn.
     * @return List<CustomerManagement> - The values.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<CustomerManagement> getCustomers(
            @RequestParam(value = C_REQUEST_PARAM_ISSUER_IISN, required = false) String iisn) {
        return customerMgmtService.getCustomers(iisn);
    }

    /**
     * This method is used to set the account locked status of the customer
     * 
     * @param iisn
     *            - Issuer iisn
     * @param id
     *            - Issuer id
     * @param request
     *            - request
     * @return CustomerLockResponse - The response.
     */
    @RequestMapping(value = C_LOCK, method = RequestMethod.POST)
    public @ResponseBody
    CustomerLockResponse lock(@RequestParam(value = C_REQUEST_PARAM_ISSUER_IISN, required = false) String iisn,
            @RequestParam(value = C_REQUEST_PARAM_CUSTOMER_ID, required = false) Integer id, HttpServletRequest request) {
        CustomerLockResponse custLockResponse = new CustomerLockResponse();
        boolean locked = customerMgmtService.lock(id, iisn);
        custLockResponse.setSuccess(locked);
        if (locked == false) {
            custLockResponse.setErrorMsg(messageSource.getMessage(
                    CustomerManagementErrorCode.CUSTOMER_LOCK_STATUS.name(), null, request.getLocale()));
        }

        return custLockResponse;
    }

    /**
     * This method is used to set the account unlocked status of the customer
     * 
     * @param iisn
     *            - Issuer iisn
     * @param id
     *            - Issuer id
     * @param request
     *            - request
     * @return CustomerLockResponse - The response.
     */
    @RequestMapping(value = C_UNLOCK, method = RequestMethod.POST)
    public @ResponseBody
    CustomerUnLockResponse unlock(@RequestParam(value = C_REQUEST_PARAM_ISSUER_IISN, required = false) String iisn,
            @RequestParam(value = C_REQUEST_PARAM_CUSTOMER_ID, required = false) Integer id, HttpServletRequest request) {
        CustomerUnLockResponse custUnLockResponse = new CustomerUnLockResponse();
        boolean unLocked = customerMgmtService.unlock(id, iisn);
        custUnLockResponse.setSuccess(unLocked);
        if (unLocked == false) {
            custUnLockResponse.setErrorMsg(messageSource.getMessage(
                    CustomerManagementErrorCode.CUSTOMER_UNLOCK_STATUS.name(), null, request.getLocale()));
        }
        return custUnLockResponse;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
     * springframework.context.MessageSource)
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles exception.
     * 
     * @param request
     *            HttpServletRequest - The request.
     * @param t
     *            Throwable - The exception.
     * @return Empty List - The response.
     */
    @ExceptionHandler(Throwable.class)
    public @ResponseBody
    Object handleException(HttpServletRequest request, Throwable t) {
        logger.warn("Exception while fetching customers: ", t);

        return new ArrayList<Object>();
    }
}
