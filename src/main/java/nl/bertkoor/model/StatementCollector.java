package nl.bertkoor.model;

import java.util.HashSet;
import java.util.Set;

public class StatementCollector {

    private Set<String> referenceNumbers = new HashSet<>();
    private CustomerStatement newStatement = null;

    public synchronized void prepareForStatement(final CustomerStatement statement) {
        if (this.newStatement != null) {
            throw new IllegalArgumentException("newStatement is not null, first call process()");
        }
        this.newStatement = statement;
    }

    public synchronized void process() {
        if (this.newStatement == null) {
            throw new IllegalArgumentException("newStatement is null, first call prepareForStatement()");
        }
        this.referenceNumbers.add(this.newStatement.getReferenceNumber());
        this.newStatement = null;
    }

    public long countStatements() {
        return this.referenceNumbers.size();
    }
}
