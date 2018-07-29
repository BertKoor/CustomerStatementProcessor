package nl.bertkoor.model.validation;

import java.math.BigDecimal;

@BalancedStatementConstraint
public interface BalancedStatement {
    BigDecimal getStartBalance();
    BigDecimal getMutation();
    BigDecimal getEndBalance();
}
