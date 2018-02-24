package dao;

import java.util.Random;

import es.uniovi.asw.Csv;

public class Agente {
	private String nombre, localizacion, email, identificador;
	private int tipo;
	private String password;

	public Agente(String nombre, String localizacion,
			String email, String identificador, int tipo) {
		super();
		this.nombre = nombre;
		if(localizacion!=null)
			this.localizacion = localizacion;
		
		this.email = email;
		this.identificador = identificador;
		this.tipo = tipo;
		crearPassword();

	}
	
	public String toString (){
        String mensaje="Agente:\n\t Nombre: "+getNombre()+"\n\t Localizacion: "+ getLocalizacion()+"\n\t Email: "+ getEmail()+"\n\t Identificador: "+ getIdentificador()+"\n\t Tipo: "+ Csv.getHashMAp().get(getTipo())+"\n\t Contraseña: "+ getPassword();
        return mensaje;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
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

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void crearPassword() {
		setPassword("");
		char[] minusculas = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		char[] mayusculas = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
		char[] numeros = "0123456789".toCharArray();
		char[] simbolos = "'¿?*+-$%".toCharArray();

		// Tiene una letra mayuscula
		Random random = new Random();
		int pos = random.nextInt(mayusculas.length);
		setPassword(getPassword() + mayusculas[pos]);

		// Tiene 5 letras minusculas
		for (int i = 0; i < 5; i++) {
			random = new Random();
			pos = random.nextInt(minusculas.length);
			setPassword(getPassword() + minusculas[pos]);
		}

		// Tiene un numero
		random = new Random();
		pos = random.nextInt(numeros.length);
		setPassword(getPassword() + numeros[pos]);

		// Tiene un simbolo especial
		random = new Random();
		pos = random.nextInt(simbolos.length);
		setPassword(getPassword() + simbolos[pos]);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
