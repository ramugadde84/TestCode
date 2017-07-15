/**
 * 
 */
package org.tch.ste.infra.test.batch;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.batch.support.FixedLengthWriter;
import org.tch.ste.infra.test.domain.dto.TestPerson;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * Tests the writing of fixed length records.
 * 
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class FixedLengthWriterTest extends BaseTest {

    private static final String FIXED_LENGTH_TEST_OUTPUT_FILE = "test_fixed_length_output.txt";
    private static final String FIXED_LENGTH_TEST_FILE = "test_fixed_length.txt";

    private static List<TestPerson> testPersons = new ArrayList<TestPerson>();

    @Autowired
    @Qualifier("personFileWriter")
    private FixedLengthWriter<TestPerson> testPersonWriter;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        TestPerson testPerson = TestPersonUtil.createPerson("Keerthana", 8, false, null, 0, "0");
        testPersons.add(testPerson);
        testPerson = TestPersonUtil.createPerson("Karthik", 37, true, "Sangeetha", 19.060000000, "4187013657834123");
        testPersons.add(testPerson);
        testPerson = TestPersonUtil.createPerson("Ganesh", 35, true, "Gayathri", 21.050000000, "5134638790873456");
        testPersons.add(testPerson);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        testPersons.clear();
    }

    /**
     * Tests fixed length writing.
     * 
     * @throws IOException
     *             - When not able to write to a file.
     */
    @Test
    public void testFixedLengthWriter() throws IOException {
        List<String> lines = testPersonWriter.write(testPersons);
        File outputFile = new File(FIXED_LENGTH_TEST_OUTPUT_FILE);
        FileUtils.writeLines(outputFile, lines);
        File existingFile = new File(FIXED_LENGTH_TEST_FILE);
        assertTrue(FileUtils.contentEquals(existingFile, outputFile));
    }

}
