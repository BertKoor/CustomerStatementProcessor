package nl.bertkoor.model;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ViolationReport {

    private List<String> report = new ArrayList<>();
    private long statementCount;

    public void add(Set<ConstraintViolation<StatementCollector>> newViolations) {
        statementCount++;
        for (ConstraintViolation<StatementCollector> violation: newViolations) {
            report.add(this.renderLine(violation));
        }
    }

    public long statementCount() {
        return this.statementCount;
    }

    public List<String> report() {
        return report;
    }

    protected String renderLine(ConstraintViolation<StatementCollector> violation) {
        CustomerStatement statement = null;
        Object leafBean = violation.getLeafBean();
        if (leafBean instanceof CustomerStatement) {
            statement = (CustomerStatement)leafBean;
        } else {
            statement = ((StatementCollector)leafBean).currentStatement();
        }

        return statement.getReferenceNumber() + "\t" + //
                statement.getDescription() + "\t" + //
                violation.getPropertyPath().toString()
                        .replace("newStatement.", "") + " " + //
                violation.getMessage();
    }
}
