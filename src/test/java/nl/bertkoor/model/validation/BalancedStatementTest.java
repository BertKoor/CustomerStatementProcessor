package nl.bertkoor.model.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;

public class BalancedStatementTest extends AbstractStatementTest {

    @Getter @AllArgsConstructor
    class BalancedTestStatement implements BalancedStatement {
        private BigDecimal startBalance;
        private BigDecimal mutation;
        private BigDecimal endBalance;
    }

    @Test
    public void whenItAddsUp_thenIsValid() {
        assertValidity(new BalancedTestStatement(TEN, ONE, ELEVEN));
    }

    @Test
    public void whenItDoesntAddUp_thenNotValid() {
        assertValidity(new BalancedTestStatement(TEN, ONE, TEN),
                "endBalance expected to be 11.00");
    }


}