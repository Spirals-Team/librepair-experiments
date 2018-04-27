package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Client;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Client
 * 
 * @author Greg Dujardin
 *
 */
public interface ClientService extends CrudService<Client>{

	/**
	 * Récupération de tous les clients
	 * 
	 * @return Liste des clients
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Client> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Rechercher des Clients sous forme de CodeLabel
	 * 
	 * @return Liste des clients sous forme de CodeLabel
	 * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<CodeLabel> getAllReferentiel() throws TechnicalException, BusinessException;
}
