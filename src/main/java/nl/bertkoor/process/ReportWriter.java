package nl.bertkoor.process;

import nl.bertkoor.model.StatementError;

import java.io.PrintStream;
import java.util.Collection;

public final class ReportWriter {

    private static final int REFNR_COLUMN_WIDTH = 6;
    private static final int DESCR_COLUMN_WIDTH = 30;

    private PrintStream outputStream;
    private boolean headerPrinted = false;
    private long violationCount = 0;

    public ReportWriter(final PrintStream printStream) {
        this.outputStream = printStream;
    }

    public void startReport(final String fileName) {
        printLine("Processing " + fileName);
    }

    public void printViolations(final Collection<StatementError> violations) {
        if (!violations.isEmpty()) {
            this.printHeader();
            for (StatementError error: violations) {
                violationCount++;
                printLine(renderLine(error));
            }
        }
    }

    private void printHeader() {
        if (!headerPrinted) {
            printLine("");
            printLine(renderLine("RefNr.",
                    "Statement Description",
                    "Violation"));
            printLine(renderLine("------",
                    "------------------------------",
                    "------------------------------"));
            headerPrinted = true;
        }
    }

    public void endReport(final int statementCount) {
        printLine("");
        printLine(statementCount + " statements processed.");
        String counts = (violationCount == 0) ? "No" : "" + violationCount;
        printLine(counts + " violations found.");
    }


    private void printLine(final String line) {
        outputStream.println(line);
    }

    protected static String renderLine(final StatementError error) {
        return renderLine(error.getReferenceNumber(),
                error.getStatementDescription(),
                error.getErrorMessage());
    }

    protected static String renderLine(final String refNr,
                                       final String statementDescr,
                                       final String validationError) {
        return align(refNr, REFNR_COLUMN_WIDTH) + "   "
                + align(statementDescr, DESCR_COLUMN_WIDTH) + "   "
                + validationError;
    }

    protected static String align(final String value, final int length) {
        StringBuffer result = new StringBuffer();
        int copyLen = value.length() < length ? value.length() : length;
        result.append(value.substring(0, copyLen));
        while (result.length() < length) {
            result.append(' ');
        }
        return result.toString();
    }
}
