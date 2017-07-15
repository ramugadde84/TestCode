/**
 * 
 */
package org.tch.ste.infra.test.batch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.tch.ste.infra.util.RandomAccessFileUtil;

/**
 * @author Karthik.
 * 
 */
public class RandomAccessFileTest {

    /**
     * Tests fetching of the last line.
     * 
     * @throws IOException
     *             - If file is not accessible.
     */
    @Test
    public void testLastLineFetch() throws IOException {
        File file = new File("random_access_file.txt");
        assertEquals(RandomAccessFileUtil.readLastLine(file), "Enter.");
        assertEquals(RandomAccessFileUtil.readFirstLine(file), "Hello");
        File newFile = new File("random_access_file_with_last_cr.txt");
        assertEquals(RandomAccessFileUtil.readLastLine(newFile), "Better.");
        assertEquals(RandomAccessFileUtil.readFirstLine(newFile), "Hi There");
    }
}
