package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeZone;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service TypeZone
 * 
 * @author Greg Dujardin
 *
 */
public interface TypeZoneService extends CrudService<TypeZone>{

	/**
	 * Récupération de toutes les Type Zone
	 * 
	 * @return Liste des types de zone
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<TypeZone> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Récupération de toutes les Type Zone au format CodeLabel
	 * 
	 * @return Liste des types de zone
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
