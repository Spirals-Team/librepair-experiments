package uo.asw.dbManagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import uo.asw.dbManagement.tipos.PerfilTipos;

@Document(collection = "usuarios")
public class Usuario {
	@Id
	private ObjectId id = new ObjectId();
	
	private String nombre;
	private String apellidos;
	private String email;
	
	@NotNull
	@Column(unique = true)
	private String identificador;
	@NotNull
	private String contrasena;
	
//	@DBRef
	private PerfilTipos perfil;
	
	@DBRef
	private Set<Incidencia> incidencias = new HashSet<Incidencia>();
	
	public Usuario() {}

	public Usuario(String nombre, String apellidos, String email, String identificador, String contrasena,
			PerfilTipos perfil) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.identificador = identificador;
		this.contrasena = contrasena;
		this.perfil = perfil;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public PerfilTipos getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilTipos perfil) {
		this.perfil = perfil;
	}
	
	

	public Set<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", identificador=" + identificador + ", contrasena=" + contrasena + ", perfil=" + perfil + "]";
	}
	
	

}
