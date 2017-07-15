/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.paymenttoken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.tch.ste.domain.entity.Arn;
import org.tch.ste.domain.entity.Customer;
import org.tch.ste.domain.entity.DetokenizationRequest;
import org.tch.ste.domain.entity.PaymentInstrument;
import org.tch.ste.domain.entity.PermittedTokenRequestorArn;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.exception.SteException;
import org.tch.ste.infra.service.core.security.EncryptionService;
import org.tch.ste.infra.service.core.security.MaskingService;
import org.tch.ste.infra.util.DateUtil;
import org.tch.ste.infra.util.StringUtil;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * @author anus
 * 
 */
public class PaymentTokenUsageWriter implements ItemWriter<DetokenizationRequest> {
    
    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private MaskingService maskingService;
    
    private int numRecords = 0;

    private File file;

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file.
     * @param timeStamp
     *            String - The timestamp.
     * @param iisn
     *            String - The iisn.
     * @throws IOException
     *             IOException - If not able to create a temp file.
     */
    public PaymentTokenUsageWriter(String outputFileName, String timeStamp, String iisn) throws IOException {
        file = new File(outputFileName);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends DetokenizationRequest> items) throws Exception {
        List<String> lines = new ArrayList<String>();
        for (DetokenizationRequest detokenizationRequest : items) {
            lines.add(processDetokenizationRequest(detokenizationRequest));
            ++numRecords;
        }
        BatchFileWriterUtil.writeLines(file, lines);

    }

    /**
     * @param detokenizationRequest
     * @return
     * @throws SteException 
     */
    private String processDetokenizationRequest(DetokenizationRequest detokenizationRequest) throws SteException {
        return getContentToWrite(detokenizationRequest);
    }

    /**
     * Converts to a fixed length record.
     * 
     * @return String - The record to be written.
     * 
     * @param detokenizationRequest
     * @throws SteException 
     */
    private String getContentToWrite(DetokenizationRequest detokenizationRequest) throws SteException {
        StringBuilder retVal = new StringBuilder();
        PermittedTokenRequestorArn permittedTokenRequestorArn = detokenizationRequest.getToken()
                .getPermittedTokenRequestorArnsTokens().get(0).getPermittedTokenRequestorArn();
        Arn arn = permittedTokenRequestorArn.getArn();
        PaymentInstrument paymentInstrument = arn.getPaymentInstruments().get(0);
        Customer customer = paymentInstrument.getCustomer();
        String maskPan = null;
        if(detokenizationRequest.getTokenPanEncrypted() != null){
            String tokenPan =encryptionService.decrypt(detokenizationRequest.getTokenPanEncrypted());
            maskPan = maskingService.mask(tokenPan);
        }
        String paymentInstrumentMaskPan = null;
        if(paymentInstrument.getEncryptedPan() != null){
            String paymentInstrumentPan =encryptionService.decrypt(paymentInstrument.getEncryptedPan());
            paymentInstrumentMaskPan = maskingService.mask(paymentInstrumentPan);
        }
        retVal.append(numRecords + 1);
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(customer.getIssuerCustomerId()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(paymentInstrument.getIssuerAccountId()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(customer.getUserName()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(arn.getArn()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(detokenizationRequest.getNetworkTrId()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(maskPan));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(detokenizationRequest.getTokenExpMonthYear()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(detokenizationRequest.isWasSuccessful());
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(detokenizationRequest.getFailureReasonCode()));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(StringUtil.convertNullToBlank(paymentInstrumentMaskPan));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(paymentInstrument.getExpiryMonthYear());
        retVal.append(VaultConstants.CSV_SEPERATOR);
        retVal.append(DateUtil.formatDate(detokenizationRequest.getRequestDateTime(), InfraConstants.STD_TIMESTAMP_FMT));
        retVal.append(VaultConstants.CSV_SEPERATOR);
        return retVal.toString();
    }
}
