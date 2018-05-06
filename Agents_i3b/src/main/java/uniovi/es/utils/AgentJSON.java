package uniovi.es.utils;

import uniovi.es.entities.Agent;

public class AgentJSON {

	private String name;

	private String location;

	private String email;

	private String id;

	private String kind;

	private String kindCode;

	public AgentJSON(Agent agent) {
		this.name = agent.getName();
		this.location = agent.getLocation();
		this.email = agent.getEmail();
		this.id = agent.getId();
		this.kind = agent.getKind();
		this.kindCode = agent.getKindCode();
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getLocation() {
		return location;
	}

	public String getKind() {
		return kind;
	}

	public String getId() {
		return id;
	}

	public String getKindCode() {
		return kindCode;
	}

}