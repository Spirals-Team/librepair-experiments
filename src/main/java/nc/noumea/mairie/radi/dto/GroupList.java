package nc.noumea.mairie.radi.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupList {

	private List<Group> groups;

	public GroupList() {
		groups = new ArrayList<Group>();
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
