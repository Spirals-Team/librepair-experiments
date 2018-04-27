package nc.noumea.mairie.bilan.energie.contract.service;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

/**
 * Interface du service Sécurité
 * 
 * @author Greg Dujardin
 *
 */
public interface SecurityService {

	/**
	 * Retourne le nom de l'utilisateur courant
	 * 
	 * @return nom de l'utilisateur connecté
     * @throws TechnicalException Exception technique
	 */
	String getCurrentUserName() throws TechnicalException;
	
	/**
	 * Retourne l'utilisateur courant
	 * 
	 * @return utilisateur connecté
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier 
	 */
	Utilisateur getCurrentUser() throws TechnicalException, BusinessException;
	
	/**
	 * Utilisateur courant à un rôle
	 * 
	 * @param role rôle à tester
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean hasRole(String role) throws TechnicalException, BusinessException;
	
	/**
	 * Utilisateur des EPs ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isUtilisateurEP() throws TechnicalException, BusinessException;
	
	/**
	 * Utilisateur des bâtiments ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isUtilisateurBatiment() throws TechnicalException, BusinessException;
	
	/**
	 * Administrateur ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isAdministrateur() throws TechnicalException, BusinessException;
	
	/**
	 * Contributeur ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isContributeur() throws TechnicalException, BusinessException;
	
	/**
	 * Administrateur Batiment ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isAdministrateurBatiment() throws TechnicalException, BusinessException;

	/**
	 * Contributeur Batiment ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isContributeurBatiment() throws TechnicalException, BusinessException;

	/**
	 * Visiteur Batiment ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isVisiteurBatiment() throws TechnicalException, BusinessException;

	/**
	 * Administrateur EP ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isAdministrateurEP() throws TechnicalException, BusinessException;

	/**
	 * Contributeur EP ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isContributeurEP() throws TechnicalException, BusinessException;

	/**
	 * Visiteur EP ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	boolean isVisiteurEP() throws TechnicalException, BusinessException;
	
	
	/**
	 * Permet d'enregistrer l'utilisateur courant comme "logué"
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	public void loginCurrentUser() throws TechnicalException, BusinessException;
	
	/**
	 * Permet d'enregistrer l'utilisateur courant comme "dé-logué"
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	public void logoutCurrentUser() throws TechnicalException, BusinessException;
}
