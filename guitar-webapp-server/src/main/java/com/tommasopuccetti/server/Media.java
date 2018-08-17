package com.tommasopuccetti.server;

import org.springframework.data.annotation.Id;

public class Media {
	
	@Id
	private String id;
	private String name;
	private String embedded;
	
	public Media(String name, String embedded) {
		this.name = name;
		this.embedded = embedded;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmbedded() {
		return embedded;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", name=" + name + ", embedded=" + embedded + "]";
	}

	public void setId(String id) {
		this.id = id;
		
	}
	
	

}
