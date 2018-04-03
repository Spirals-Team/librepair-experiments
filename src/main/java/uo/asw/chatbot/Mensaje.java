package uo.asw.chatbot;

import java.util.Date;

public class Mensaje {

	private Date fechaHora;
	private String contenido;
	private String autor;

	
	public Mensaje(Date fechaHora, String contenido, String autor) {
		super();
		this.fechaHora = fechaHora;
		this.contenido = contenido;
		this.autor = autor;
	}
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	
}
