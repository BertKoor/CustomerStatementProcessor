package nl.bertkoor.model;

import java.util.HashSet;
import java.util.Set;

@StatementCollectorConstraint
public class StatementCollector {

    private Set<String> referenceNumbers = new HashSet<>();
    private CustomerStatement newStatement = null;

    protected boolean hasNewStatement() {
        return this.newStatement != null;
    }

    public synchronized void prepareForStatement(final CustomerStatement statement) {
        if (this.hasNewStatement()) {
            throw new IllegalArgumentException("newStatement is not null, first call process()");
        }
        this.newStatement = statement;
    }

    public synchronized void process() {
        if (!this.hasNewStatement()) {
            throw new IllegalArgumentException("newStatement is null, first call prepareForStatement()");
        }
        this.referenceNumbers.add(this.newStatement.getReferenceNumber());
        this.newStatement = null;
    }

    public long countStatements() {
        return this.referenceNumbers.size();
    }

    protected boolean referenceNumbersContainsNewStatement() {
        return this.referenceNumbers.contains(this.newStatement.getReferenceNumber());
    }
}
