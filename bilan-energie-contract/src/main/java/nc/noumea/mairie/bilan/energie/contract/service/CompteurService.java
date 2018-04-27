package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Compteur
 * 
 * @author Greg Dujardin
 *
 */
public interface CompteurService extends CrudService<Compteur>{

	public static final String ERROR_NUMERO_DOUBLON_PERIODE = "Il existe déjà un compteur pour cette période avec le numéro '";
	public static final String ERROR_NUMERO_DOUBLON_POLICE = "Il existe déjà un compteur ce numéro pour cette police'";
	public static final String ERROR_HORS_PERIODE_POLICE = "La période du compteur est en dehors de la période de la police.";
	public static final String ERROR_DATE_FIN_NOK = "La date de fin du compteur ne doit pas être antérieure à la date de début.";
	public static final String ERROR_DATE_FIN_NON_RENSEIGNEE = "La date de fin du compteur doit être renseignée car la date de fin de la police est renseignée.";


	/**
	 * Recherche des compteurs par numéro de compteur exact
	 * @param numeroCompteur Numéro du compteur
	 * 
	 * @return Liste des compteurs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Compteur> getAllByNumeroCompteurExact(String numeroCompteur) throws TechnicalException, BusinessException;
}
