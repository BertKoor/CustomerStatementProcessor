package nl.bertkoor.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public final class CustomerStatement {

    @NotNull @NotEmpty
    private String referenceNumber;
    private String accountNumber;
    private String description;
    private String startBalance;
    private String mutation;
    private String endBalance;
}
