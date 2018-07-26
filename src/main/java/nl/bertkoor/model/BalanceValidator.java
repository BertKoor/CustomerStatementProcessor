package nl.bertkoor.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class BalanceValidator implements ConstraintValidator<BalanceConstraint, CustomerStatement> {

    @Override
    public void initialize(BalanceConstraint customerStatementConstraint) {
    }

    @Override
    public boolean isValid(CustomerStatement customerStatement, ConstraintValidatorContext constraintValidatorContext) {
        if (customerStatement.getStartBalance() == null ||
                customerStatement.getMutation() == null ||
                customerStatement.getEndBalance() == null) {
            return true;
        }

        BigDecimal expectedEndBalance = customerStatement.getStartBalance() //
                .add(customerStatement.getMutation()) //
                .setScale(2);

        boolean isValid = expectedEndBalance.equals(customerStatement.getEndBalance().setScale(2));
        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext. //
                     buildConstraintViolationWithTemplate( "expected to be " + expectedEndBalance )
                    .addPropertyNode( "endBalance" )
                    .addConstraintViolation();
        }
        return isValid;
    }
}
