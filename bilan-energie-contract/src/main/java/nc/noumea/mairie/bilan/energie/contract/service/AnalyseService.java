package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Analyse;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Analyse
 * 
 * @author Greg Dujardin
 *
 */
public interface AnalyseService extends CrudService<Analyse>{

	/**
	 * Récupération de toutes les analyses
	 * 
	 * @return Liste des analyses
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Analyse> getAll() throws TechnicalException, BusinessException;
	
}
