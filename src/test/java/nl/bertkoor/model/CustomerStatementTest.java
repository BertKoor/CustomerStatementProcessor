package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
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

    // Extensive tests on @NotBlank annotation on one field: referenceNumber
    @Test
    public void whenReferenceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                .referenceNumber(null)
                .accountNumber("1") //
                .description("foobar") //
                .build(), //
                "referenceNumber may not be empty");
    }


    @Test
    public void whenReferenceIsFilled_thenIsValid() {
        assertValidity(CustomerStatement.builder() //
                .referenceNumber("1") //
                .accountNumber("1") //
                .description("foobar") //
                .build());
    }

    @Test
    public void whenReferenceIsEmpty_thenNotValid() {
        assertValidity(CustomerStatement.builder() //
                .referenceNumber("") //
                .accountNumber("1") //
                .description("foobar") //
                .build(), //
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsBlank_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                .referenceNumber(" ")
                .accountNumber("1") //
                .description("foobar") //
                .build(), //
                "referenceNumber may not be empty");
    }

    // Basic test on other fields with @NotBlank annotation
    @Test
    public void whenAccountIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                .referenceNumber("1") //
                .accountNumber(null) //
                .description("foobar") //
                .build(), //
                "accountNumber may not be empty");
    }

    @Test
    public void whenDescriptionIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                .referenceNumber("1") //
                .accountNumber("foobar") //
                .description(null) //
                .build(), //
                "description may not be empty");
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