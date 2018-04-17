package com.isa.instaticketapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Model for placas
 * 
 * @author Milica Kovacevic
 *
 */
@Entity
@Table(name = "Place")
public class Place extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name", length = 50, unique = true)
	private String name;

	@Size(max = 50)
	@Column(name = "address", length = 50)
	private String address;

	@Size(max = 5000)
	@Column(name = "descripton", length = 5000)
	private String descripton;

	@Size(max = 20)
	@Column(name = "type", length = 20)
	private String type;

	private int vote=0;

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescripton() {
		return descripton;
	}

	public void setDescripton(String descripton) {
		this.descripton = descripton;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", address=" + address + ", descripton=" + descripton + "]";
	}

}
