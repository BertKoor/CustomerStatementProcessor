package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import nl.bertkoor.model.StatementCollector;
import nl.bertkoor.model.ViolationReport;

import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class CsvFileProcessor {

    private ReportWriter reportWriter;

    private CsvLineParser parser = new CsvLineParser();
    private StatementCollector collector = new StatementCollector();
    private Validator validator = buildDefaultValidatorFactory().getValidator();
    private ViolationReport violations = new ViolationReport();
    private int recordCount = 0;

    public CsvFileProcessor(final PrintStream printStream) {
        this.reportWriter = new ReportWriter(printStream);
    }

    public void processFile(final Path filePath) {
        this.reportWriter.startReport(filePath.getFileName().toString());
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.ISO_8859_1)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                CustomerStatement statement = this.parser.parse(line);
                if (statement != null) {
                    this.process(statement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.reportWriter.endReport(this.recordCount);
        }
    }

    protected void process(final CustomerStatement statement) {
        this.recordCount++;
        collector.prepareForStatement(statement);
        violations.add(validator.validate(collector));
        collector.process();
    }

}
