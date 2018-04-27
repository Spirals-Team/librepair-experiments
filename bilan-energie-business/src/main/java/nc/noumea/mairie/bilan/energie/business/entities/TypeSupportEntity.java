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
 * Entity Type Support
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="TYPE_SUPPORT")
public class TypeSupportEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idTypeSupportGenerator")
	    @SequenceGenerator(
	        name="idTypeSupportGenerator",
	        sequenceName="SEQ_TYPE_SUPPORT_ID",
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
	
	/** Code SIG du type Support */
	@OneToOne
	@JoinColumn(name = "CODE_SIG_ID")
	private CodeSIGEntity codeSIG;
	
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
	 * @return the codeSIG
	 */
	public CodeSIGEntity getCodeSIG() {
		return codeSIG;
	}

	/**
	 * @param codeSIG the codeSIG to set
	 */
	public void setCodeSIG(CodeSIGEntity codeSIG) {
		this.codeSIG = codeSIG;
	}

	
}
