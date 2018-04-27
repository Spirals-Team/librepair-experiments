package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.PoliceSimple;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Police
 * 
 * @author Greg Dujardin
 * 
 */
public interface PoliceService extends CrudService<Police> {

	public static final String ERROR_NUMERO_DOUBLON = "Il existe déjà une police avec le numéro '";
	public static final String ERROR_DATE_FIN_NOK = "La date de fin de la police ne doit pas être antérieure à la date de début.";

	/**
	 * Récupération de toutes les polices
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<PoliceSimple> getAll() throws TechnicalException, BusinessException;

	/**
	 * Recherche des polices par numéro de police
	 * 
	 * @param numeroPolice Numéro de police
	 * @param historique Critère historique
	 * @param chercherEP Critère 'Chercher les EPs'
	 * @param chercherBatiment Critère 'Chercher les batiments'
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<PoliceSimple> getAllByNumeroPolice(String numeroPolice,
			boolean historique, boolean chercherEP, boolean chercherBatiment)
			throws TechnicalException, BusinessException;

	/**
	 * Recherche des polices par numéro de police exact
	 * 
	 * @param numeroPolice Numéro de police
	 * 
	 * @return Liste des polices
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Police> getAllByNumeroPoliceExact(String numeroPolice)
			throws TechnicalException, BusinessException;
}
