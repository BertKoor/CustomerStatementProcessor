package nl.bertkoor.process;

import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static junit.framework.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class TestWithPrintStreamInterception {
    protected ByteArrayOutputStream stream;

    @Before
    public void before() {
        this.stream = new ByteArrayOutputStream();
    }

    protected void assertIsWritten(final String value) {
        try (ByteArrayOutputStream stream = this.stream) {
            assertThat(stream.toString(StandardCharsets.UTF_8.name())).contains(value);
        } catch (IOException e) {
            fail(e.toString());
        }
    }


}
