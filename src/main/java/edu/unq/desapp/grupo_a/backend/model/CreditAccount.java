package edu.unq.desapp.grupo_a.backend.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "credit_account")
public class CreditAccount extends PersistenceEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Double currentAmount;

	private User user;

	public CreditAccount() {
		this.currentAmount = (double) 0;
	}

	public Double getCurrentAmount() {
		return this.currentAmount;
	}

	public void addAmount(Double amount) {
		this.currentAmount += amount;
	}

	public void loseAmount(Double amount) {
		this.currentAmount -= amount;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
