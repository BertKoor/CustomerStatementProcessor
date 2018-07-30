package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileProcessor {

    private ReportWriter reportWriter;

    private int recordCount = 0;
    private CsvLineParser parser = new CsvLineParser();
    private StatementValidator validator = new StatementValidator();

    public CsvFileProcessor(final PrintStream printStream) {
        this.reportWriter = new ReportWriter(printStream);
    }

    public void processFile(final Path filePath) {
        this.reportWriter.startReport(filePath.getFileName().toString());
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.ISO_8859_1)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                this.processRecord(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.reportWriter.endReport(this.recordCount);
        }
    }

    protected void processRecord(final String recordLine) {
        this.recordCount++;
        CustomerStatement statement = this.parser.parse(recordLine);
        if (statement != null) {
            this.reportWriter.reportStatementViolations(this.validator.validate(statement));
        }

    }

}
