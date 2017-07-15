/**
 * 
 */
package org.tch.ste.auth.service.internal.batch.authattempts;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.tch.ste.auth.util.BatchFileWriterUtil;

/**
 * @author anus
 *
 */
public class AuthAttemptsHeaderTrailerWriter implements StepExecutionListener {
    
    private static Logger logger=LoggerFactory.getLogger(AuthAttemptsHeaderTrailerWriter.class);
    
    private static String header = "Record Number, Issuer Customer ID, Vault Username, Authentication Attempt TimeStamp , Client IP Address, Client User Agent, Authentication Result";
    
    private File outputFile;
    
    /**
     * Overloaded constructor.
     * 
     * @param outputFileName String - The output file name.
     * @throws IOException Thrown.
     */
    public AuthAttemptsHeaderTrailerWriter(String outputFileName) throws IOException {
        outputFile=new File(outputFileName);
        if (!outputFile.exists() && !outputFile.createNewFile()) {
            throw new RuntimeException("Unable to create a new file: " + outputFileName + " for writing");
        }
    }


    /* (non-Javadoc)
     * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
     */
    @Override
    public void beforeStep(StepExecution stepExecution) {
        try {
            BatchFileWriterUtil.writeLine(outputFile,header);
        } catch (IOException e) {
            logger.error("Error while writing header to output file", e);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        StringBuilder trailer=new StringBuilder("Number of Records,");
        trailer.append(stepExecution.getWriteCount());
        try {
            BatchFileWriterUtil.writeLine(outputFile,trailer.toString());
        } catch (IOException e) {
            logger.error("Error while writing trailer to output file", e);
        }
        return ExitStatus.COMPLETED;
    }
    

}
