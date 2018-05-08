package edu.unq.desapp.grupo_a.backend.model;

import edu.unq.desapp.grupo_a.backend.model.exceptions.UserInitException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;

	private String streetName;
	private String comments;
	private Double coordLat;
	private Double coordLong;
	private String streetNumber;
	private String department;
	private String districtName;
	private String zipCode;
	private String floor;
	private String betweenStreet1;
	private String betweenStreet2;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Double getCoordLat() {
		return coordLat;
	}
	public void setCoordLat(Double coordLat) {
		this.coordLat = coordLat;
	}
	public Double getCoordLong() {
		return coordLong;
	}
	public void setCoordLong(Double coordLong) {
		this.coordLong = coordLong;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getBetweenStreet1() {
		return betweenStreet1;
	}
	public void setBetweenStreet1(String betweenStreet1) {
		this.betweenStreet1 = betweenStreet1;
	}
	public String getBetweenStreet2() {
		return betweenStreet2;
	}
	public void setBetweenStreet2(String betweenStreet2) {
		this.betweenStreet2 = betweenStreet2;
	}

	public static void check(Address address) throws UserInitException {
		if (address == null) {
			throw new UserInitException();
		}
	}
}
