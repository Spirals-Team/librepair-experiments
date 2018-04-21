package com.uniovi.model;

import java.util.List;

import javax.persistence.*;

/**
 * Clase que representa a los usuarios de la aplicacion
 * 
 * @author Tania Álvarez Díaz
 *
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Location localizacion;
    private String email;
    @Column(unique = true)
    private String identificador; // Es unico y es el nombre de usuario
    private String password;
    private String tipo;

    // @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    // private List<Incidence> incidencias;

    /**
     * Constructor vacio
     */
    User() {
    }

    /**
     * Constructor
     * 
     * @param nombre
     * @param localizacion
     * @param email
     * @param identificador
     * @param tipo
     */
    public User(String nombre, String localizacion, String email, String identificador, String tipo, String password) {
	setNombre(nombre);
	setLocalizacion(obtenerLocalizacion(localizacion));
	setEmail(email);
	setIdentificador(identificador);
	setTipo(tipo);
	setPassword(password);
    }

    public String getNombre() {
	return nombre;
    }

    private void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public Location getLocalizacion() {
	return localizacion;
    }

    private void setLocalizacion(Location localizacion) {
	this.localizacion = localizacion;
    }

    public String getEmail() {
	return email;
    }

    private void setEmail(String email) {
	this.email = email;
    }

    public String getIdentificador() {
	return identificador;
    }

    private void setIdentificador(String identificador) {
	this.identificador = identificador;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getTipo() {
	return tipo;
    }

    private void setTipo(String tipo) {
	this.tipo = tipo;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
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
	User other = (User) obj;
	if (identificador == null) {
	    if (other.identificador != null)
		return false;
	} else if (!identificador.equals(other.identificador))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "User [id =" + id + ", nombre=" + nombre + ", localizacion=" + localizacion.toString() + ", email="
		+ email + ", identificador=" + identificador + ", tipo=" + tipo + "]";
    }

    /**
     * Método para obtener la localizacion del usuario a partir del string que se
     * pasa por parámetro
     * 
     * @param loc
     * @return localizacion del usuario
     */
    private Location obtenerLocalizacion(String loc) {
	if (loc.equals("")) {
	    Location l = new Location(0, 0);
	    l.setExist(false);
	    return l;
	}
	String[] trozos = loc.split("&");
	double latitud = Double.parseDouble(trozos[0]);
	double longitud = Double.parseDouble(trozos[1]);
	return new Location(latitud, longitud);
    }
}