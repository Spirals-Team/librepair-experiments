package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Adresse;
import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Adresse
 * 
 * @author Greg Dujardin
 *
 */
public interface AdresseService extends CrudService<Adresse> {

	/**
	 * Récupération de toutes les adresses
	 * 
	 * @return Liste des adresses
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Adresse> getAll() throws TechnicalException, BusinessException;
	
	/**
	 * Recherche d'une adresse par son numéro de voie
	 * 
	 * @param nomVoie nom de la voie à rechercher
	 * @return Liste des adresses trouvées
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<AdresseLabel> getAllAdresseByVoie(String nomVoie) throws TechnicalException, BusinessException;
	
	
	/**
	 * Recherche d'une adresse par son identifiant objectId
	 * 
	 * @param objectId identifiant interne de l'adresse
	 * @return adresse trouvée
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	Adresse getAdresseByObjectId(Long objectId) throws TechnicalException, BusinessException;
	
}
