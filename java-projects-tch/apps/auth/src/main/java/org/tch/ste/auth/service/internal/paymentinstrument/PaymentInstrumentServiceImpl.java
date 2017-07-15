package org.tch.ste.auth.service.internal.paymentinstrument;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tch.ste.auth.dto.PaymentInstrumentRequest;
import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Implementation for PaymentInstrumentService.
 * 
 * @author sharduls
 * 
 */
@Service
public class PaymentInstrumentServiceImpl implements PaymentInstrumentService {

    @Autowired
    private PaymentInstrumentLifeCycleHandler paymentInstrumentLifeCycleHandler;

    @Autowired
    private PaymentInstrumentChangeService paymentInstrumentChangeService;

    /**
     * Save the Payment Instrument for those are Added ,are tokenised else
     * otherwise De-Activated.
     * 
     * @param paymentInstrumentRequest
     *            -
     */
    @Override
    public void savePaymentInstrument(PaymentInstrumentRequest paymentInstrumentRequest) {

        String ciid = paymentInstrumentRequest.getCiid();
        String trId = paymentInstrumentRequest.getTrId();
        String iisn = paymentInstrumentRequest.getIisn();
        List<Integer> activePaymentInstrument  = paymentInstrumentRequest.getActivePaymentInstruments();
        List<Integer> inactivePaymentInstrument  = paymentInstrumentRequest.getInactivePaymentInstruments();
            List<PaymentInstrument> addedPaymentInstrument = paymentInstrumentChangeService.getAddedPaymentInstruments(activePaymentInstrument
                , iisn, trId, ciid); 
            activatePaymentInstrument(addedPaymentInstrument, ciid, trId, iisn);
            List<PaymentInstrument> removedPaymentInstrument = paymentInstrumentChangeService.getRemovedPaymentInstruments(
        		inactivePaymentInstrument, iisn, trId, ciid);        
            deactivatePaymentInstruments(removedPaymentInstrument, ciid, trId, iisn);
    }

    /**
     * Inactive Payment Instruments.
     * 
     * @param removedPaymentInstrument
     *            - List of Payment Instruments Removed.
     * @param ciid
     *            String - The CIID.
     * @param trId
     *            String - The Token Requestor Id.
     * @param iisn
     *            String - The IISN Value.
     */
    public void deactivatePaymentInstruments(List<PaymentInstrument> removedPaymentInstrument, String ciid,
            String trId, String iisn) {

        for (PaymentInstrument paymentInstrument : removedPaymentInstrument) {
            paymentInstrumentLifeCycleHandler.deactivate(paymentInstrument, ciid, trId, iisn);
        }

    }

    /**
     * Generate Tokens For the Added Token Instruments.
     * 
     * @param addedPaymentInstrument
     *            - List of Payment Instruments Added.
     * @param ciid
     *            String - The ciid.
     * @param trId
     *            String - The Token Requestor Id.
     * @param iisn
     *            String - The IISN Value.
     */
    public void activatePaymentInstrument(List<PaymentInstrument> addedPaymentInstrument, String ciid, String trId,
            String iisn) {

        for (PaymentInstrument paymentInstrument : addedPaymentInstrument) {
            paymentInstrumentLifeCycleHandler.activate(paymentInstrument, ciid, trId, iisn);
        }

    }

}
