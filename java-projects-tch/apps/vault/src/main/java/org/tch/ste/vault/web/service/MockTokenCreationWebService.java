/**
 * 
 */
package org.tch.ste.vault.web.service;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_MOCK_TOKEN_CREATION_SERVICE;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.vault.constant.VaultErrorCode;
import org.tch.ste.vault.domain.dto.Errors;
import org.tch.ste.vault.domain.dto.MockTokenCreationRequest;
import org.tch.ste.vault.domain.dto.MockTokenCreationResponse;
import org.tch.ste.vault.service.internal.token.TokenCreationFacade;

/**
 * @author kjanani
 * 
 */
@RestController
@RequestMapping(C_MOCK_TOKEN_CREATION_SERVICE)
public class MockTokenCreationWebService implements MessageSourceAware {

    @Autowired
    private TokenCreationFacade tokenCreationFacade;

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<MockTokenCreationRequest> requestConverter;

    private MessageSource messageSource;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
     * springframework.context.MessageSource)
     */

    /**
     * @param request
     *            -
     * @return MockTokenCreationResponse
     * @throws Throwable
     *             -
     */
    @RequestMapping(method = RequestMethod.POST)
    public MockTokenCreationResponse getCreatedToken(HttpServletRequest request) throws Throwable {

        MockTokenCreationResponse response = new MockTokenCreationResponse();

        MockTokenCreationRequest req;
        try {
            req = requestConverter
                    .objectify(IOUtils.toString(request.getInputStream()), MockTokenCreationRequest.class);

            Token token = tokenCreationFacade.createToken(req.getIisn(), req.getTrId(), req.getArnId(),req.getCiid());

            if (token != null) {
                response.setToken(token.getPlainTextToken());
                response.setArnId(req.getArnId());
                response.setTrId(req.getTrId());
                response.setIisn(req.getIisn());
                response.setCiid(req.getCiid());
            }
            else {
                setInvalidResponse(response);
            }

        } catch (Throwable t) {
            setInvalidResponse(response);
        }

        return response;
    }

    /**
     * Invalid Response.
     * 
     * @param response MockTokenCreationResponse - The response.
     */
    private void setInvalidResponse(MockTokenCreationResponse response) {
        Errors errors = new Errors();
        String responseCode = VaultErrorCode.INPUT_PARAMETER_VALUE_IS_INVALID.getResponseStr();
        errors.setErrorCode(responseCode);
        errors.setErrorMessage(messageSource.getMessage(responseCode, null, Locale.getDefault()));
        response.setErrorlist(errors);
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

}
