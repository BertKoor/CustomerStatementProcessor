package nl.bertkoor.model;

import lombok.Data;

@Data
public final class CustomerStatement {
    private String referenceNumber;
    private String accountNumber;
    private String description;
    private String startBalance;
    private String mutation;
    private String endBalance;
}
