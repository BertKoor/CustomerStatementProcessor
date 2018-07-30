package nl.bertkoor.process;

import nl.bertkoor.model.CustomerStatement;
import nl.bertkoor.model.StatementError;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;

public final class StatementValidator {

    private Validator beanValidator = buildDefaultValidatorFactory() //
            .getValidator();
    private Set<String> referenceNumbers = new HashSet<>();

    public List<StatementError> validate(final CustomerStatement statement) {
        List<StatementError> result = new ArrayList<>();
        if (!referenceNumbers.add(statement.getReferenceNumber())) {
            result.add(new StatementError(statement,
                    "referenceNumber already processed"));
        }
        for (ConstraintViolation<CustomerStatement> violation
                : this.beanValidator.validate(statement)) {
            result.add(buildStatementError(violation));
        }
        return result;
    }

    private StatementError buildStatementError(
            final ConstraintViolation<CustomerStatement> violation) {
        StringBuffer errMsg = new StringBuffer();
        errMsg.append(violation.getPropertyPath());
        errMsg.append(' ');
        errMsg.append(violation.getMessage());
        return new StatementError((CustomerStatement) violation.getLeafBean(),
                errMsg.toString());
    }
}
