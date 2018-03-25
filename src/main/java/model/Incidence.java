package model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import utils.IncidenceUtils;

@Document(collection="incidences")
public class Incidence {
	
	@Id
	private String inciId;
	
	private String username;
	private int usertype;
	private String inci_name, inci_description, inci_location, inci_info;
	private List<String> tags, otherfields;
	private int state, expiration;
	private String operatorId, stateStr;
	private List<String> comments;
	
	public Incidence() {
		comments = new ArrayList<String>();
		tags = new ArrayList<String>();
		otherfields = new ArrayList<String>();
	}
	
	public Incidence(String inciId, String username, int usertype, String inci_name, String inci_description,
			String inci_location, String inci_info, List<String> tags, List<String> otherfields, int state,
			int expiration, String operatorId, List<String> comments) {
		super();
		this.inciId = inciId;
		this.username = username;
		this.usertype = usertype;
		this.inci_name = inci_name;
		this.inci_description = inci_description;
		this.inci_location = inci_location;
		this.inci_info = inci_info;
		this.tags = tags;
		this.otherfields = otherfields;
		this.state = state;
		this.expiration = expiration;
		this.operatorId = operatorId;
		this.comments = comments;
		this.stateStr = IncidenceUtils.getStateString(state);
	}

	@JsonIgnore
	public String getComment() {
		String comment = "";
		for (String s: comments) {
			comment+="\n";
			comment+=s;
		}
		return comment;
			
	}
	public String getInciId() {
		return inciId;
	}

	public void setInciId(String inciId) {
		this.inciId = inciId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usetype) {
		this.usertype = usetype;
	}

	public String getInciName() {
		return inci_name;
	}

	public void setInciName(String inci_name) {
		this.inci_name = inci_name;
	}

	public String getInciDescription() {
		return inci_description;
	}

	public void setInciDescription(String inci_description) {
		this.inci_description = inci_description;
	}

	public String getInciLocation() {
		return inci_location;
	}

	public void setInciLocation(String inci_location) {
		this.inci_location = inci_location;
	}

	public String getInciInfo() {
		return inci_info;
	}

	public void setInciInfo(String inci_info) {
		this.inci_info = inci_info;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getOtherfields() {
		return otherfields;
	}

	public void setOtherfields(List<String> otherfields) {
		this.otherfields = otherfields;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		this.stateStr = IncidenceUtils.getStateString(state);
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}
	
	public String getTagsStr() {
		if(tags == null)
			return "";
		String result = "";
		for(String s : tags) {
			result += s + ", ";
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append(" \"id\":\"").append(inciId).append("\",");
		sb.append(" \"username\":\"").append(username).append("\",");
		sb.append(" \"usertype\":").append(usertype).append(",");
		sb.append(" \"name\":\"").append(inci_name).append("\",");
		sb.append(" \"description\":\"").append(inci_description).append("\",");
		sb.append(" \"location\":\"").append(inci_location).append("\",");
		sb.append(" \"info\":\"").append(inci_info).append("\",");
		sb.append(" \"state\":\"").append(stateStr).append("\",");
		sb.append(" \"exiration\":").append(expiration).append(",");
		sb.append(" \"operator\":\"").append(operatorId).append("\"");
		sb.append('}');
		return sb.toString();
	}

}
