package nc.noumea.mairie.bilan.energie.business.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Conversion
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="CONVERSION")
public class ConversionEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idConversionGenerator")
	    @SequenceGenerator(
	        name="idConversionGenerator",
	        sequenceName="SEQ_CONVERSION_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Unité de gestion */
	@Column(name = "UNITE_GES")
	private String uniteGes;
	
	/** Coefficient de conversion en TEP */
	@Column(name = "CONVERT_TEP")
	private BigDecimal convertTep;
	
	/** Coefficient de conversion en TeqCO2 */
	@Column(name = "CONVERT_TEQCO")
	private BigDecimal convertTeqCO2;
	
	/** Coefficient de conversion en Kw */
	@Column(name = "CONVERT_KW")
	private BigDecimal convertKw;
	
	/** Date de début d'utilisation du taux */
	@Column(name = "DATE_DEBUT")
	private Date dateDebut;
	
	/** Date de fin d'utilisation du taux */
	@Column(name = "DATE_FIN")
	private Date dateFin;

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
	 * @return the uniteGes
	 */
	public String getUniteGes() {
		return uniteGes;
	}

	/**
	 * @param uniteGes the uniteGes to set
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
	 * @param convertTep the convertTep to set
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
	 * @param convertTeqCO2 the convertTeqCO2 to set
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
	 * @param convertKw the convertKw to set
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

}
