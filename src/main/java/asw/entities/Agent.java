package asw.entities;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TAgent")
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String user;
	private String password;
	private String nombre;
	private String email;
	private int tipo;
	@OneToMany(fetch = FetchType.EAGER, mappedBy="agent")
	private Set<Incidence> incidencias = new HashSet<>();

	// Constructor vacio para JPA
	public Agent() {
	}
	
	/** Para pruebas
	 */
	public Agent(String user, String pass) {
		super();
		this.user = user;
		this.password = pass;
	}

	public Agent(String id, String nombre, String pass, String email, int tipo) {
		this(id, pass);
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Set<Incidence> getIncidencias() {
		return incidencias;
	}
	
	public void setIncidencias(Set<Incidence> incidencias) {
		this.incidencias = incidencias;
	}

	public Long getID() {
		return id;
	}
	
	public void setID(Long id)
	{
		this.id = id;
	}
	
	public String getNombreUsuario() {
		return user;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Citizen [ID=" + id + ", nombre=" + nombre + ", email=" + email
				+ ", tipo=" + tipo + "]";
	}
	
	public void crearContraseña()
	{
		Random random = new Random();
		
		String pass = "";
		int longitud_pass = random.nextInt(4) + 7;
		
		for (int i = 0; i < longitud_pass; i++) 
		{
			char caracterRandom = (char)(random.nextInt(26) + 'a'); // caracter de A a Z
			pass += caracterRandom;
		}
		
		this.password = pass;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void addIncidencia(Incidence incidence)
	{
		this.incidencias.add( incidence );
	}
}
