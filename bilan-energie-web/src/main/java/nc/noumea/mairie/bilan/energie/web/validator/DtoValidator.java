package nc.noumea.mairie.bilan.energie.web.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import nc.noumea.mairie.bilan.energie.contract.dto.DtoModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

/**
 * Validateur ZK Customizé afin de vérifier les dto utilisant l'API Validation
 * de Java
 * 
 * @author David ALEXIS
 * 
 */
@Service
public class DtoValidator extends AbstractValidator {

	/**
	 * Injection du validateur
	 */
	@Autowired
	private Validator validator;

	/*
	 * @see org.zkoss.bind.Validator#validate(org.zkoss.bind.ValidationContext)
	 */
	@Override
	public void validate(ValidationContext ctx) {

		DtoModel dto = (DtoModel) ctx.getValidatorArg("dto");

		Set<ConstraintViolation<DtoModel>> constrainte = validator
				.validate(dto);

		for (ConstraintViolation<DtoModel> item : constrainte) {
			addInvalidMessage(ctx, item.getPropertyPath().toString(),
					item.getMessage());
		}
	}

}
