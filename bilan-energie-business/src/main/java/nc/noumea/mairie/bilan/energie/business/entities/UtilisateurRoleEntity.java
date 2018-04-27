package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nc.noumea.mairie.bilan.energie.contract.enumeration.Role;

/**
 * Entity Utilisateur
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="UTILISATEUR_ROLE")
public class UtilisateurRoleEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idUtilisateurRoleGenerator")
	    @SequenceGenerator(
	        name="idUtilisateurRoleGenerator",
	        sequenceName="SEQ_UTILISATEUR_ROLE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Utilisateur */
	@ManyToOne
	@JoinColumn(name = "UTILISATEUR_ID")
	private UtilisateurEntity utilisateur;
	
	/** Rôle */
	@Column(name = "ROLE")
	@Enumerated(EnumType.STRING)
	private Role role;

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
	 * @return the utilisateur
	 */
	public UtilisateurEntity getUtilisateur() {
		return utilisateur;
	}

	/**
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(UtilisateurEntity utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	
}
