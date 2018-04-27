package nc.noumea.mairie.bilan.energie.contract.service;

import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Utilisateur
 * 
 * @author Greg Dujardin
 *
 */
public interface UtilisateurService extends CrudService<Utilisateur>{

	/**
	 * Récupération de tous les utilisateurs
	 * 
	 * @return Liste des utilsateurs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Utilisateur> getAll() throws TechnicalException, BusinessException;

	/**
	 * Récupération d'un utilisateur par son login
	 * 
	 * @param login login recherché
	 * @return l'utilisateur
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	Utilisateur getUtilisateurByLogin(String login) throws TechnicalException,
			BusinessException;

	
	/**
	 * Récupération des utilisateurs par nom
	 * 
	 * @param nom Nom de l'utilisateur
	 * @param historique Critère historique
	 * @return Liste des utilisateurs
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	List<Utilisateur> getAllByNom(String nom, boolean historique) throws TechnicalException,
	BusinessException;
}
