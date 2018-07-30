package nl.bertkoor.process;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.bertkoor.model.CustomerStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public final class CsvFileProcessor {

    private ReportWriter reportWriter;

    private int recordCount = 0;
    private CsvLineParser parser = new CsvLineParser();
    private StatementValidator validator = new StatementValidator();

    public CsvFileProcessor(final PrintStream printStream) {
        this.reportWriter = new ReportWriter(printStream);
    }

    public void processFile(final Path filePath) {
        this.reportWriter.startReport(filePath.getFileName().toString());
        try (BufferedReader reader = Files.newBufferedReader(filePath,
                StandardCharsets.ISO_8859_1)) {
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
        CustomerStatement statement = this.parser.parse(recordLine);
        if (statement != null) {
            this.recordCount++;
            this.reportWriter.printViolations(
                    this.validator.validate(statement));
        }

    }

}
