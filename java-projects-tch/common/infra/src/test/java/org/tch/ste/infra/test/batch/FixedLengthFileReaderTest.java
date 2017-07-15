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
import org.apache.commons.io.LineIterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tch.ste.infra.batch.support.FixedLengthReader;
import org.tch.ste.infra.test.domain.dto.TestPerson;
import org.tch.ste.test.base.BaseTest;
import org.tch.ste.test.base.TransactionalUnitTest;

/**
 * @author Karthik.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalUnitTest
public class FixedLengthFileReaderTest extends BaseTest {

    private static final String FIXED_LENGTH_TEST_FILE = "test_fixed_length.txt";

    private static List<TestPerson> expectedPersons = new ArrayList<TestPerson>();

    @Autowired
    @Qualifier("personFileReader")
    private FixedLengthReader<TestPerson> fixedLengthReader;

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherTeardown()
     */
    @Override
    protected void doOtherTeardown() {
        super.doOtherTeardown();
        expectedPersons.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tch.ste.test.base.BaseTest#doOtherSetup()
     */
    @Override
    protected void doOtherSetup() {
        super.doOtherSetup();
        TestPerson testPerson = TestPersonUtil.createPerson("Keerthana", 8, false, null, 0, "0");
        expectedPersons.add(testPerson);
        testPerson = TestPersonUtil.createPerson("Karthik", 37, true, "Sangeetha", 19.060000000, "4187013657834123");
        expectedPersons.add(testPerson);
        testPerson = TestPersonUtil.createPerson("Ganesh", 35, true, "Gayathri", 21.050000000, "5134638790873456");
        expectedPersons.add(testPerson);
    }

    /**
     * Test for fixed length file read.
     * 
     * @throws IOException
     *             - Thrown when unable to read file.
     */
    @Test
    public void testFileRead() throws IOException {
        File file = new File(FIXED_LENGTH_TEST_FILE);
        LineIterator lineIterator = FileUtils.lineIterator(file);
        List<String> lines = new ArrayList<String>();
        while (lineIterator.hasNext()) {
            lines.add(lineIterator.nextLine());
        }
        List<TestPerson> testPersons = fixedLengthReader.read(lines, true);
        validateExpectedResult(testPersons);
    }

    /**
     * Validates the result.
     * 
     * @param testPersons
     *            List<TestPerson> - The test persons.
     */
    private static void validateExpectedResult(List<TestPerson> testPersons) {
        int size = testPersons.size();
        assertEquals(size, expectedPersons.size());
        for (int i = 0; i < size; ++i) {
            checkEquals(testPersons.get(i), expectedPersons.get(i));
        }
    }

    /**
     * Checks whether the two are equal.
     * 
     * @param testPerson1
     *            TestPerson- The first.
     * @param testPerson2
     *            TestPerson- The second.
     */
    private static void checkEquals(TestPerson testPerson1, TestPerson testPerson2) {
        assertEquals(testPerson1.getName(), testPerson2.getName());
        assertEquals(testPerson1.getAge(), testPerson2.getAge());
        assertEquals(testPerson1.getSpouseName(), testPerson2.getSpouseName());
        assertEquals(testPerson1.getSalary(), testPerson2.getSalary());
        assertEquals(testPerson1.isMarried(), testPerson2.isMarried());
    }

}
