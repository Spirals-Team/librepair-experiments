package nc.noumea.mairie.radi.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LightUserList {

	private List<LightUser> users;

	public LightUserList() {
		users = new ArrayList<LightUser>();
	}
	
	public List<LightUser> getUsers() {
		return users;
	}

	public void setUsers(List<LightUser> users) {
		this.users = users;
	}
}
