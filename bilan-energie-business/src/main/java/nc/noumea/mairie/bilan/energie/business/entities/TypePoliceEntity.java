package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Type Police
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="TYPE_POLICE")
public class TypePoliceEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idTypePoliceGenerator")
	    @SequenceGenerator(
	        name="idTypePoliceGenerator",
	        sequenceName="SEQ_TYPE_POLICE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Libelle */
	@Column(name = "LIBELLE")
	private String libelle;
	
	/** Description */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/** Comptage modifiable ? */
	@Column(name = "COMPTAGE_MODIFIABLE")
	private boolean comptageModifiable;
	
	/** Catégorie du type de Police */
	@OneToOne
	@JoinColumn(name = "CATEGORIE_POLICE_ID")
	private CategoriePoliceEntity categorie;
	
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
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the comptageModifiable
	 */
	public boolean isComptageModifiable() {
		return comptageModifiable;
	}

	/**
	 * @param comptageModifiable the comptageModifiable to set
	 */
	public void setComptageModifiable(boolean comptageModifiable) {
		this.comptageModifiable = comptageModifiable;
	}

	/**
	 * @return the categorie
	 */
	public CategoriePoliceEntity getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set
	 */
	public void setCategorie(CategoriePoliceEntity categorie) {
		this.categorie = categorie;
	}

	
}
