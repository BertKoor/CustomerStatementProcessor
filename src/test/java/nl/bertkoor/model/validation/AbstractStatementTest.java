package nl.bertkoor.model.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractStatementTest {

    public static final BigDecimal ELEVEN = BigDecimal.TEN.add(BigDecimal.ONE);

    private static final Validator VALIDATOR = buildDefaultValidatorFactory().getValidator();

    protected void assertValidity(final BalancedStatement sut, final String... expectedMessage) {
        Set<ConstraintViolation<BalancedStatement>> violations = VALIDATOR.validate(sut);
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
