/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.vault.constant.BatchOutputFileName;
import org.tch.ste.vault.domain.dto.BatchFileTrailer;
import org.tch.ste.vault.domain.dto.BatchOutputFileHeader;
import org.tch.ste.vault.service.internal.batch.BatchFileHeaderTrailerWriter;

/**
 * Trailer writer for the lock/unlock job.
 * 
 * @author Karthik.
 * 
 */
public class LockUnlockHeaderTrailerWriter extends BatchFileHeaderTrailerWriter {

    @Autowired
    @Qualifier("loclUnlockHeaderWriter")
    private FixedLengthWriter<BatchOutputFileHeader> headerWriter;

    @Autowired
    @Qualifier("lockUnlockTrailerWriter")
    private FixedLengthWriter<BatchFileTrailer> trailerWriter;

    /**
     * Constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     * @param timeStamp
     *            String - The timestamp.
     */
    public LockUnlockHeaderTrailerWriter(String outputFileName, String timeStamp) {
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
        return BatchOutputFileName.SETACCOUNTLOCKCONF.name();
    }

}
