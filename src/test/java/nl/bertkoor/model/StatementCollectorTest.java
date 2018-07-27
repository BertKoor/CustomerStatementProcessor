package nl.bertkoor.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class StatementCollectorTest {

    private static final Validator VALIDATOR = buildDefaultValidatorFactory().getValidator();

    private StatementCollector sut;

    @Before
    public void before() {
        sut = new StatementCollector();
    }

    @Test
    public void assertThatUniqueReferenceNumbersAreAdded() {
        assertThat(sut.countStatements()).isEqualTo(0);
        assertValidity(sut);

        sut.prepareForStatement(this.buildValidStatement("1"));
        assertThat(sut.countStatements()).isEqualTo(0);
        assertValidity(sut);

        sut.process();
        assertThat(sut.countStatements()).isEqualTo(1);
        assertValidity(sut);

        sut.prepareForStatement(this.buildValidStatement("2"));
        sut.process();
        assertThat(sut.countStatements()).isEqualTo(2);
        assertValidity(sut);

        // add "2" again, should not be valid and count should remain 2
        sut.prepareForStatement(this.buildValidStatement("2"));
        assertValidity(sut, "referenceNumber already processed");
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

    @Test
    public void assertThatInvalidStatementsAreRecursivelyValidated() {
        sut.prepareForStatement(this.buildValidStatement(""));
        assertValidity(sut, "newStatement.referenceNumber may not be empty");
    }

    private CustomerStatement buildValidStatement(String refNr) {
        return CustomerStatement.builder()
                .referenceNumber(refNr)
                .accountNumber("foo")
                .description("bar")
                .startBalance(BigDecimal.ZERO)
                .mutation(BigDecimal.ONE)
                .endBalance(BigDecimal.ONE)
                .build();
    }

    private void assertValidity(final StatementCollector sut, final String... expectedMessage) {
        Set<ConstraintViolation<StatementCollector>> violations = VALIDATOR.validate(sut);
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