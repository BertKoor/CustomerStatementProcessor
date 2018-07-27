package nl.bertkoor.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatementCollectorValidator
        implements ConstraintValidator<StatementCollectorConstraint, StatementCollector> {

    @Override
    public void initialize(final StatementCollectorConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(final StatementCollector collector, final ConstraintValidatorContext context) {
        if (!collector.hasNewStatement()) {
            return true;
        }
        boolean isValid = !collector.referenceNumbersContainsNewStatement();
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("already processed") //
                    .addPropertyNode("referenceNumber") //
                    .addConstraintViolation();
        }
        return isValid;
    }
}
