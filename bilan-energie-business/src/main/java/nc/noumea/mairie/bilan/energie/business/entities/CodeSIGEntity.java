package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Code SIG
 * 
 * @author Greg Dujardin
 * 
 */
@Entity
@Table(name = "CODE_SIG")
public class CodeSIGEntity extends AbstractEntity {

	/** Identifiant technique */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idCodeSIGGenerator")
	@SequenceGenerator(name = "idCodeSIGGenerator", sequenceName = "SEQ_CODE_SIG_ID", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	/** Libelle */
	@Column(name = "LIBELLE")
	private String libelle;

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
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle
	 *            the libelle to set
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
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
