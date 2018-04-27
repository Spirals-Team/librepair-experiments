package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeSIG;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Code SIG
 * 
 * @author Greg Dujardin
 *
 */
public interface CodeSIGService extends CrudService<CodeSIG>{

	/**
	 * Récupération de tous les codes SIG
	 * 
	 * @return Liste des codes SIG
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeSIG> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des codes SIG sous forme de CodeLabel
	 * 
	 * @return Liste des codes SIG sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
