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

    /**
     * Create a StatementError for a statement with its error message.
     * The needed attributes for the report are copied, this frees some memory.
     * @param statement
     * @param errMsg
     */
    public StatementError(final CustomerStatement statement,
                          final String errMsg) {
        this.referenceNumber = statement.getReferenceNumber();
        this.statementDescription = statement.getDescription();
        this.errorMessage = errMsg;
    }

}
