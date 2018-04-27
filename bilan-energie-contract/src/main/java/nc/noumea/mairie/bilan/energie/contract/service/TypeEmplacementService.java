package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeEmplacement;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypeCompteur
 * 
 * @author Greg Dujardin
 *
 */
public interface TypeEmplacementService extends CrudService<TypeEmplacement>{

	/**
	 * Récupération de tous les types d'emplacement
	 * 
	 * @return Liste des types d'emplacement
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypeEmplacement> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Récupération de tous les types d'emplacement au format CodeLabel
	 * 
	 * @return Liste des types d'emplacement
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
