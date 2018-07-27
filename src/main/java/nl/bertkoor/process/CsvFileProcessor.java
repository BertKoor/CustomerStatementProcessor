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
import java.util.List;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class CsvFileProcessor {

    private Path filePath;
    private CsvLineParser parser = new CsvLineParser();
    private StatementCollector collector = new StatementCollector();
    private Validator validator = buildDefaultValidatorFactory().getValidator();
    private ViolationReport violations = new ViolationReport();

    public CsvFileProcessor(final Path path) {
        this.filePath = path;
    }

    public void process() {
        System.out.println("Processing " + filePath.getFileName().toString());
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
        }
    }

    protected void process(final CustomerStatement statement) {
        collector.prepareForStatement(statement);
        violations.add(validator.validate(collector));
        collector.process();
    }

    public void report () {
        System.out.println(collector.countStatements() + " unique reference numbers processed");
        List<String> report = violations.report();
        if (report.isEmpty()) {
            System.out.println("No violations found");
        } else {
            System.out.println("RefNr.\tStatement Description       \tViolation");
            System.out.println("------\t----------------------------\t--------------------");
            for (String line: report) {
                System.out.println(line);
            }
        }
    }
}
