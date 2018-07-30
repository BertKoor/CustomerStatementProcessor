package nl.bertkoor;

import nl.bertkoor.process.CsvFileProcessor;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class App {

    private PrintStream printStream = System.out;

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        try {
            File root = new File(this.getClass().getResource("/").toURI());

            for (File file: root.listFiles(this.buildFilenameFilter())) {
                this.process(file.toPath());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private FilenameFilter buildFilenameFilter() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith("records.");
            }
        };
    }

    private void process(final Path filePath) {
        String fileName = filePath.getFileName().toString();
        String ext = fileName.substring(fileName.indexOf('.')+1);
        if ("csv".equals(ext)) {
            CsvFileProcessor processor = new CsvFileProcessor(this.printStream);
            processor.processFile(filePath);
        } else {
            this.printStream.println("File " + fileName + " not processed: extension unknown");
        }
    }
}
