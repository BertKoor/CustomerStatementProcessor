package nl.bertkoor;

import org.junit.Test;

public class AppTest {
    private App sut = new App();

    @Test(expected = RuntimeException.class)
    public void testCsvFile() {
        sut.run("doesNotExist.csv");
    }

    @Test
    public void testUnknownExtension() {
        sut.process("records.foo");
    }
}
