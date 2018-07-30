package nl.bertkoor;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.bertkoor.process.CsvFileProcessor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Path;

@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public final class App {

    private PrintStream printStream = System.out;

    public static void main(final String[] args) {
        new App().run("/");
    }

    protected void run(final String path) {
        try {
            File root = new File(this.getClass().getResource(path).toURI());

            for (File file: root.listFiles(this.buildFilenameFilter())) {
                this.process(file.toPath());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected FilenameFilter buildFilenameFilter() {
        return new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.startsWith("records.");
            }
        };
    }

    protected void process(final Path filePath) {
        String fileName = filePath.getFileName().toString();
        String ext = fileName.substring(fileName.indexOf('.') + 1);
        if ("csv".equals(ext)) {
            CsvFileProcessor processor = new CsvFileProcessor(this.printStream);
            processor.processFile(filePath);
        } else {
            this.printStream.println("File " + fileName
                    + " not processed: extension unknown");
        }
    }
}
