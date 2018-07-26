package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the CustomerStatement.
 * Could include MeanBean also, but EqualsVerifier provides enough coverage already.
 */
public class CustomerStatementTest {

    private static final Validator validator = buildDefaultValidatorFactory().getValidator();
    private static final BigDecimal ELEVEN = TEN.add(ONE);

    @Test
    public void equalsVerifierTest() {
        EqualsVerifier
                .forClass(CustomerStatement.class)
                .verify();
    }

    // Extensive tests on @NotBlank annotation on one field: referenceNumber
    @Test
    public void whenReferenceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder() //
                        .referenceNumber(null) // <-- FAILS
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsFilled_thenIsValid() {
        assertValidity(CustomerStatement.builder() //
                .referenceNumber("1") // <-- OK
                .accountNumber("1") //
                .description("foobar") //
                .startBalance(TEN) //
                .mutation(ONE) //
                .endBalance(ELEVEN) //
                .build());
    }

    @Test
    public void whenReferenceIsEmpty_thenNotValid() {
        assertValidity(CustomerStatement.builder() //
                        .referenceNumber("") // <-- FAILS
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "referenceNumber may not be empty");
    }

    @Test
    public void whenReferenceIsBlank_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber(" ") // <-- FAILS
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "referenceNumber may not be empty");
    }

    // Basic test on other fields with @NotBlank annotation
    @Test
    public void whenAccountIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber(null) // <-- FAILS
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "accountNumber may not be empty");
    }

    @Test
    public void whenDescriptionIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber("foobar") //
                        .description(null) // <-- FAILS
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "description may not be empty");
    }

    // Basic test on the BigDecimal fields
    @Test
    public void whenStartBalanceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(null) // <-- FAILS
                        .mutation(ONE) //
                        .endBalance(ELEVEN) //
                        .build(), //
                "startBalance may not be null");
    }

    @Test
    public void whenMutationIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(null) // <-- FAILS
                        .endBalance(ELEVEN) //
                        .build(), //
                "mutation may not be null");
    }

    @Test
    public void whenEndBalanceIsNull_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(null) // <-- FAILS
                        .build(), //
                "endBalance may not be null");
    }

    @Test
    public void whenItDoesntAddUp_thenNotValid() {
        assertValidity(CustomerStatement.builder()
                        .referenceNumber("1") //
                        .accountNumber("1") //
                        .description("foobar") //
                        .startBalance(TEN) //
                        .mutation(ONE) //
                        .endBalance(TEN) // <-- FAILS
                        .build(), //
                "endBalance expected to be 11.00");
    }

    private void assertValidity(final CustomerStatement sut, final String... expectedMessage) {
        Set<ConstraintViolation<CustomerStatement>> violations = validator.validate(sut);
        List<String> msgList = new ArrayList<>();
        for (ConstraintViolation cv : violations) {
            msgList.add(cv.getPropertyPath() + " " + cv.getMessage());
        }

        if (expectedMessage.length == 0) {
            assertThat(msgList).isEqualTo(Collections.EMPTY_LIST);
        } else {
            assertThat(msgList).contains(expectedMessage);
        }
    }
}