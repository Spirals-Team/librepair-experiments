package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSource;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypeSource
 * 
 * @author Greg Dujardin
 *
 */
public interface TypeSourceService extends CrudService<TypeSource>{

	/**
	 * Récupération de toutes les Type Source
	 * 
	 * @return Liste des types de source
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypeSource> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Récupération de toutes les Type Source au format CodeLabel
	 * 
	 * @return Liste des types de source
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
