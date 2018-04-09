package com.app.entities;

import java.util.ArrayList;
import java.util.List;

public class Operator {

	private Long id;

	private String username;
	private String password;

	private List<Incident> incidents = new ArrayList<Incident>();

	public Operator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Operator() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents(List<Incident> incidents) {
		this.incidents = incidents;
	}

	@Override
	public String toString() {
		return "Operator [id='" + id + "', username='" + username + "', password='" + password + "', incidents='" + incidents
				+ "']";
	}
	
}
