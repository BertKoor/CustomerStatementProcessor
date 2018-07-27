package nl.bertkoor.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public class ViolationReportTest {

    private static final Validator VALIDATOR = buildDefaultValidatorFactory().getValidator();

    private ViolationReport sut;
    private StatementCollector collector;

    @Before
    public void before() {
        sut = new ViolationReport();
        collector = new StatementCollector();
    }

    @Test
    public void testRenderEndbalanceMismatch() {
        collector.prepareForStatement(this.buildMismatchingStatement());
        String line = sut.renderLine(this.getViolation());
        assertThat(line).isEqualTo("refNr\\tdescr\\tendBalance expected to be 0.00");
    }

    @Test
    public void testRenderDuplicateReferenceNumber() {
        CustomerStatement statement1 = this.buildStatement1();
        collector.prepareForStatement(statement1);
        collector.process();
        collector.prepareForStatement(statement1);
        String line = sut.renderLine(this.getViolation());
        assertThat(line).isEqualTo("1\\tdescr\\treferenceNumber already processed");
    }

    @Test
    public void testViolationReport() {
        collector.prepareForStatement(this.buildMismatchingStatement());
        sut.add(this.getViolations());
        collector.process();
        CustomerStatement statement1 = this.buildStatement1();
        collector.prepareForStatement(statement1);
        collector.process();
        collector.prepareForStatement(statement1);
        sut.add(this.getViolations());

        List<String> report = sut.report();
        assertThat(report.size()).isEqualTo(2);
    }

    private CustomerStatement buildMismatchingStatement() {
        return CustomerStatement.builder()
                .referenceNumber("refNr")
                .accountNumber("accNr")
                .description("descr")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.ZERO)
                .endBalance(BigDecimal.ONE)
                .build();
    }

    private CustomerStatement buildStatement1() {
        return CustomerStatement.builder()
                .referenceNumber("1")
                .accountNumber("accNr")
                .description("descr")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.ZERO)
                .endBalance(BigDecimal.ZERO)
                .build();
    }

    private ConstraintViolation<StatementCollector> getViolation() {
         return this.getViolations().iterator().next();
    }

    /**
     * Get some real violations, because Mocked violations might not behave as intended.
     * @return a set of violations
     */
    private Set<ConstraintViolation<StatementCollector>> getViolations() {
         return VALIDATOR.validate(collector);
    }
}