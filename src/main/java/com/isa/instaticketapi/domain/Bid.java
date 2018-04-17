package com.isa.instaticketapi.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Bid")
public class Bid extends AbstractAuditingEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@ManyToOne(fetch=FetchType.EAGER)
	private Offer offer;
	
	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Column
	private String sum;
	
	@Column
	private String userName;
	
	@Column
	private String ordNum;
	
	public Bid() {
		// TODO Auto-generated constructor stub
	}

	public Bid(User user, Offer offer, String sum) {
		super();
		this.user = user;
		this.offer = offer;
		this.sum = sum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(String ordNum) {
		this.ordNum = ordNum;
	}
	
	
	
	
	

}
