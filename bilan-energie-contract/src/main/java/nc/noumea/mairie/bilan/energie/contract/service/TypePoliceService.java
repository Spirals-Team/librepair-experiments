package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypePolice;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypePolice
 * 
 * @author Greg Dujardin
 *
 */
public interface TypePoliceService extends CrudService<TypePolice>{

	/**
	 * Récupération de toutes les Type de police
	 * 
	 * @return Liste des types de police
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypePolice> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Récupération de toutes les Type de police au format CodeLabel
	 * 
	 * @return Liste des types de police
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
