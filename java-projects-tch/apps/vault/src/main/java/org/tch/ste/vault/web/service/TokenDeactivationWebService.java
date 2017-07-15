/**
 * 
 */
package org.tch.ste.vault.web.service;

import static org.tch.ste.vault.constant.VaultControllerConstants.C_TOKEN_DEACTIVATION_URL;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tch.ste.domain.entity.Token;
import org.tch.ste.infra.util.ObjectConverter;
import org.tch.ste.vault.domain.dto.TokenDeactivationRequest;
import org.tch.ste.vault.domain.dto.TokenDeactivationResponse;
import org.tch.ste.vault.service.core.tokendeactivation.TokenDeactivationFacade;

/**
 * @author pamartheepan
 * 
 */
@RestController
@RequestMapping(C_TOKEN_DEACTIVATION_URL)
public class TokenDeactivationWebService {

    private Logger logger = LoggerFactory.getLogger(TokenDeactivationWebService.class);

    @Autowired
    private TokenDeactivationFacade paymentInstrumentDeactivationFacade;

    @Autowired
    @Qualifier("jsonConverter")
    private ObjectConverter<TokenDeactivationRequest> tokenConverter;

    /**
     * Method to deactivated tokens using web service call.
     * 
     * @param request
     */
    @RequestMapping(method = RequestMethod.POST)
    TokenDeactivationResponse tokenDeactivate(HttpServletRequest request) {
        TokenDeactivationResponse res = new TokenDeactivationResponse();
        TokenDeactivationRequest req;
        List<Token> tokenList;
        try {
            req = tokenConverter.objectify(IOUtils.toString(request.getInputStream()), TokenDeactivationRequest.class);
            res.setIisn(req.getIisn());
            res.setPan(req.getPan());
            res.setDate(req.getDate());
            res.setArn(req.getArn());
            if (req.getArn() != null) {
                tokenList = paymentInstrumentDeactivationFacade.tokenDeactivation(req.getIisn(), req.getArn());
            } else {
                tokenList = paymentInstrumentDeactivationFacade.tokenDeactivation(req.getIisn(), req.getPan(),
                        req.getDate());
            }
            res.setTotalNoOfTokensDeactivated(tokenList.size());
        } catch (Throwable t) {
            logger.warn("Runtime error msg :" + t);
        }

        return res;
    }
}
