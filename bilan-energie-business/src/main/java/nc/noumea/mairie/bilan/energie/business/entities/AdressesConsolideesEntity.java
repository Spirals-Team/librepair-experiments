package nc.noumea.mairie.bilan.energie.business.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity Adresses Consolidees
 * 
 * @author Greg Dujardin
 *
 */
@Entity
@Table(name="ADRESSES_CONSOLIDEES")
public class AdressesConsolideesEntity  {
	
	/** Identifiant dans la table adresses_consolidees */
	@Id
	@Column(name = "OBJECTID")
	private Long objectId;
	
	/** id_adresse */
	@Column(name = "ID_ADRESSE")
	private Long idAdresse;

	/** no_voie */
	@Column(name = "NO_VOIE")
	private Long noVoie;
	
	/** complement_no_voie */
	@Column(name = "COMPLEMENT_NO_VOIE")
	private String complementNoVoie;

	/** voie */
	@Column(name = "VOIE")
	private String voie;
	
	/** Libellé complément Adresse */
	@Column(name = "LIBELLE_COMPLEMENT_ADRESSE")
	private String libelleComplementAdresse;
	
	/** Quartier */
	@Column(name = "QUARTIER")
	private String quartier;
	
	/** Commune */
	@Column(name = "COMMUNE")
	private String commune;
	
	/** nic */
	@Column(name = "NIC")
	private String nic;
	
	/** lotissement */
	@Column(name = "LOTISSEMENT")
	private String lotissement;
	
	/** surf_cad_ha */
	@Column(name = "SURF_CAD_HA")
	private Long surfCadHa;
	
	/** surf_cad_a */
	@Column(name = "SURF_CAD_A")
	private Long surfCadA;

	/** surf_cad_ca */
	@Column(name = "SURF_CAD_CA")
	private Long surfCadCa;

	/** code_postal */
	@Column(name = "CODE_POSTAL")
	private Long codePostal;

	/** shape */
	@Column(name = "SHAPE")
	private String shape;
	
	/** surf_equiv_m2 */
	@Column(name = "SURF_EQUIV_M2")
	private String surfEquivM2;

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
	 * @return the idAdresse
	 */
	public Long getIdAdresse() {
		return idAdresse;
	}

	/**
	 * @param idAdresse the idAdresse to set
	 */
	public void setIdAdresse(Long idAdresse) {
		this.idAdresse = idAdresse;
	}

	/**
	 * @return the noVoie
	 */
	public Long getNoVoie() {
		return noVoie;
	}

	/**
	 * @param noVoie the noVoie to set
	 */
	public void setNoVoie(Long noVoie) {
		this.noVoie = noVoie;
	}

	/**
	 * @return the complementNoVoie
	 */
	public String getComplementNoVoie() {
		return complementNoVoie;
	}

	/**
	 * @param complementNoVoie the complementNoVoie to set
	 */
	public void setComplementNoVoie(String complementNoVoie) {
		this.complementNoVoie = complementNoVoie;
	}

	/**
	 * @return the voie
	 */
	public String getVoie() {
		return voie;
	}

	/**
	 * @param voie the voie to set
	 */
	public void setVoie(String voie) {
		this.voie = voie;
	}

	/**
	 * @return the libelleComplementAdresse
	 */
	public String getLibelleComplementAdresse() {
		return libelleComplementAdresse;
	}

	/**
	 * @param libelleComplementAdresse the libelleComplementAdresse to set
	 */
	public void setLibelleComplementAdresse(String libelleComplementAdresse) {
		this.libelleComplementAdresse = libelleComplementAdresse;
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
	 * @return the nic
	 */
	public String getNic() {
		return nic;
	}

	/**
	 * @param nic the nic to set
	 */
	public void setNic(String nic) {
		this.nic = nic;
	}

	/**
	 * @return the lotissement
	 */
	public String getLotissement() {
		return lotissement;
	}

	/**
	 * @param lotissement the lotissement to set
	 */
	public void setLotissement(String lotissement) {
		this.lotissement = lotissement;
	}

	/**
	 * @return the surfCadHa
	 */
	public Long getSurfCadHa() {
		return surfCadHa;
	}

	/**
	 * @param surfCadHa the surfCadHa to set
	 */
	public void setSurfCadHa(Long surfCadHa) {
		this.surfCadHa = surfCadHa;
	}

	/**
	 * @return the surfCadA
	 */
	public Long getSurfCadA() {
		return surfCadA;
	}

	/**
	 * @param surfCadA the surfCadA to set
	 */
	public void setSurfCadA(Long surfCadA) {
		this.surfCadA = surfCadA;
	}

	/**
	 * @return the surfCadCa
	 */
	public Long getSurfCadCa() {
		return surfCadCa;
	}

	/**
	 * @param surfCadCa the surfCadCa to set
	 */
	public void setSurfCadCa(Long surfCadCa) {
		this.surfCadCa = surfCadCa;
	}

	/**
	 * @return the codePostal
	 */
	public Long getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(Long codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the shape
	 */
	public String getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(String shape) {
		this.shape = shape;
	}

	/**
	 * @return the surfEquivM2
	 */
	public String getSurfEquivM2() {
		return surfEquivM2;
	}

	/**
	 * @param surfEquivM2 the surfEquivM2 to set
	 */
	public void setSurfEquivM2(String surfEquivM2) {
		this.surfEquivM2 = surfEquivM2;
	}
	
}
