package validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD,
	ElementType.PARAMETER,
	ElementType.METHOD,
	ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CPFValidator.class)
public @interface CPF {

	String message() default "CPF inválido";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
