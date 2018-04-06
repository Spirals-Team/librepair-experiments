package com.app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app.entities.Operator;

import com.app.utils.LatLng;

public class Incident {

	private Agent agent;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aditionalProperties == null) ? 0 : aditionalProperties.hashCode());
		result = prime * result + ((aditionalPropertiesString == null) ? 0 : aditionalPropertiesString.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((incidentName == null) ? 0 : incidentName.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((locationString == null) ? 0 : locationString.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((topic == null) ? 0 : topic.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incident other = (Incident) obj;
		if (aditionalProperties == null) {
			if (other.aditionalProperties != null)
				return false;
		} else if (!aditionalProperties.equals(other.aditionalProperties))
			return false;
		if (aditionalPropertiesString == null) {
			if (other.aditionalPropertiesString != null)
				return false;
		} else if (!aditionalPropertiesString.equals(other.aditionalPropertiesString))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (incidentName == null) {
			if (other.incidentName != null)
				return false;
		} else if (!incidentName.equals(other.incidentName))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (locationString == null) {
			if (other.locationString != null)
				return false;
		} else if (!locationString.equals(other.locationString))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (status != other.status)
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (topic == null) {
			if (other.topic != null)
				return false;
		} else if (!topic.equals(other.topic))
			return false;
		return true;
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
