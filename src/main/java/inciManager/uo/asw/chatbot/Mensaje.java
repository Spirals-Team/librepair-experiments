package inciManager.uo.asw.chatbot;

import java.util.Date;

public class Mensaje {

	private Date fechaHora;
	private String hora;
	private String contenido;
	private String autor;

	
	public Mensaje(Date fechaHora, String contenido, String autor) {
		super();
		this.fechaHora = fechaHora;
		int min = fechaHora.getMinutes();
	    if(min<=9)
	    	this.hora = ("  "+ fechaHora.getHours() +":0"+min);
	    else
	    	this.hora = ("  "+ fechaHora.getHours() +":"+min);
	
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
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	
}
