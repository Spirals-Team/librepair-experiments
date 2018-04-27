package nc.noumea.mairie.bilan.energie.business.dao;

import java.security.Principal;
import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.dto.UtilisateurRole;
import nc.noumea.mairie.bilan.energie.contract.enumeration.Role;
import nc.noumea.mairie.bilan.energie.contract.exceptions.DataAccessException;
import nc.noumea.mairie.bilan.energie.contract.service.SecurityService;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * Service de gestion de la sécurité
 * 
 * @author Greg Dujardin
 * 
 */
@Service("securityService")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SecurityBusiness implements SecurityService {

	/** Principal */
	@Autowired
	private Principal principal;

	/** Service des utilisateurs */
	@Autowired
	private UtilisateurService utilisateurService;

	/** Utilisateur Courant */
	private Utilisateur userCourant;

	/**
	 * Retourne le nom de l'utilisateur courant
	 * 
	 * @return nom de l'utilisateur connecté
     * @throws TechnicalException Exception technique
	 */
	@Override
	public String getCurrentUserName() throws TechnicalException {

		String userName = "";

		try {
			userName = this.getCurrentUser().getNom() + " "
					+ this.getCurrentUser().getPrenom() + " ("
					+ this.getCurrentUser().getLogin() + ")";
		} catch (BusinessException e) {
			throw new DataAccessException(
					"Erreur lors de la lecture de l'utilisateur", e);
		}

		return userName;
	}

	/**
	 * Retourne l'utilisateur courant
	 * 
	 * @return utilisateur connecté
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public Utilisateur getCurrentUser() throws TechnicalException,
			BusinessException {

		if (userCourant == null)
			userCourant = utilisateurService.getUtilisateurByLogin(principal
					.getName());

		return userCourant;
	}

	/**
	 * Utilisateur a un rôle ?
	 * 
	 * @param role
	 *            rôle à tester
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	private boolean hasRole(Role role) throws TechnicalException,
			BusinessException {

		return hasRole(role.toString());
	}

	/**
	 * Utilisateur courant à un rôle
	 * 
	 * @param role
	 *            rôle à tester
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public boolean hasRole(String role) throws TechnicalException,
			BusinessException {

		Utilisateur utilisateur = getCurrentUser();

		Role roleToTest = Role.valueOf(role);
		
		for (UtilisateurRole utilisateurRole : utilisateur.getListeUtilisateurRole()) {
			if (utilisateurRole.getRole().equals(roleToTest))
				return true;
		}
		return false;
	}

	/**
	 * Utilisateur des EPs ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public boolean isUtilisateurEP() throws TechnicalException,
			BusinessException {
		return hasRole(Role.ADMINISTRATEUR_EP) || hasRole(Role.CONTRIBUTEUR_EP)
				|| hasRole(Role.VISITEUR_EP);
	}

	/**
	 * Utilisateur des Bâtiments ?
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public boolean isUtilisateurBatiment() throws TechnicalException,
			BusinessException {
		return hasRole(Role.ADMINISTRATEUR_BATIMENT)
				|| hasRole(Role.CONTRIBUTEUR_BATIMENT)
				|| hasRole(Role.VISITEUR_BATIMENT);
	}

	/**
	 * Administrateur
	 * 
	 * @return true / false
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Override
	public boolean isAdministrateur() throws TechnicalException,
			BusinessException {
		return hasRole(Role.ADMINISTRATEUR_BATIMENT)
				|| hasRole(Role.ADMINISTRATEUR_EP);

	}

	@Override
	public boolean isContributeur() throws TechnicalException,
			BusinessException {
		return isAdministrateur() || isContributeurBatiment()
				|| isContributeurEP();
	}

	@Override
	public boolean isAdministrateurBatiment() throws TechnicalException,
			BusinessException {
		return hasRole(Role.ADMINISTRATEUR_BATIMENT);
	}

	@Override
	public boolean isContributeurBatiment() throws TechnicalException,
			BusinessException {
		return hasRole(Role.CONTRIBUTEUR_BATIMENT);
	}

	@Override
	public boolean isVisiteurBatiment() throws TechnicalException,
			BusinessException {
		return hasRole(Role.VISITEUR_BATIMENT);
	}

	@Override
	public boolean isAdministrateurEP() throws TechnicalException,
			BusinessException {
		return hasRole(Role.ADMINISTRATEUR_EP);
	}

	@Override
	public boolean isContributeurEP() throws TechnicalException,
			BusinessException {
		return hasRole(Role.CONTRIBUTEUR_EP);
	}

	@Override
	public boolean isVisiteurEP() throws TechnicalException, BusinessException {
		return hasRole(Role.VISITEUR_EP);
	}

	/**
	 * @see nc.noumea.mairie.bilan.energie.contract.service.SecurityService#loginCurrentUser()
	 */
	@Override
	public void loginCurrentUser() throws TechnicalException, BusinessException {
		
		// Mise à jour de la dernière date de connexion
		Utilisateur user = utilisateurService.getUtilisateurByLogin(getCurrentUser().getLogin());
		user.setDerniereConnexion(new Date());
		utilisateurService.update(user);
	}

	/**
	 * @see nc.noumea.mairie.bilan.energie.contract.service.SecurityService#logoutCurrentUser()
	 */
	@Override
	public void logoutCurrentUser() throws TechnicalException, BusinessException {

		// Mise à jour de la dernière date de connexion
		Utilisateur user = utilisateurService.getUtilisateurByLogin(getCurrentUser().getLogin());
		user.setDerniereConnexion(null);
		utilisateurService.update(user);
	}

}
