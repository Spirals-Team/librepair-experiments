package uniovi.es.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.google.gson.Gson;

public class Incidence {

	@Id
	private String inciId;
	private String inci_description;
	private String inci_name;
	private String inci_location;
	private int state, expiration;
	private String username;
	private String inci_info;
	private int usertype;
	private Map<String, String> customFields;
	private String operatorId;
	private List<String> comments, tags;

	public Incidence() {
		tags = new ArrayList<String>();
		comments = new ArrayList<String>();
		customFields = new HashMap<>();
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

	public String getStateStr() {
		switch (state) {
		case 0:
			return "Open";
		case 1:
			return "In Process";
		case 2:
			return"Closed";
		case 3:
			return "Cancelled";
		default:
			return "";
		}
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public String getInciId() {
		return inciId;
	}

	public void setInciId(String inciId) {
		this.inciId = inciId;
	}

	public String getInci_description() {
		return inci_description;
	}

	public void setInci_description(String inci_description) {
		this.inci_description = inci_description;
	}

	public String getInci_name() {
		return inci_name;
	}

	public void setInci_name(String inci_name) {
		this.inci_name = inci_name;
	}

	public String getInci_location() {
		return inci_location;
	}

	public void setInci_location(String inci_location) {
		this.inci_location = inci_location;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInci_info() {
		return inci_info;
	}

	public void setInci_info(String inci_info) {
		this.inci_info = inci_info;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public Map<String, String> getCustomFields() {
		return customFields;
	}

	public void setCustomFields(Map<String, String> customFields) {
		this.customFields = customFields;
	}

	public String tagsToStr() {
		String str = "";
		for (String s : tags)
			str += s + ",";
		return str;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		String fields = gson.toJson(customFields);

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append(" \"id\":\"").append(inciId).append("\",");
		sb.append(" \"username\":\"").append(username).append("\",");
		sb.append(" \"usertype\":").append(usertype).append(",");
		sb.append(" \"name\":\"").append(inci_name).append("\",");
		sb.append(" \"description\":\"").append(inci_description).append("\",");
		sb.append(" \"location\":\"").append(inci_location).append("\",");
		sb.append(" \"info\":\"").append(inci_info).append("\",");
		sb.append(" \"state\":").append(state).append(",");
		sb.append(" \"exiration\":").append(expiration).append(",");
		sb.append(" \"operator\":\"").append(operatorId).append("\",");
		sb.append(" \"tags\":\"").append(tagsToStr()).append("\",");
		sb.append(" \"customfields\":").append(fields);
		sb.append('}');
		return sb.toString();

	}

}
