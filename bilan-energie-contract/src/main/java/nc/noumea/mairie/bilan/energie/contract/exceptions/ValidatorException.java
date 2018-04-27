package nc.noumea.mairie.bilan.energie.contract.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

import nc.noumea.mairie.bilan.energie.contract.dto.DtoModel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;

/**
 * Exception levée lors d'une validation d'une DTO
 * 
 * @author Greg Dujardin
 * 
 */
public class ValidatorException extends BusinessException {

	private static final long serialVersionUID = 1L;

	/**
	 * Contrainte levé sur un modèle
	 */
	private Set<ConstraintViolation<DtoModel>> constrainte;

	/**
	 * Constructeur
	 * 
	 * @param constrainte Ensemble des contraintes qui lèvent l'exception
	 */
	public ValidatorException(Set<ConstraintViolation<DtoModel>> constrainte) {
		super();
		this.constrainte = constrainte;
	}

	/**
	 * @return {@link #constrainte}
	 */
	public Set<ConstraintViolation<DtoModel>> getConstrainte() {
		return constrainte;
	}
}
