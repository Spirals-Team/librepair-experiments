//package poe.spring.domain;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Voyage {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	private String villeDepart;
//	private String villeArrive;
//	private Date dateDepart;
//	private Long prix;
//	private Integer nbPlace;
//
//	@Override
//	public String toString() {
//		return "Voyage [id=" + id + ", villeDepart=" + villeDepart + ", villeArrive=" + villeArrive + ", dateDepart="
//				+ dateDepart + ", prix=" + prix + ", nbPlace=" + nbPlace + "]";
//	}
//
//	public Voyage(Long id, String villeDepart, String villeArrive, Date dateDepart, Long prix, Integer nbPlace) {
//		super();
//		this.id = id;
//		this.villeDepart = villeDepart;
//		this.villeArrive = villeArrive;
//		this.dateDepart = dateDepart;
//		this.prix = prix;
//		this.nbPlace = nbPlace;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getVilleDepart() {
//		return villeDepart;
//	}
//
//	public void setVilleDepart(String villeDepart) {
//		this.villeDepart = villeDepart;
//	}
//
//	public String getVilleArrive() {
//		return villeArrive;
//	}
//
//	public void setVilleArrive(String villeArrive) {
//		this.villeArrive = villeArrive;
//	}
//
//	public Date getDateDepart() {
//		return dateDepart;
//	}
//
//	public void setDateDepart(Date dateDepart) {
//		this.dateDepart = dateDepart;
//	}
//
//	public Long getPrix() {
//		return prix;
//	}
//
//	public void setPrix(Long prix) {
//		this.prix = prix;
//	}
//
//	public Integer getNbPlace() {
//		return nbPlace;
//	}
//
//	public void setNbPlace(Integer nbPlace) {
//		this.nbPlace = nbPlace;
//	}
//
//}
