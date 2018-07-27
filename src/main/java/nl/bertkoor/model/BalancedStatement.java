package nl.bertkoor.model;

import java.math.BigDecimal;

@BalancedStatementConstraint
public interface BalancedStatement {
    BigDecimal getStartBalance();
    BigDecimal getMutation();
    BigDecimal getEndBalance();
}
