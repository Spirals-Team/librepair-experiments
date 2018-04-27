package nc.noumea.mairie.bilan.energie.business.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Utilisateur
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="UTILISATEUR")
public class UtilisateurEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idUtilisateurGenerator")
	    @SequenceGenerator(
	        name="idUtilisateurGenerator",
	        sequenceName="SEQ_UTILISATEUR_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Login de l'utilisateur */
	@Column(name = "LOGIN")
	private String login;
	
	/** Nom de l'utilisateur */
	@Column(name = "NOM")
	private String nom;
	
	/** Prénom de l'utilisateur */
	@Column(name = "PRENOM")
	private String prenom;
	
	/** Liste des rôles */
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true, mappedBy = "utilisateur")
	private List<UtilisateurRoleEntity> listeUtilisateurRole;
	
	/** Date de début d'autorisation */
	@Column(name = "DATE_DEBUT")
	private Date dateDebut;
	
	/** Date de fin d'autorisation */
	@Column(name = "DATE_FIN")
	private Date dateFin;

	/** Date dde dernière connexion */
	@Column(name = "DERNIERE_CONNEXION")
	private Date derniereConnexion;

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
	 * @return the listeUtilisateurRole
	 */
	public List<UtilisateurRoleEntity> getListeUtilisateurRole() {
		return listeUtilisateurRole;
	}

	/**
	 * @param listeUtilisateurRole the listeUtilisateurRole to set
	 */
	public void setListeUtilisateurRole(
			List<UtilisateurRoleEntity> listeUtilisateurRole) {
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
	 * @return the derniereConnexion
	 */
	public Date getDerniereConnexion() {
		return derniereConnexion;
	}

	/**
	 * @param derniereConnexion the derniereConnexion to set
	 */
	public void setDerniereConnexion(Date derniereConnexion) {
		this.derniereConnexion = derniereConnexion;
	}

	
}
