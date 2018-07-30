package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import nl.bertkoor.model.StatementError;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementValidatorTest {

    private StatementValidator sut;

    @Before
    public void before() {
        sut = new StatementValidator();
    }

    @Test
    public void assertThatBasicBeanValidationWorks() {
        CustomerStatement statement = CustomerStatement.builder().build();
        assertThat(sut.validate(statement)).contains(
                new StatementError(statement, "referenceNumber may not be empty"));
    }

    @Test
    public void assertTheBalancedStatementValidationWorks() {
        CustomerStatement statement = CustomerStatement.builder()
                .referenceNumber("1")
                .startBalance(BigDecimal.ONE)
                .mutation(BigDecimal.TEN)
                .endBalance(BigDecimal.ZERO)
                .build();
        assertThat(sut.validate(statement)).contains(
                new StatementError(statement, "endBalance expected to be 11.00"));
    }

    @Test
    public void assertThatDuplicateReferenceNumbersAreInvalid() {
        CustomerStatement statement = CustomerStatement.builder()
                .referenceNumber("1")
                .accountNumber("2")
                .description("foobar")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.ONE)
                .endBalance(BigDecimal.ONE)
                .build();
        assertThat(sut.validate(statement)).isEmpty();
        assertThat(sut.validate(statement)).contains(
                new StatementError(statement, "referenceNumber already processed")
        );
    }
}