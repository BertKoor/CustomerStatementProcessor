package nl.bertkoor.process;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class CsvFileProcessorTest extends TestWithPrintStreamInterception {

    private CsvFileProcessor sut;

    @Before
    public void before() {
        super.before();
        this.sut = new CsvFileProcessor(new PrintStream(this.stream));
    }

    @Test
    public void testEmpty() {
        sut.processFile(this.getResourcePath("/empty.csv"));
        assertIsWritten("Processing empty.csv");
        assertIsWritten("0 statements");
        assertIsWritten("No violations");
    }

    @Test
    public void testValidRecords() {
        sut.processFile(this.getResourcePath("/validRecords.csv"));
        assertIsWritten("Processing validRecords.csv");
        assertIsWritten("2 statements");
        assertIsWritten("No violations");
    }

    @Test
    public void testInvalidRecords() {
        sut.processFile(this.getResourcePath("/invalidRecords.csv"));
        assertIsWritten("Processing invalidRecords.csv");
        assertIsWritten("2 statements");
        assertIsWritten("2 violations");
        assertIsWritten("endBalance expected to be");
        assertIsWritten("referenceNumber already processed");
    }

    @Test
    public void testNonexistingFile() {
        try {
            sut.processFile(new File("/doesNotExist").toPath());
            fail();
        } catch (RuntimeException e) {
            assertThat(e.getCause() instanceof NoSuchFileException).isTrue();
        }
    }

    private Path getResourcePath(final String fileName) {
        try {
            return new File(this.getClass().getResource(fileName).toURI()).toPath();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Resource " + fileName + " not found");
        }
    }
}