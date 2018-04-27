package nc.noumea.mairie.bilan.energie.contract.dto;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;


/**
 * DTO Adresse
 * 
 * @author Greg Dujardin
 *
 */
public class Adresse implements DtoModel  {
	
	private static final long serialVersionUID = 1L;

	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Version */
	private long version;
	
	/** Identifiant dans la table adresses_consolidees */
	private Long objectId;

	/** Adresse ligne 1 */
	private String adresseLigne1;
	
	/** Quartier */
	private String quartier;
	
	/** Commune */
	private String commune;
	
	/** Adresse obsolète ? */
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
	 * get Version
	 * 
	 * @return the version
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
	 * obsolete ?
	 * 
	 * @return booléen
	 */
	public boolean isObsolete() {
		return obsolete;
	}

	/**
	 * set Obsolete
	 * 
	 * @param obsolete Booléen to set
	 */
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	
}
