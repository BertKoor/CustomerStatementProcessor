package nl.bertkoor.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BalancedStatementValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BalancedStatementConstraint {
    String message() default "will be overwritten";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
