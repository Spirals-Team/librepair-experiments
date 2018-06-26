package uo.asw.entities;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agentes")
public class Agent {

	private String nombre;
	private String password;
	private String email;
	private String ubicacion;
	private String identificador;
	private String kind;
	private int kindCode;
	
	private List<Incidence> listaIncidencias;

	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param password
	 * @param email
	 * @param dNI
	 * @param kind
	 * @param kindCode
	 */
	
	public Agent()
	{
		
	}
	public Agent(String nombre, String password, String email, String identificador, String ubicacion,
			String kind, int kindCode) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.email = email;
		this.identificador = identificador;
		this.ubicacion = ubicacion;
		this.kind = kind;
		this.kindCode = kindCode;
	}
	
	public Agent(String nombre, String kind, int kindCode) {
		super();
		this.nombre = nombre;
		this.kind = kind;
		this.kindCode = kindCode;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre=nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getKindCode() {
		return kindCode;
	}

	public void setKindCode(int kindCode) {
		this.kindCode = kindCode;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public List<Incidence> getListaIncidencias() {
		return listaIncidencias;
	}

	public void setListaIncidencias(List<Incidence> listaIncidencias) {
		this.listaIncidencias = listaIncidencias;
	}

	@Override
	public String toString() {
		return "Agent [nombre=" + nombre + ", email=" + email
				+ ", ubicacion=" + ubicacion + ", identificador=" + identificador + ", kind=" + kind + ", kindCode="
				+ kindCode + "]";
	}
	

	



}