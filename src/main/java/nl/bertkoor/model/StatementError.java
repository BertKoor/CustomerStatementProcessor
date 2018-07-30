package nl.bertkoor.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode @ToString // convenience for unit tests
public final class StatementError {

    private final String referenceNumber;
    private final String statementDescription;
    private final String errorMessage;

    public StatementError(final CustomerStatement statement, final String errorMessage) {
        this.referenceNumber = statement.getReferenceNumber();
        this.statementDescription = statement.getDescription();
        this.errorMessage = errorMessage;
    }

}
