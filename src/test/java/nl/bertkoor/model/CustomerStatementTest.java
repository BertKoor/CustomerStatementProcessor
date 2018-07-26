package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
                .verify();
    }

    @Test
    public void whenReferenceIsFilled_thenIsValid() {
        assertValidity(CustomerStatement.builder().referenceNumber("1").build());
    }

    @Test
    public void whenReferenceIsEmpty_thenNotValid() {
        assertValidity(CustomerStatement.builder().referenceNumber("").build(), //
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsBlank_thenNotValid() {
        assertValidity(CustomerStatement.builder().referenceNumber(" ").build(), //
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder().build(), //
                "referenceNumber may not be null", "referenceNumber may not be empty");
    }

    private void assertValidity(final CustomerStatement sut, final String... expectedMessage) {
        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation cv: violations) {
            msgList.add(cv.getPropertyPath() + " " + cv.getMessage());
        }

        if (expectedMessage.length == 0) {
            assertThat(msgList).isEqualTo(Collections.EMPTY_LIST);
        } else {
            assertThat(msgList).contains(expectedMessage);
        }
    }
}