package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Adresse
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="ADRESSE")
public class AdresseEntity extends AbstractEntity {
	
	/** Identifiant technique */
	@Id
	@GeneratedValue(
	        strategy=GenerationType.SEQUENCE, 
	        generator="idAdresseGenerator")
	    @SequenceGenerator(
	        name="idAdresseGenerator",
	        sequenceName="SEQ_ADRESSE_ID",
	        allocationSize=1
	    )
	@Column(name = "ID")
	private Long id;
	
	/** Identifiant dans la table adresses_consolidees */
	@Column(name = "OBJECTID")
	private Long objectId;

	/** Adresse ligne 1 */
	@Column(name = "ADRESSE_LIGNE1")
	private String adresseLigne1;
	
	/** Quartier */
	@Column(name = "QUARTIER")
	private String quartier;
	
	/** Commune */
	@Column(name = "COMMUNE")
	private String commune;
	
	/** Adresse obsolète ? */
	@Column(name = "OBSOLETE")
	private boolean obsolete;

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
	 * @return the objectId
	 */
	public Long getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the adresseLigne1
	 */
	public String getAdresseLigne1() {
		return adresseLigne1;
	}

	/**
	 * @param adresseLigne1 the adresseLigne1 to set
	 */
	public void setAdresseLigne1(String adresseLigne1) {
		this.adresseLigne1 = adresseLigne1;
	}

	/**
	 * @return the quartier
	 */
	public String getQuartier() {
		return quartier;
	}

	/**
	 * @param quartier the quartier to set
	 */
	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	/**
	 * @return the commune
	 */
	public String getCommune() {
		return commune;
	}

	/**
	 * @param commune the commune to set
	 */
	public void setCommune(String commune) {
		this.commune = commune;
	}

	/**
	 * @return the obsolete
	 */
	public boolean isObsolete() {
		return obsolete;
	}

	/**
	 * @param obsolete the obsolete to set
	 */
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	
}
