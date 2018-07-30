package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import noNamespace.CustomerStatementType;

public class XmlStatementParser {

    public CustomerStatement parse(final CustomerStatementType value) {
        return CustomerStatement.builder()
                .referenceNumber(value.getReference())
                .accountNumber(value.getAccountNumber())
                .description(value.getDescription())
                .startBalance(value.getStartBalance())
                .mutation(value.getMutation())
                .endBalance(value.getEndBalance())
                .build();
    }
}
