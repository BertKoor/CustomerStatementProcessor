package nl.bertkoor;

import nl.bertkoor.process.CsvFileProcessor;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        try {
            File root = new File(this.getClass().getResource("/").toURI());

            for (File file: root.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("records.");
                }
            })) {
                this.process(file.toPath());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(Path filePath) {
        String fileName = filePath.getFileName().toString();
        String ext = fileName.substring(fileName.indexOf('.')+1);
        if ("csv".equals(ext)) {
            CsvFileProcessor processor = new CsvFileProcessor(filePath);
            processor.process();
            processor.report();
        }
    }
}
