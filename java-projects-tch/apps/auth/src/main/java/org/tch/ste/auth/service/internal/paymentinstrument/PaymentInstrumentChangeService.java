/**
 * 
 */
package org.tch.ste.auth.service.internal.paymentinstrument;

import java.util.List;

import org.tch.ste.domain.entity.PaymentInstrument;

/**
 * Exposes methods to fetch changed payment instruments.
 * 
 * @author Karthik.
 * 
 */
public interface PaymentInstrumentChangeService {
    /**
     * This Methods returns the list of Payment Instruments which are added by
     * the Customer.
     * 
     * @param activePaymentInstruments
     *            - List of Payment Instrument Id's .
     * @param iisn
     *            String - The iisn.
     * @param trId
     *            String - The Token Requester Id.
     * @param ciid
     *            String - The CIID.
     * @return List<Integer> - Returns the List of Payment Instruments which are
     *         added.
     */
    List<PaymentInstrument> getAddedPaymentInstruments(List<Integer> activePaymentInstruments, String iisn,
            String trId, String ciid);

    /**
     * This Methods returns the list of Payment Instrument Id's which are
     * removed by the Customer.
     * 
     * @param inactivePaymentInstruments
     *            - List of Payment Instrument Id's .
     * @param iisn
     *            String - The iisn.
     * @param trId
     *            String - The Token Requester Id.
     * @param ciid
     *            String - The CIID.
     * @return List<Integer> - Returns the List of Payment Instruments which are
     *         removed.
     */
    List<PaymentInstrument> getRemovedPaymentInstruments(List<Integer> inactivePaymentInstruments, String iisn,
            String trId, String ciid);

    /**
     * This method takes the ARN,Token Requester Id ,CIID and Payment Instrument
     * State and returns whether it is active or not.
     * 
     * @param arnId
     *            String - ARN Id.
     * @param trId
     *            String - Token Requester Id.
     * @param ciid
     *            String - CIID.
     * @param active
     *            boolean - Payment Instrument State.
     * @return boolean - Payment Instrument State.
     */
    boolean isPaymentInstrumentActive(int arnId, String trId, String ciid, boolean active);
}
