package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;
import nc.noumea.mairie.bilan.energie.contract.enumeration.Role;


/**
 * DTO Utilisateur
 * 
 * @author Greg Dujardin
 *
 */
public class Utilisateur implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Version de l'entité */
	private long version;
	
	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Login de l'utilisateur */
	private String login;
	
	/** Nom de l'utilisateur */
	private String nom;
	
	/** Prénom de l'utilisateur */
	private String prenom;
	
	/** Liste des rôles */
	private List<UtilisateurRole> listeUtilisateurRole;
	
	/** Date de début d'autorisation */
	private Date dateDebut;
	
	/** Date de fin d'autorisation */
	private Date dateFin;
	
	/** Date de dernière connexion */
	private Date derniereConnexion;
	
	/** Date de modification */
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	private String auteurModif;

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * get ListeUtilisateurRole
	 *
	 * @return listeUtilisateurRole
	 */
	public List<UtilisateurRole> getListeUtilisateurRole() {
		return listeUtilisateurRole;
	}

	/**
	 * set ListeUtilisateurRole
	 *
	 * @param listeUtilisateurRole ListeUtilisateurRole to set
	 */
	public void setListeUtilisateurRole(List<UtilisateurRole> listeUtilisateurRole) {
		this.listeUtilisateurRole = listeUtilisateurRole;
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * get DerniereConnexion
	 *
	 * @return DerniereConnexion
	 */
	public Date getDerniereConnexion() {
		return derniereConnexion;
	}

	/**
	 * set DerniereConnexion
	 *
	 * @param derniereConnexion DerniereConnexion to set
	 */
	public void setDerniereConnexion(Date derniereConnexion) {
		this.derniereConnexion = derniereConnexion;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the auteurCreation
	 */
	public String getAuteurCreation() {
		return auteurCreation;
	}

	/**
	 * @param auteurCreation the auteurCreation to set
	 */
	public void setAuteurCreation(String auteurCreation) {
		this.auteurCreation = auteurCreation;
	}

	/**
	 * @return the dateModif
	 */
	public Date getDateModif() {
		return dateModif;
	}

	/**
	 * @param dateModif the dateModif to set
	 */
	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}

	/**
	 * @return the auteurModif
	 */
	public String getAuteurModif() {
		return auteurModif;
	}

	/**
	 * @param auteurModif the auteurModif to set
	 */
	public void setAuteurModif(String auteurModif) {
		this.auteurModif = auteurModif;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * l'utilisateur est actif ?
	 * 
	 * @return utilisateur actif ?
	 */
	public boolean isActif() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date dateJour = calendar.getTime();
		
		return (this.getDateFin() == null || this.getDateFin().after(dateJour));
	}
	
	/**
	 * Utilisateur a un rôle ?
	 * 
	 * @param role
	 *            rôle à tester
	 * @return true / false
	 */
	public boolean hasRole(Role role) {

		return hasRole(role.toString());
	}

	/**
	 * Utilisateur courant à un rôle
	 * 
	 * @param role
	 *            rôle à tester
	 * @return true / false
	 */
	public boolean hasRole(String role) {

		Role roleToTest = Role.valueOf(role);
		
		for (UtilisateurRole utilisateurRole : getListeUtilisateurRole()) {
			if (utilisateurRole.getRole().equals(roleToTest))
				return true;
		}
		return false;
	}

	/**
	 * isAdministrateurBatiment ?
	 *
	 * @return booléen
	 */
	public boolean isAdministrateurBatiment()  {
		return hasRole(Role.ADMINISTRATEUR_BATIMENT);
	}

	/**
	 * isContributeurBatiment ?
	 *
	 * @return booléen
	 */
	public boolean isContributeurBatiment()  {
		return hasRole(Role.CONTRIBUTEUR_BATIMENT);
	}

	/**
	 * isVisiteurBatiment ?
	 *
	 * @return booléen
	 */ 
	public boolean isVisiteurBatiment()  {
		return hasRole(Role.VISITEUR_BATIMENT);
	}

	/**
	 * isAdministrateurEP ?
	 *
	 * @return booléen
	 */
	public boolean isAdministrateurEP()  {
		return hasRole(Role.ADMINISTRATEUR_EP);
	}

	/**
	 * isContributeurEP ?
	 *
	 * @return booléen
	 */
	public boolean isContributeurEP()  {
		return hasRole(Role.CONTRIBUTEUR_EP);
	}

	/**
	 * isVisiteurEP ?
	 *
	 * @return booléen
	 */
	public boolean isVisiteurEP() {
		return hasRole(Role.VISITEUR_EP);
	}

}
