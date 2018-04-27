package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CategoriePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Catégorie de Police
 * 
 * @author Greg Dujardin
 *
 */
public interface CategoriePoliceService extends CrudService<CategoriePolice>{

	/**
	 * Récupération de tous les catégories de Police
	 * 
	 * @return Liste des catégories de police
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CategoriePolice> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des catégories de Police sous forme de CodeLabel
	 * 
	 * @return Liste de catégories de police sous forme de code label
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
