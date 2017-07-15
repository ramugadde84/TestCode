package org.tch.ste.auth.web.service.registration;

import static org.tch.ste.auth.constant.AuthControllerConstants.C_PAYMENT_INSTRUMENT_SERVICE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.auth.dto.PaymentInstrumentRequest;
import org.tch.ste.auth.dto.PaymentInstrumentResponse;
import org.tch.ste.auth.service.internal.paymentinstrument.PaymentInstrumentService;
import org.tch.ste.auth.service.internal.tokenrequestor.TokenRequestorUrlService;
import org.tch.ste.infra.util.ObjectConverter;

/**
 * Payment Instrument Web Service.
 * 
 * @author sharduls
 * 
 */
@RestController
@RequestMapping(C_PAYMENT_INSTRUMENT_SERVICE)
public class PaymentInstrumentWebService {

    private Logger logger = LoggerFactory.getLogger(PaymentInstrumentWebService.class);

	@Autowired
	private PaymentInstrumentService paymentInstrumentService;

	@Autowired
	private TokenRequestorUrlService tokenRequestorUrlService;


    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<PaymentInstrumentRequest> requestConverter;

    /**
     * Handles Saving of PaymentInstruments.
     * 
     * @param request
     *            - HttpServletRequest Object
     * @return PaymentInstrumentSaveResponse - Save Response.
     * @throws IllegalAccessException
     *             - Exception
     * @throws InvocationTargetException
     *             - Exception
     * @throws IOException
     *             -Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public PaymentInstrumentResponse savePaymentInstrument(HttpServletRequest request) throws IllegalAccessException,
            InvocationTargetException, IOException {
		PaymentInstrumentResponse response = null;
		PaymentInstrumentRequest paymentInstrumentRequest = requestConverter
				.objectify(IOUtils.toString(request.getInputStream()),
						PaymentInstrumentRequest.class);
		paymentInstrumentService.savePaymentInstrument(paymentInstrumentRequest);
		response = new PaymentInstrumentResponse();
		response.setSuccess(true);
		response.setRedirectUrl(tokenRequestorUrlService.formUrl(paymentInstrumentRequest.getTrId(), true));
        return response;
    }

    /**
     * This method is used to set the errormessage for invalid Json response
     * 
     * @return
     */
    private static PaymentInstrumentResponse createInvalidJsonResponse() {
        PaymentInstrumentResponse response = new PaymentInstrumentResponse();
        response.setSuccess(false);
        return response;
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
	PaymentInstrumentResponse handleException(HttpServletRequest request, Throwable t) {
		logger.warn("Exception while Saving PaymentInstruments : ", t);
		return createInvalidJsonResponse();
	}

}
