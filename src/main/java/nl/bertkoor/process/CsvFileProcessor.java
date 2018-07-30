package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import nl.bertkoor.model.StatementCollector;
import nl.bertkoor.model.ViolationReport;

import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class CsvFileProcessor {

    private Path filePath;
    private ReportWriter reportWriter;

    private CsvLineParser parser = new CsvLineParser();
    private StatementCollector collector = new StatementCollector();
    private Validator validator = buildDefaultValidatorFactory().getValidator();
    private ViolationReport violations = new ViolationReport();
    private int recordCount = 0;

    public CsvFileProcessor(final Path path) {
        this.filePath = path;
        this.reportWriter = new ReportWriter(System.out);
    }

    public void process() {
        try (BufferedReader reader = Files.newBufferedReader(this.filePath, StandardCharsets.ISO_8859_1)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                CustomerStatement statement = parser.parse(line);
                if (statement != null) {
                    this.process(statement);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            reportWriter.endReport(recordCount);
        }
    }

    protected void process(final CustomerStatement statement) {
        this.recordCount++;
        collector.prepareForStatement(statement);
        violations.add(validator.validate(collector));
        collector.process();
    }

}
