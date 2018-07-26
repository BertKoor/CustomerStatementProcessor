package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Unit tests for the CustomerStatement.
 * Could include MeanBean also, but EqualsVerifier provides enough coverage already.
 */
public class CustomerStatementTest {

    @Test
    public void equalsVerifierTest() {
        EqualsVerifier
                .forClass(CustomerStatement.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}