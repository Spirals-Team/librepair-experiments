package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnalyse;

/**
 * Entity Analyse
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="ANALYSE_RAPPORT")
public class AnalyseEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idAnalyseGenerator")
	    @SequenceGenerator(
	        name="idAnalyseGenerator",
	        sequenceName="SEQ_ANALYSE_RAPPORT_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Designation */
	@Column (name = "DESIGNATION")
	private String designation;
	
	/** Structure à laquelle est rattachée l'analyse */
	@ManyToOne
	@JoinColumn(name = "STRUCTURE_ID")
	private StructureEntity structure;
	
	/** Type de l'analyse */
	@Column(name = "TYPE")
	private TypeAnalyse type;
	
	/** Période */
	@Column(name = "PERIODE")
	private String periode;
	
	/** Position */
	@Column(name = "POSITION")
	private String position;
	
	/** Detail */
	@Column(name = "DETAIL")
	private String detail;
	
	/** Auteur */
	@Column(name = "AUTEUR")
	private String auteur;

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
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the structure
	 */
	public StructureEntity getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(StructureEntity structure) {
		this.structure = structure;
	}

	/**
	 * @return the type
	 */
	public TypeAnalyse getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeAnalyse type) {
		this.type = type;
	}

	/**
	 * @return the periode
	 */
	public String getPeriode() {
		return periode;
	}

	/**
	 * @param periode the periode to set
	 */
	public void setPeriode(String periode) {
		this.periode = periode;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the auteur
	 */
	public String getAuteur() {
		return auteur;
	}

	/**
	 * @param auteur the auteur to set
	 */
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	
	
}
