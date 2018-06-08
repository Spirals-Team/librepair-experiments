package io.xrates.backend.datamodel.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "user_name", nullable=false)
	private String name;
	
	@Column(unique = true, name = "email", nullable=false)
	@Email
	private String email;
	
	@Column(name = "contact_number")
	private long contactNumber;
	
	@Column(name = "isd_code", length = 3)
	private int isdCode;
	
	@Column(name = "email_verified")
	private boolean emailVerified;
	
	@Column(name = "contact_verified")
	private boolean contactVerfied;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Subscription> subscriptions;
	
	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getContact_number() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(int isdCode) {
		this.isdCode = isdCode;
	}

	public Set<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isContactVerfied() {
		return contactVerfied;
	}

	public void setContactVerfied(boolean contactVerfied) {
		this.contactVerfied = contactVerfied;
	}
	
}
