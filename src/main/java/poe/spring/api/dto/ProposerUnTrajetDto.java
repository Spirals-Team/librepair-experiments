package poe.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class ProposerUnTrajetDto {
	private String villeDepart;
	private String villeArrivee;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date dateDepart; // précision à la minute
	private Integer prix;
	private Integer nbPlaces;
	private Long conducteurId;

	public String getVilleDepart() {
		return villeDepart;
	}

	public void setVilleDepart(String villeDepart) {
		this.villeDepart = villeDepart;
	}

	public String getVilleArrivee() {
		return villeArrivee;
	}

	public void setVilleArrivee(String villeArrivee) {
		this.villeArrivee = villeArrivee;
	}

	public Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Integer getPrix() {
		return prix;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}

	public Integer getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(Integer nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	public Long getConducteurId() {
		return conducteurId;
	}

	public void setConducteurId(Long conducteurId) {
		this.conducteurId = conducteurId;
	}
}