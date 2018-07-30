package nl.bertkoor;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.bertkoor.process.CsvFileProcessor;

import java.io.InputStream;
import java.io.PrintStream;

@SuppressFBWarnings(value = {"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public final class App {

    private PrintStream printStream = System.out;

    public static void main(final String[] args) {
        new App().run("records.csv", "records.xml");
    }

    protected void run(final String... paths) {
        for (String path: paths) {
            this.printStream.println();
            this.printStream.println("Processing " + path + " ...");
            this.process(path);
        }
    }

    protected void process(final String pathName) {
        String ext = pathName.substring(pathName.indexOf('.') + 1);
        InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(pathName);
        if ("csv".equals(ext)) {
            CsvFileProcessor processor = new CsvFileProcessor(this.printStream);
            processor.process(pathName, stream);
        } else {
            this.printStream.println(pathName
                    + " not processed: extension unsupported");
        }
    }
}
