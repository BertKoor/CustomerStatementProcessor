package nl.bertkoor.process;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;

public class CsvFileProcessorTest extends TestWithPrintStreamInterception {

    private CsvFileProcessor sut;

    @Before
    public void before() {
        super.before();
        this.sut = new CsvFileProcessor(new PrintStream(this.stream));
    }

    @Test
    public void testEmpty() {
        letSutProcess("empty.csv");
        assertIsWritten("0 statements");
        assertIsWritten("No violations");
    }

    @Test
    public void testValidRecords() {
        letSutProcess("validRecords.csv");
        assertIsWritten("2 statements");
        assertIsWritten("No violations");
    }

    @Test
    public void testInvalidRecords() {
        letSutProcess("invalidRecords.csv");
        assertIsWritten("2 statements");
        assertIsWritten("2 violations");
        assertIsWritten("endBalance expected to be");
        assertIsWritten("referenceNumber already processed");
    }

    @Test(expected = NullPointerException.class)
    public void testNonexistingFile() {
        letSutProcess("doesNotExist");
    }

    private void letSutProcess(final String fileName) {
        InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);
        sut.process(stream);
    }
}