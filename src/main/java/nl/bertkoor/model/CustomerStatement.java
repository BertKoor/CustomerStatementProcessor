package nl.bertkoor.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Builder @EqualsAndHashCode
@BalanceConstraint
public final class CustomerStatement {

    @NotBlank private final String referenceNumber;
    @NotBlank private final String accountNumber;
    @NotBlank private final String description;
    @NotNull private final BigDecimal startBalance;
    @NotNull private final BigDecimal mutation;
    @NotNull private final BigDecimal endBalance;
}
