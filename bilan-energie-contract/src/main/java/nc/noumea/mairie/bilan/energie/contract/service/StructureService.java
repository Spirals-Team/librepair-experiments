package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Structure
 * 
 * @author Greg Dujardin
 *
 */
public interface StructureService {

	/**
	 * Récupération de toutes les structures
	 * 
	 * @return Liste des structures 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<StructureLabel> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des structures par désignation
	 * 
	 * @param designation Désignation de la structure à rechercher
	 * @return Liste des structures trouvées
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<StructureLabel> getAllStructureByDesignation(String designation) throws TechnicalException, BusinessException;
}
