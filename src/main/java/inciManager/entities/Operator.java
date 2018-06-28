package inciManager.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Operator {
	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String email;
	private String password;
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "operadorAsignado", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Incidencia> incidenciasAsignadas = new HashSet<Incidencia>();

	public Operator() {
	}

	public Operator(String email, String password, String name) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<Incidencia> getIncidenciasAsignadas() {
		return incidenciasAsignadas;
	}

	public void setIncidenciasAsignadas(Set<Incidencia> incidenciasAsignadas) {
		this.incidenciasAsignadas = incidenciasAsignadas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Operator other = (Operator) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
