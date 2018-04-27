package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSupport;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypeSupport
 * 
 * @author Greg Dujardin
 *
 */
public interface TypeSupportService extends CrudService<TypeSupport>{

	/**
	 * Récupération de toutes les Type Support
	 * 
	 * @return Liste des types de support
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypeSupport> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Récupération de toutes les Type Support au format CodeLabel
	 * 
	 * @return Liste des types de support
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
