package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EmploymentPlace")
public class EmploymentPlace extends AbstractAuditingEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Place place;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User admin;
	
	public EmploymentPlace() {
		// TODO Auto-generated constructor stub
	}

	public EmploymentPlace(Place place, User admin) {
		super();
		this.place = place;
		this.admin = admin;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	
	
	

}
