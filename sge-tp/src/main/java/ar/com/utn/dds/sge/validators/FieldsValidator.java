package ar.com.utn.dds.sge.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import ar.com.utn.dds.sge.constants.ConstantesErrores;
import ar.com.utn.dds.sge.exceptions.FieldValidationException;

public class FieldsValidator<T>{

	private Validator validator;
	
	public FieldsValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public void validarAtributosRequeridos(List<T> entidades) throws FieldValidationException{
		Set<ConstraintViolation<T>> violations = new HashSet<ConstraintViolation<T>> ();
		String msg = "";
		
		for(T entidad : entidades) {
			violations.addAll(validator.validate(entidad));
		}
		
		for (ConstraintViolation<T> violation : violations) {
			msg += violation.getMessage()+ "\n";
		}
		
		if(!violations.isEmpty())
			throw new FieldValidationException(msg + ConstantesErrores.CAMPOS_REQUERIDOS_VACIOS);
	}
	
}
