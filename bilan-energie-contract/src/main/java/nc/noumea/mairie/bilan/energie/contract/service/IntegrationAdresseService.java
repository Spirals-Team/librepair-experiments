package nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service d'intégration des adresses
 * 
 * @author Greg Dujardin
 *
 */
public interface IntegrationAdresseService {

	/**
	 * Récupération des adresses sur la base référentielle
	 * 
	 * @throws BusinessException Exception métier 
     * @throws TechnicalException Exception technique 
	 */
	void recuperationAdressesConsolidees()  throws TechnicalException, BusinessException;
	
}
