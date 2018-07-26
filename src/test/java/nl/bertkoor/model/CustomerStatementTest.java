package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the CustomerStatement.
 * Could include MeanBean also, but EqualsVerifier provides enough coverage already.
 */
public class CustomerStatementTest {

    private static final Validator validator = buildDefaultValidatorFactory().getValidator();

    @Test
    public void equalsVerifierTest() {
        EqualsVerifier
                .forClass(CustomerStatement.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void whenReferenceIsFilled_thenIsValid() {
        CustomerStatement sut = new CustomerStatement();
        sut.setReferenceNumber("1");

        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenReferenceIsEmpty_thenNotValid() {
        CustomerStatement sut = new CustomerStatement();
        sut.setReferenceNumber("");

        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        assertThat(violations.isEmpty()).isFalse();
    }

    @Test
    public void whenReferenceIsBlank_thenIsValid() {
        CustomerStatement sut = new CustomerStatement();
        sut.setReferenceNumber(" ");

        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenReferenceIsNull_thenNotValid() {
        CustomerStatement sut = new CustomerStatement();

        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        assertThat(violations.isEmpty()).isFalse();
    }

}