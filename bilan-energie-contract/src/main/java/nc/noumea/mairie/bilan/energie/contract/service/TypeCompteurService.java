package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeCompteur;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypeCompteur
 * 
 * @author Greg Dujardin
 *
 */
public interface TypeCompteurService extends CrudService<TypeCompteur>{

	/**
	 * Récupération de tous les types de compteur
	 * 
	 * @return Liste des types de compteur
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypeCompteur> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des types de compteur sous forme de CodeLabel
	 *
	 * @return Liste des types de compteur sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception Métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
