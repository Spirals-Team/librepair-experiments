package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Conversion;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Conversion
 * 
 * @author Greg Dujardin
 *
 */
public interface ConversionService extends CrudService<Conversion>{

	/**
	 * Récupération de toutes les conversions
	 * 
	 * @return Liste des taux de conversion
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Conversion> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des taux de conversion sous forme de CodeLabel
	 *
	 * @return Liste des taux de conversion sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
