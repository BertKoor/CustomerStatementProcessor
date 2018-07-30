package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StatementErrorTest {

    @Test
    public void equalsVerifierTest() {
        EqualsVerifier
                .forClass(StatementError.class)
                .verify();
    }

    @Test
    public void testConstructorAndGetters() {
        CustomerStatement statement = CustomerStatement.builder()
                .referenceNumber("refNr")
                .description("descr")
                .build();
        StatementError sut = new StatementError(statement, "errMsg");
        assertThat(sut.getReferenceNumber()).isEqualTo("refNr");
        assertThat(sut.getStatementDescription()).isEqualTo("descr");
        assertThat(sut.getErrorMessage()).isEqualTo("errMsg");
    }

}