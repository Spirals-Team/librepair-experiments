package poe.spring.form;

import java.util.Date;

import javax.validation.constraints.Size;

public class TrajetForm {
	@Size(min = 1)
	private String villeDepart;
	@Size(min = 1)
	private String villeArrivee;
	@Size(min = 1)
	private Date dateDepart;
	@Size(min = 1)
	private Long prix;
	@Size(min = 1)
	private Integer nbPlace;

	public String getVilleDepart() {
		return villeDepart;
	}

	public void setVilleDepart(String villeDepart) {
		this.villeDepart = villeDepart;
	}

	public String getVilleArrivee() {
		return villeArrivee;
	}

	public void setVilleArrivee(String villeArrive) {
		this.villeArrivee = villeArrive;
	}

	public Date getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Long getPrix() {
		return prix;
	}

	public void setPrix(Long prix) {
		this.prix = prix;
	}

	public Integer getNbPlace() {
		return nbPlace;
	}

	public void setNbPlace(Integer nbPlace) {
		this.nbPlace = nbPlace;
	}

}