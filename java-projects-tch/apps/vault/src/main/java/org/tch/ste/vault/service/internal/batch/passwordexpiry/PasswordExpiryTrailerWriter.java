/**
 * 
 */
package org.tch.ste.vault.service.internal.batch.passwordexpiry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.tch.ste.vault.util.BatchFileWriterUtil;

/**
 * To write the footer record.
 * 
 * @author Karthik.
 * 
 */
public class PasswordExpiryTrailerWriter implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(PasswordExpiryTrailerWriter.class);

    private static String header = "Record Count, Issuer Customer Id, User Name, Password, Error Code";

    private String outputFileName;

    /**
     * Overloaded constructor.
     * 
     * @param outputFileName
     *            String - The output file name.
     */
    public PasswordExpiryTrailerWriter(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.
     * springframework.batch.core.StepExecution)
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        try {
            BatchFileWriterUtil.writeLine(new File(outputFileName), header);
        } catch (IOException e) {
            logger.warn("Unable to write the header to file name: " + outputFileName, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.
     * springframework.batch.core.StepExecution)
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus retVal = ExitStatus.COMPLETED;
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFileName, true), "UTF-8")) {
            out.append(String.valueOf(stepExecution.getWriteCount()));
        } catch (IOException e) {
            logger.warn("Unable to write the trailer to file name: " + outputFileName, e);
            retVal = ExitStatus.FAILED;
        }
        return retVal;
    }

}
