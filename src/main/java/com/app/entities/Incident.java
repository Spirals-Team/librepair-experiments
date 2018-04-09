package com.app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app.entities.Operator;

import com.app.utils.LatLng;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "manager_incident_collection")
public class Incident {

	@Id
	private String idautogenerado;

	private String agent;
	private String incidentName;
	private String description;
	private List<String> tags = new ArrayList<String>();;
	private Operator operator;
	private String topic;
	private String locationString;
	private LatLng location;
	private Date date;
	private Map<String, String> aditionalProperties = new HashMap<String, String>();
	private String aditionalPropertiesString;
	private IncidentStatus status;

	public enum IncidentStatus {
		OPEN, IN_PROCESS, CLOSED, CANCELLED
	};

	public Incident() {

	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Map<String, String> getAditionalProperties() {
		return aditionalProperties;
	}

	public void setAditionalProperties(Map<String, String> aditionalProperties) {
		this.aditionalProperties = aditionalProperties;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public IncidentStatus getStatus() {
		return status;
	}

	public void setStatus(IncidentStatus status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public String getLocationString() {
		return locationString;
	}

	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}

	public String getAditionalPropertiesString() {
		return aditionalPropertiesString;
	}

	public void setAditionalPropertiesString(String aditionalPropertiesString) {
		this.aditionalPropertiesString = aditionalPropertiesString;
	}

	@Override
	public String toString() {
		return "Incident [agent='" + agent + "', incidentName='" + incidentName + "', description='" + description
				+ "', tags='" + tags + "', operator='" + operator + "', topic='" + topic + "', locationString='"
				+ locationString + "', location='" + location + "', date='" + date + "', aditionalProperties='"
				+ aditionalProperties + "', aditionalPropertiesString='" + aditionalPropertiesString + "', status='"
				+ status + "']";
	}
	

}
