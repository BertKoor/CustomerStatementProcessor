package nl.bertkoor.model;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class StatementCollectorTest {

    private StatementCollector sut;

    @Before
    public void before() {
        sut = new StatementCollector();
    }

    @Test
    public void assertThatUniqueReferenceNumbersAreAdded() {
        assertThat(sut.countStatements()).isEqualTo(0);

        sut.prepareForStatement(CustomerStatement.builder().referenceNumber("1").build());
        assertThat(sut.countStatements()).isEqualTo(0);

        sut.process();
        assertThat(sut.countStatements()).isEqualTo(1);

        sut.prepareForStatement(CustomerStatement.builder().referenceNumber("2").build());
        sut.process();
        assertThat(sut.countStatements()).isEqualTo(2);

        // add "2" again, count should remain 2
        sut.prepareForStatement(CustomerStatement.builder().referenceNumber("2").build());
        sut.process();
        assertThat(sut.countStatements()).isEqualTo(2);
    }

    @Test
    public void whenProcessCalledWithoutPrepare_thenShouldFail() {
        try {
            sut.process();
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("newStatement is null, first call prepareForStatement()");
        }
    }

    @Test
    public void whenPrepareCalledAgainWithoutProcess_thenShouldFail() {
        sut.prepareForStatement(CustomerStatement.builder().build());
        try {
            sut.prepareForStatement(CustomerStatement.builder().build());
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("newStatement is not null, first call process()");
        }
    }

}