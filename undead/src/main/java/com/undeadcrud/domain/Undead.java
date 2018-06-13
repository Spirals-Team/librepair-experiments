package com.undeadcrud.domain;

public class Undead {
	private int id;
	private String nazwa;
	private String typ;
	private int tier;
	private String lokacja;
	private String zdolnoscSpecjalna;

	public Undead( String nazwa, String typ, int tier, String lokacja, String zdolnoscSpecjalna) {
		//this.id = id;
		this.nazwa = nazwa;
		this.typ = typ;
		this.tier = tier;
		this.lokacja = lokacja;
		this.zdolnoscSpecjalna = zdolnoscSpecjalna;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public String getLokacja() {
		return lokacja;
	}

	public void setLokacja(String lokacja) {
		this.lokacja = lokacja;
	}

	public String getZdolnoscSpecjalna() {
		return zdolnoscSpecjalna;
	}

	public void setZdolnoscSpecjalna(String zdolnoscSpecjalna) {
		this.zdolnoscSpecjalna = zdolnoscSpecjalna;
	}
}