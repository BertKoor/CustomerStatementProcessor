package nl.bertkoor.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;

/**
 * Unit tests for the CustomerStatement.
 * Could include MeanBean also, but EqualsVerifier provides enough coverage already.
 */
public class CustomerStatementTest extends AbstractStatementTest {

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

}