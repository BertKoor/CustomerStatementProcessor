package nl.bertkoor.process;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.bertkoor.model.CustomerStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public final class CsvFileProcessor extends FileProcessor {

    private CsvLineParser parser = new CsvLineParser();

    public CsvFileProcessor(final PrintStream printStream) {
        super(printStream);
    }

    public void process(final InputStream stream) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.ISO_8859_1))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                this.processStatement(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.endReport();
        }
    }

    @Override
    protected CustomerStatement parseStatement(final Object value) {
        return parser.parse((String)value);
    }
}
