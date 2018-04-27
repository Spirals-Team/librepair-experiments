package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Direction;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Direction
 * 
 * @author Greg Dujardin
 *
 */
public interface DirectionService extends CrudService<Direction>{

	/**
	 * Récupération de toutes les Directions
	 * 
	 * @return Liste des directions
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Direction> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des directions sous forme de CodeLabel
	 *
	 * @return Liste des directions sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
