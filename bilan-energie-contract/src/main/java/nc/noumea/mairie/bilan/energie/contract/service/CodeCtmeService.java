package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeCtme;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service CodeCtme
 * 
 * @author Greg Dujardin
 *
 */
public interface CodeCtmeService extends CrudService<CodeCtme>{

	/**
	 * Récupération de toutes les codes Ctme
	 * 
	 * @return Liste des codes CTME
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeCtme> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des codes CTME sous forme de CodeLabel
	 * 
	 * @return Liste des codes CTME sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
