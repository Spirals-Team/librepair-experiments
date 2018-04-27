package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Parametrage
 * 
 * @author Greg Dujardin
 * 
 */
@Entity
@Table(name = "PARAMETRAGE")
public class ParametrageEntity extends AbstractEntity {

	/** Identifiant technique */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idParametrageGenerator")
	@SequenceGenerator(name = "idParametrageGenerator", sequenceName = "SEQ_PARAMETRAGE_ID", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	/** Libelle */
	@Column(name = "PARAMETRE")
	private String parametre;

	/** Valeur */
	@Column(name = "VALEUR")
	private String valeur;

	/** Description */
	@Column(name = "DESCRIPTION")
	private String description;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the parametre
	 */
	public String getParametre() {
		return parametre;
	}

	/**
	 * @param parametre
	 *            the parametre to set
	 */
	public void setParametre(String parametre) {
		this.parametre = parametre;
	}

	/**
	 * @return the valeur
	 */
	public String getValeur() {
		return valeur;
	}

	/**
	 * @param valeur
	 *            the valeur to set
	 */
	public void setValeur(String valeur) {
		this.valeur = valeur;
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

}
