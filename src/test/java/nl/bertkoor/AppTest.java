package nl.bertkoor;

import org.junit.Test;

import java.io.File;
import java.io.FilenameFilter;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private App sut = new App();

    @Test(expected = NullPointerException.class)
    public void testNonexistingPath() {
        sut.run("0:does/not/exists");
    }

    @Test
    public void testFilenameFilter() {
        FilenameFilter filter = sut.buildFilenameFilter();
        assertThat(filter.accept(new File("/"), "record.foo"))
                .isFalse();
        assertThat(filter.accept(new File("/"), "records.foo"))
                .isTrue();
    }

    @Test(expected = RuntimeException.class)
    public void testCsvFile() {
        sut.process(new File("/doesNotExist.csv").toPath());
    }

    @Test
    public void testUnknownExtension() {
        sut.process(new File("/records.foo").toPath());
    }
}
