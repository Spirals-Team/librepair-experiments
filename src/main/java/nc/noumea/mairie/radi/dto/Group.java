package nc.noumea.mairie.radi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
public class Group extends LightGroup {

	private String cn;
	private String sAMAccountType;
	private Date whenChanged;
	private Date whenCreated;
	private String objectGUID;
	private String objectSid;
	private String objectCategory;
	private List<String> objectClass;

	@JsonIgnore
	private List<String> members;

	public Group() {
		members = new ArrayList<String>();
		objectClass = new ArrayList<String>();
	}

	@XmlTransient
	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getsAMAccountType() {
		return sAMAccountType;
	}

	public void setsAMAccountType(String sAMAccountType) {
		this.sAMAccountType = sAMAccountType;
	}

	public Date getWhenChanged() {
		return whenChanged;
	}

	public void setWhenChanged(Date whenChanged) {
		this.whenChanged = whenChanged;
	}

	public Date getWhenCreated() {
		return whenCreated;
	}

	public void setWhenCreated(Date whenCreated) {
		this.whenCreated = whenCreated;
	}

	public String getObjectGUID() {
		return objectGUID;
	}

	public void setObjectGUID(String objectGUID) {
		this.objectGUID = objectGUID;
	}

	public String getObjectSid() {
		return objectSid;
	}

	public void setObjectSid(String objectSid) {
		this.objectSid = objectSid;
	}

	public String getObjectCategory() {
		return objectCategory;
	}

	public void setObjectCategory(String objectCategory) {
		this.objectCategory = objectCategory;
	}

	public List<String> getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(List<String> objectClass) {
		this.objectClass = objectClass;
	}
}
