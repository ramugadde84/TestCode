/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.vault.constant.BatchResponseCode;
import org.tch.ste.vault.constant.VaultConstants;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * Preload Instrument Writer.
 * 
 * @author Karthik.
 * 
 */
public class PaymentInstrumentPreloadBatchWriter implements ItemWriter<PaymentInstrumentPreloadContent> {

    private static Logger logger = LoggerFactory.getLogger(PaymentInstrumentPreloadBatchWriter.class);

    private File outputFile;

    private String iisn;

    private String headerTrailerValidationResult;

    private PaymentInstrumentPreloadService preloadService;

    @Autowired
    @Qualifier("paymentInstrumentContentWriter")
    private FixedLengthWriter<PaymentInstrumentPreloadRecordResponse> paymentInstrumentContentWriter;

    @Autowired
    private PaymentInstrumentPreloadUtil paymentInstrumentPreloadUtil;

    /**
     * Constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     * @param iisn
     *            String - The iisn.
     * @param preloadService
     *            PaymentInstrumentPreloadService - The preload service.
     * @throws IOException
     *             Thrown when unable to create an output.
     */
    public PaymentInstrumentPreloadBatchWriter(String outputFileName, String iisn,
            PaymentInstrumentPreloadService preloadService) throws IOException {
        this.outputFile = new File(outputFileName);
        this.iisn = iisn;
        if (!outputFile.exists() && !outputFile.createNewFile()) {
            throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
        }
        this.preloadService = preloadService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
     */
    @Override
    public void write(List<? extends PaymentInstrumentPreloadContent> items) throws Exception {
        List<String> lines = new ArrayList<String>();
        if (BatchResponseCode.SUCCCESS.getResponseStr().equals(headerTrailerValidationResult)) {
            for (PaymentInstrumentPreloadContent preloadContent : items) {
                try {
                    lines.add(paymentInstrumentContentWriter.write(preloadService.addPaymentInstrument(iisn,
                            preloadContent)));
                } catch (Throwable t) {
                    // Catch any unexpected exceptions and set the error code
                    // accordingly.
                    logger.warn("Error while attempting to preload: ", t);
                    lines.add(convertToOutput(preloadContent,
                            BatchResponseCode.UNEXPECTED_ERROR_DURING_PROCESSING.getResponseStr()));
                }
            }
        } else {
            for (PaymentInstrumentPreloadContent preloadContent : items) {
                lines.add(convertToOutput(preloadContent));
            }
        }
        BatchFileWriterUtil.writeLines(outputFile, lines);
    }

    /**
     * Converts to ouput.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @return String - The output.
     */
    private String convertToOutput(PaymentInstrumentPreloadContent preloadContent) {
        PaymentInstrumentPreloadRecordResponse output = new PaymentInstrumentPreloadRecordResponse();
        paymentInstrumentPreloadUtil.copyValues(output, preloadContent);
        output.setResponseCode(BatchResponseCode.RECORD_NOT_PROCESSED_DUE_TO_HEADER_TRAILER_ERROR.getResponseStr());
        return paymentInstrumentContentWriter.write(output);
    }

    /**
     * Converts the input record to an output record with the given error code.
     * 
     * @param preloadContent
     *            PaymentInstrumentPreloadContent - The content.
     * @param validationErrorCode
     *            String - The error code.
     * @return String - The output string.
     */
    private String convertToOutput(PaymentInstrumentPreloadContent preloadContent, String validationErrorCode) {
        PaymentInstrumentPreloadRecordResponse output = new PaymentInstrumentPreloadRecordResponse();
        paymentInstrumentPreloadUtil.copyValues(output, preloadContent);
        output.setResponseCode(validationErrorCode);
        return paymentInstrumentContentWriter.write(output);
    }

    /**
     * Retrieves the header trailer result.
     * 
     * @param stepExecution
     *            StepExecution - The step execution.
     */
    @BeforeStep
    public void retrieveHeaderTrailerResult(StepExecution stepExecution) {
        ExecutionContext jobContext = stepExecution.getJobExecution().getExecutionContext();
        headerTrailerValidationResult = jobContext.getString(VaultConstants.HEADER_TRAILER_VALIDATION_RESULT, null);
    }
}
