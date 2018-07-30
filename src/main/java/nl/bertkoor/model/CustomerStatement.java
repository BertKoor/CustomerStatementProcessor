package nl.bertkoor.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.bertkoor.model.validation.BalancedStatement;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * This represents a customer statement.
 * Its properties can be validated with basic bean validation.
 * Also the balance is validated by implementing BalancedStatement.
 */
@Builder @Getter
@EqualsAndHashCode @ToString // convenience for unit tests
public final class CustomerStatement implements BalancedStatement {
    @NotBlank private final String referenceNumber;
    @NotBlank private final String accountNumber;
    @NotBlank private final String description;
    @NotNull private final BigDecimal startBalance;
    @NotNull private final BigDecimal mutation;
    @NotNull private final BigDecimal endBalance;
}
