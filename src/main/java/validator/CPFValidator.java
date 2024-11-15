package validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String>{

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		if(cpf.length() != 11) {
			return false;
		}
		String validatorDigits;
		//calcula e concatena o décimo digito e o décimo primeiro digito
		validatorDigits = calculateDigit(10, cpf) + calculateDigit(11, cpf);
		if(cpf.endsWith(validatorDigits)) {
			return true;
		}
		return false;
	}
	
	private String calculateDigit(int nthDigit, String cpf) {
		int digit;
		int remainder;
		int weight = 9;
		int sum = 0;
		for(int i = nthDigit-2; i >= 0; --i) {
			digit = Character.getNumericValue(cpf.charAt(i));
			sum = sum + (digit * weight);
			--weight;
		}
		remainder = sum % 11;
		if(remainder == 10) {
			return "0";
		}
		else {
			return String.valueOf(remainder);
		}
	}
}
