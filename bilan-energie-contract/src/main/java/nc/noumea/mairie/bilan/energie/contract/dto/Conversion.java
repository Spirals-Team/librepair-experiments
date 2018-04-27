package nc.noumea.mairie.bilan.energie.contract.dto;

import java.math.BigDecimal;
import java.util.Date;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;

/**
 * DTO Conversion
 * 
 * @author Greg Dujardin
 * 
 */
public class Conversion implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;

	/** Version de l'entité */
	private long version;
	
	/** Unité de gestion */
	private String uniteGes;

	/** Coefficient de conversion en TEP */
	private BigDecimal convertTep;

	/** Coefficient de conversion en TeqCO2 */
	private BigDecimal convertTeqCO2;

	/** Coefficient de conversion en Kw */
	private BigDecimal convertKw;

	/** Date de début d'utilisation du taux */
	private Date dateDebut;

	/** Date de fin d'utilisation du taux */
	private Date dateFin;

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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	 * @return the uniteGes
	 */
	public String getUniteGes() {
		return uniteGes;
	}

	/**
	 * @param uniteGes
	 *            the uniteGes to set
	 */
	public void setUniteGes(String uniteGes) {
		this.uniteGes = uniteGes;
	}

	/**
	 * @return the convertTep
	 */
	public BigDecimal getConvertTep() {
		return convertTep;
	}

	/**
	 * @param convertTep
	 *            the convertTep to set
	 */
	public void setConvertTep(BigDecimal convertTep) {
		this.convertTep = convertTep;
	}

	/**
	 * @return the convertTeqCO2
	 */
	public BigDecimal getConvertTeqCO2() {
		return convertTeqCO2;
	}

	/**
	 * @param convertTeqCO2
	 *            the convertTeqCO2 to set
	 */
	public void setConvertTeqCO2(BigDecimal convertTeqCO2) {
		this.convertTeqCO2 = convertTeqCO2;
	}

	/**
	 * @return the convertKw
	 */
	public BigDecimal getConvertKw() {
		return convertKw;
	}

	/**
	 * @param convertKw
	 *            the convertKw to set
	 */
	public void setConvertKw(BigDecimal convertKw) {
		this.convertKw = convertKw;
	}

	/**
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut
	 *            the dateDebut to set
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
	 * @param dateFin
	 *            the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation
	 *            the dateCreation to set
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
	 * @param auteurCreation
	 *            the auteurCreation to set
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
	 * @param dateModif
	 *            the dateModif to set
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
	 * @param auteurModif
	 *            the auteurModif to set
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
