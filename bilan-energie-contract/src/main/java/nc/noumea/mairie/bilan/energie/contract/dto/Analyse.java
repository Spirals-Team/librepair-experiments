package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnalyse;

/**
 * DTO Analyse
 * 
 * @author Greg Dujardin
 *
 */
public class Analyse implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Version */
	private long version;
	
	/** Designation */
	private String designation;
	
	/** Structure à laquelle est rattachée l'analyse */
	private StructureLabel structure;
	
	/** Type de l'analyse */
	private TypeAnalyse type;
	
	/** Période */
	private String periode;
	
	/** Position */
	private String position;
	
	/** Detail */
	private String detail;
	
	/** Auteur */
	private String auteur;
	
	/** Date de modification */
	private Date dateCreation;
	
	/** Auteur de la dernière modification */
	private String auteurCreation;

	/** Date de modification */
	private Date dateModif;
	
	/** Auteur de la dernière modification */
	private String auteurModif;

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
	 * get Version
	 * 
	 * @return version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * set Version
	 * 
	 * @param version Version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * get Designation
	 * 
	 * @return désignation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * set Designation
	 * 
	 * @param designation Désignation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the structure
	 */
	public StructureLabel getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(StructureLabel structure) {
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
	
	
}
