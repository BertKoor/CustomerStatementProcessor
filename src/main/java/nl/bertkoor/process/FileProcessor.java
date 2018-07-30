package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;

import java.io.InputStream;
import java.io.PrintStream;

public abstract class FileProcessor {

    private ReportWriter reportWriter;
    private int recordCount = 0;
    private StatementValidator validator = new StatementValidator();

    public FileProcessor(final PrintStream printStream) {
        this.reportWriter = new ReportWriter(printStream);
    }

    public abstract void process(final InputStream stream);

    protected abstract CustomerStatement parseStatement(Object value);

    protected void processStatement(Object value) {
        CustomerStatement statement = this.parseStatement(value);
        if (statement != null) {
            this.recordCount++;
            this.reportWriter.printViolations(
                    this.validator.validate(statement));
        }
    }

    protected void endReport() {
        this.reportWriter.endReport(this.recordCount);
    }
}
