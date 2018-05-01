package uo.asw.dbManagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Agent {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@Column(unique=true)
	private String identifier;
	
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	private String location;
	
	@NotNull
	private String kind;
	
	@OneToMany(mappedBy = "agent")
	private Set<Incidence> incidences = new HashSet<>();
	
	public Agent(){}
	
	public Agent(String identifier, String password, String kind) {
		super();
		this.identifier = identifier;
		this.password = password;
		this.kind = kind;
	}

	public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Set<Incidence> getIncidences() {
		return incidences;
	}

	public void setIncidences(Set<Incidence> incidences) {
		this.incidences = incidences;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", identifier=" + identifier + ", password=" + password + ", name=" + name
				+ ", email=" + email + ", location=" + location + ", kind=" + kind + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		Agent other = (Agent) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
}
