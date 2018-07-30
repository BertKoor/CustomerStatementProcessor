package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import nl.bertkoor.model.StatementError;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportWriterTest extends TestWithPrintStreamInterception {

    private ReportWriter sut;

    @Before
    public void before() {
        super.before();
        this.sut = new ReportWriter(new PrintStream(this.stream));
    }

    @Test
    public void assertThatHeaderIsWritten() {
        sut.printViolations(buildErrors("Computer says no"));
        assertIsWritten("RefNr.   Statement Description");
    }

    @Test
    public void assertThatNoViolationsAreReported() {
        sut.printViolations(buildErrors());
        sut.endReport(12);
        assertIsWritten("No violations");
    }

    @Test
    public void assertThatValidationsAreWritten() {
        sut.printViolations(buildErrors("It does not compute", "This is wrong"));
        assertIsWritten("It does not compute");
        assertIsWritten("This is wrong");
    }

    @Test
    public void assertThatNrStatementsIsWritten() {
        sut.endReport(12);
        assertIsWritten("12 statements");
        assertIsWritten("No violations");
    }

    @Test
    public void assertThatNrViolationsIsWritten() {
        sut.printViolations(buildErrors("It does not compute"));
        sut.printViolations(buildErrors("This is wrong"));
        sut.endReport(0);
        assertIsWritten("2 violations");
    }

    private Collection<StatementError> buildErrors(String... messages) {
        List<StatementError> result = new ArrayList<>();
        for (String message: messages) {
            result.add(new StatementError(CustomerStatement.builder()
                    .referenceNumber("1")
                    .description("foobar")
                    .build(), message));
        }
        return result;
    }
}