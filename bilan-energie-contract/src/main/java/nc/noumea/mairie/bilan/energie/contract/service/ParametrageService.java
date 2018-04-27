package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Parametrage
 * 
 * @author Greg Dujardin
 *
 */
public interface ParametrageService extends CrudService<Parametrage>{

	/**
	 * Récupération de toutes les Type Support
	 * 
	 * @return Liste des paramétrages
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Parametrage> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche des paramétres par nom
	 *
	 * @param parametre Nom du paramétre
	 * @return parametrage
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	Parametrage getByParametre(String parametre)  throws TechnicalException, BusinessException;

}
