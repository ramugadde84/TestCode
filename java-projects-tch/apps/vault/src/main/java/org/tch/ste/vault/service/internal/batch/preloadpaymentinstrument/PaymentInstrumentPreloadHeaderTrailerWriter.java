/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.preloadpaymentinstrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.vault.constant.BatchOutputFileName;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.domain.dto.BatchOutputFileHeader;
import org.tch.ste.vault.service.internal.batch.BatchFileHeaderTrailerWriter;

/**
 * Implements trailer writing for the preload.
 * 
 * @author Karthik.
 * 
 */
public class PaymentInstrumentPreloadHeaderTrailerWriter extends BatchFileHeaderTrailerWriter {

    @Autowired
    @Qualifier("paymentInstrumentPreloadHeaderWriter")
    private FixedLengthWriter<BatchOutputFileHeader> headerWriter;

    @Autowired
    @Qualifier("paymentInstrumentTrailerWriter")
    private FixedLengthWriter<BatchFileTrailer> trailerWriter;

    /**
     * Constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     * @param timeStamp
     *            String - The timestamp.
     */
    public PaymentInstrumentPreloadHeaderTrailerWriter(String outputFileName, String timeStamp) {
        super(outputFileName, timeStamp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.vault.service.internal.batch.BatchFileTrailerWriter#
     * getTrailerWriter()
     */
    @Override
    protected FixedLengthWriter<BatchFileTrailer> getTrailerWriter() {
        return trailerWriter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchFileHeaderTrailerWriter
     * #getHeaderWriter()
     */
    @Override
    protected FixedLengthWriter<BatchOutputFileHeader> getHeaderWriter() {
        return headerWriter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.tch.ste.vault.service.internal.batch.BatchFileHeaderTrailerWriter
     * #getFileType()
     */
    @Override
    protected String getFileType() {
        return BatchOutputFileName.PYMNTAUTHCREATECONF.name();
    }
}
