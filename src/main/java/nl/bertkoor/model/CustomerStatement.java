package nl.bertkoor.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Builder @EqualsAndHashCode
public final class CustomerStatement {

    @NotNull @NotBlank
    private final String referenceNumber;
    private final String accountNumber;
    private final String description;
    private final BigDecimal startBalance;
    private final BigDecimal mutation;
    private final BigDecimal endBalance;
}
