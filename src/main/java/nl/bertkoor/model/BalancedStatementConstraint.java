package nl.bertkoor.model;

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
    String message() default "It doesn't add up";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
