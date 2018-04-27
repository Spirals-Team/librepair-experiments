package nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.bilan.energie.contract.dto.annotations.IdDto;


/**
 * DTO Support
 * 
 * @author Greg Dujardin
 *
 */
public class Support implements DtoModel {

	private static final long serialVersionUID = 1L;

	/** Version de l'entité */
	private long version;
	
	/** Identifiant technique */
	@IdDto
	private Long id;
	
	/** Liste des luminaires */
	private List<Luminaire> listeLuminaire;
	
	/** Identifiant de l'éclairage Public */
	private Long idEclairagePublic;
	
	/** Adresse */
	private AdresseLabel adresse;
	
	/** Type de support */
	private CodeLabel typeSupport;
	
	/** Support utilisé pour la distribution d'électricité */
	private boolean mixteDistri;
	
	/** Modèle de support */
	private String modele;
	
	/** Numéro dans l'inventaire EEC */
	private String numInventaire;
	
	/** Propriétaire du support */
	private CodeLabel proprietaire;
	
	/** Date de début d'existence */
	private Date dateDebut;
	
	/** Date de fin d'existence */
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
	 * @return the listeLuminaire
	 */
	public List<Luminaire> getListeLuminaire() {
		return listeLuminaire;
	}

	/**
	 * @param listeLuminaire the listeLuminaire to set
	 */
	public void setListeLuminaire(List<Luminaire> listeLuminaire) {
		this.listeLuminaire = listeLuminaire;
	}

	/**
	 * @return the idEclairagePublic
	 */
	public Long getIdEclairagePublic() {
		return idEclairagePublic;
	}

	/**
	 * @param idEclairagePublic the idEclairagePublic to set
	 */
	public void setIdEclairagePublic(Long idEclairagePublic) {
		this.idEclairagePublic = idEclairagePublic;
	}

	/**
	 * @return the adresse
	 */
	public AdresseLabel getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(AdresseLabel adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the typeSupport
	 */
	public CodeLabel getTypeSupport() {
		return typeSupport;
	}

	/**
	 * @param typeSupport the typeSupport to set
	 */
	public void setTypeSupport(CodeLabel typeSupport) {
		this.typeSupport = typeSupport;
	}

	/**
	 * @return the mixteDistri
	 */
	public boolean isMixteDistri() {
		return mixteDistri;
	}

	/**
	 * @param mixteDistri the mixteDistri to set
	 */
	public void setMixteDistri(boolean mixteDistri) {
		this.mixteDistri = mixteDistri;
	}

	/**
	 * @return the modele
	 */
	public String getModele() {
		return modele;
	}

	/**
	 * @param modele the modele to set
	 */
	public void setModele(String modele) {
		this.modele = modele;
	}

	/**
	 * @return the numInventaire
	 */
	public String getNumInventaire() {
		return numInventaire;
	}

	/**
	 * @param numInventaire the numInventaire to set
	 */
	public void setNumInventaire(String numInventaire) {
		this.numInventaire = numInventaire;
	}

	/**
	 * @return the proprietaire
	 */
	public CodeLabel getProprietaire() {
		return proprietaire;
	}

	/**
	 * @param proprietaire the proprietaire to set
	 */
	public void setProprietaire(CodeLabel proprietaire) {
		this.proprietaire = proprietaire;
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
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
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
