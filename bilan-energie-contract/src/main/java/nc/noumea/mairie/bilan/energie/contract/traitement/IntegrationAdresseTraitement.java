package nc.noumea.mairie.bilan.energie.contract.traitement;

import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface de traitement d'intégration des adresses
 *
 * @author Greg Dujardin
 *
 */
public interface IntegrationAdresseTraitement {

	/**
	 * Synchronisation des adresses
	 * 
	 * @throws BusinessException Exception métier 
     * @throws TechnicalException Exception technique 
	 */
	void synchronisationAdresse() throws TechnicalException, BusinessException;
}
