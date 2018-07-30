package nl.bertkoor.model.validation;

import java.math.BigDecimal;

/**
 * This interface contains only what is needed
 * for validation of the balance of a statement.
 */
@BalancedStatementConstraint // refers to the actual validator
public interface BalancedStatement {
    BigDecimal getStartBalance();
    BigDecimal getMutation();
    BigDecimal getEndBalance();
}
